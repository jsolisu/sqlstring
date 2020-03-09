
public class TestClass {
	TestClass() throws Exception {
		@SuppressWarnings("unused")
		SQLString query = new SQLString(this.getClass(), "Test.sql");
		
		query.setDouble("PRECIO", 0.0);
	}
}
