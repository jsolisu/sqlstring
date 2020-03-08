import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.stream.Collectors;

public class SQLString {

	private String sqlText;

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
	}

	public void setDouble(String nombre, Double valor) {
		sqlText = sqlText.replace(":" + nombre.trim(), defaultValue(valor, valor.toString(), "NULL"));
	}

	public void setDate(String nombre, Date valor) {
		sqlText = sqlText.replace(":" + nombre.trim(),
				defaultValue(valor, String.format("'%s'", valor.toString()), "NULL"));
	}

	public void setInt(String nombre, Integer valor) {
		sqlText = sqlText.replace(":" + nombre.trim(), defaultValue(valor, valor.toString(), "NULL"));
	}

	public void setString(String nombre, String valor) {
		sqlText = sqlText.replace(":" + nombre.trim(), defaultValue(valor, String.format("'%s'", valor), "NULL"));
	}

	public void setCustom(String nombre, String valor) {
		sqlText = sqlText.replace(":" + nombre.trim(), defaultValue(valor, valor, "NULL"));
	}

	public String toString() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}

}
