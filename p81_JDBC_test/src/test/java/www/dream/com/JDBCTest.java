package www.dream.com;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;
//Oracle과 연동하기 위한 System 
public class JDBCTest {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String USER_ID = "system";
	private static final String PASSWORD = "1234";
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	} // 정적 initial rider
	@Test
	public void test() {
		try(Connection connection = DriverManager.getConnection(URL, USER_ID,PASSWORD);) {
			assertNotNull(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
