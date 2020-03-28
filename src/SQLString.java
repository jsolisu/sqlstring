import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SQLString {

	class Parameter {
		public String name;
		public String partial;
	}

	// SQL Text
	private String sqlText;

	// internal SQL Text
	private String internalText;

	private List<Parameter> parameters;

	private String defaultValue(Object obj, String value, String defaultValue) {
		return (obj == null) ? defaultValue : value;
	}

	public SQLString(Class<?> clase, String recurso) throws Exception {
		super();

		InputStream in = clase.getResourceAsStream(recurso);

		if (in == null) {
			throw new Exception(String.format("Recurso [%s] no encontrado.", recurso));
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		sqlText = reader.lines().collect(Collectors.joining("\n"));
		reader.close();

		extractParameters();
	}

	private void setParameter(String nombre, String valor) {
		Boolean encontrado = false;
		internalText = "";

		for (int i = 0; i < parameters.size(); i++) {
			if (parameters.get(i).name.equalsIgnoreCase(nombre)) {
				internalText = internalText + parameters.get(i).partial + valor;
				encontrado = true;
			} else {
				internalText += parameters.get(i).partial + "?";
			}
		}

		if (!encontrado) {
			throw new IllegalArgumentException(String.format("No existe el parametro [%s].", nombre));
		}
	}

	private void extractParameters() {
		parameters = new ArrayList<Parameter>();

		int length = sqlText.length();
		StringBuffer parsedQuery = new StringBuffer(length);

		boolean inSingleQuote = false;
		boolean inDoubleQuote = false;
		boolean inSingleLineComment = false;
		boolean inMultiLineComment = false;

		for (int i = 0; i < length; i++) {
			char c = sqlText.charAt(i);
			Parameter parameter = null;

			if (inSingleQuote) {
				if (c == '\'') {
					inSingleQuote = false;
				}
			} else if (inDoubleQuote) {
				if (c == '"') {
					inDoubleQuote = false;
				}
			} else if (inMultiLineComment) {
				if (c == '*' && sqlText.charAt(i + 1) == '/') {
					inMultiLineComment = false;
				}
			} else if (inSingleLineComment) {
				if (c == '\n') {
					inSingleLineComment = false;
				}
			} else if (c == '\'') {
				inSingleQuote = true;
			} else if (c == '"') {
				inDoubleQuote = true;
			} else if (c == '/' && sqlText.charAt(i + 1) == '*') {
				inMultiLineComment = true;
			} else if (c == '-' && sqlText.charAt(i + 1) == '-') {
				inSingleLineComment = true;
			} else if (c == ':' && i + 1 < length && Character.isJavaIdentifierStart(sqlText.charAt(i + 1))) {
				int j = i + 2;
				while (j < length && Character.isJavaIdentifierPart(sqlText.charAt(j))) {
					j++;
				}
				String name = sqlText.substring(i + 1, j);
				parameter = new Parameter();
				parameter.name = name;
				parameters.add(parameter);
				c = '?';
				i += name.length();
			}
			parsedQuery.append(c);
		}

		String[] textSplit = parsedQuery.toString().split("\\?");
		for (int i = 0; i < parameters.size(); i++) {
			parameters.get(i).partial = textSplit[i];
		}
	}

	public void setDouble(String nombre, Double valor) {
		setParameter(nombre, defaultValue(valor, valor.toString(), "NULL"));
	}

	public void setDate(String nombre, Date valor) {
		setParameter(nombre, defaultValue(valor, String.format("'%s'", valor.toString()), "NULL"));
	}

	public void setInt(String nombre, Integer valor) {
		setParameter(nombre, defaultValue(valor, valor.toString(), "NULL"));
	}

	public void setString(String nombre, String valor) {
		setParameter(nombre, defaultValue(valor, String.format("'%s'", valor), "NULL"));
	}

	public void setCustom(String nombre, String valor) {
		setParameter(nombre, defaultValue(valor, valor, "NULL"));
	}

	public void setLong(String nombre, Long valor) {
		setParameter(nombre, defaultValue(valor, valor.toString(), "NULL"));
	}

	public String toString() {
		return internalText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;

		extractParameters();
	}

}
