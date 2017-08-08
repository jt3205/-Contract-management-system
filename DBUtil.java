import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * DBUtil ��ƿ��Ƽ Ŭ������ ����� ������ ���߿�
 * �����ϰ� ���ȴ�.
 */
public class DBUtil {
	/*
	 * static final String driver = "oracle.jdbc.driver.OracleDriver"; static
	 * final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	 */

	// MySQL �����ͺ��̽� ������ ��
	/*
	 * static final String driver = "com.mysql.jdbc.Driver"; static final String
	 * url = "jdbc:mysql://localhost:3306/jdbc";
	 */

	// H2 �����ͺ��̽� ������ ��
	static final String driver = "oracle.jdbc.OracleDriver";
	static final String url = "jdbc:oracle:thin:@localhost:1521:xe";

	/*
	 * Connection ��ü ����
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() throws Exception {
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, "hr", "hr");

		return con;
	}

	// �߰�
	public static void close(PreparedStatement pstmt, Connection con) {
		if (pstmt != null) {
			try {
				if (!pstmt.isClosed())
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				if (!pstmt.isClosed())
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void close(ResultSet rs, PreparedStatement pstmt, Connection con) {
		if (rs != null) {
			try {
				if (!rs.isClosed())
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		close(pstmt, con);
	}

}
