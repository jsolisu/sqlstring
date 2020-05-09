import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.stream.Collectors;

public class SQLString {

	private String sqlText;

	public SQLString() {
		super();
	}

	public SQLString(String valor) {
		super();

		sqlText = valor;
	}

	public SQLString(Class<?> clase, String recurso) throws Exception {
		try (InputStream in = clase.getResourceAsStream(recurso);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			sqlText = reader.lines().collect(Collectors.joining("\n"));
		} catch (Exception e) {
			throw new Exception(String.format("Recurso [%s] no encontrado.", recurso));
		}
	}

	private String defaultValue(Object obj, String valor, String defaultValue) {
		return (obj == null) ? defaultValue : valor;
	}

	private void setParameter(String nombre, String valor) {
		sqlText = sqlText.replaceAll(String.format(":%s(?=[^\\w])", nombre.trim().toUpperCase()), valor);
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
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}
}
