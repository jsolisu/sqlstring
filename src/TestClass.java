
public class TestClass {
	TestClass() throws Exception {
		SQLString query = new SQLString(this.getClass(), "Test.sql");

		query.setDouble("PRECIO", 0.0);

		query = new SQLString(this.getClass(), "Test2.sql");

		query.setString("ID_PERFIL", "JSOLIS");
		
		query = new SQLString(this.getClass(), "Test3.sql");

		query.setLong("FACT_ID", 1L);
		
		query.setLong("EMP_ID", 1L);
	}
}
