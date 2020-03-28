
public class TestClass {
	TestClass() throws Exception {
		SQLString query = new SQLString(this.getClass(), "Test.sql");

		query.setDouble("PRECIO", 0.0);

		query = new SQLString(this.getClass(), "Test2.sql");

		query.setString("ID_PERFIL", "JSOLIS");
	}
}
