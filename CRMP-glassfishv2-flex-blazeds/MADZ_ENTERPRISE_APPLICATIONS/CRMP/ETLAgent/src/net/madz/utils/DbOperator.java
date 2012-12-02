package net.madz.utils;

/**
 * <p>Title: DBOperator</p>
 *
 * <p>Description: DB Operating Tool</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: ultra Share</p>
 *
 * @author Barry
 * @version 1.0
 */

//import sun.jdbc.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbOperator {
	public Connection conn = null;

	public Statement st = null;
	public PreparedStatement ps = null;
	public String m_errMsg = "";
	// private static final int MAX_NUMBER = 10000;
	// private static final String MIGRATE_URL =
	// "jdbc:mysql://grassland01:24444/zapps01";
	// private static final int BATCH_SIZE = 200;
	private static final String LOCAL_PASS = "1q2w3e4r5t";
	private static final String LOCAL_USER = "root";
	private static final String LOCAL_URL = "jdbc:mysql://localhost:3306/crmp_1_db?useUnicode=true&amp;CharacterEncoding=GBK";

	public DbOperator(String driverName, String url, String user, String password) {
		getConnection(driverName, url, user, password);
	}

	public DbOperator() {
		getConnection("com.mysql.jdbc.Driver", LOCAL_URL, LOCAL_USER, LOCAL_PASS);
	}

	public DbOperator(String url, String username, String password) {
		getConnection("com.mysql.jdbc.Driver", url, username, password);
	}

	public void getConnection(String driverName, String url, String user, String password) {
		try {
			Class.forName(driverName).newInstance();
			conn = DriverManager.getConnection(url, user, password);
			st = conn.createStatement();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public PreparedStatement getPreparedStatement(String preparedStatementStr) {
		try {
			ps = conn.prepareStatement(preparedStatementStr);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return ps;
	}

	public ResultSet executePreparedStatementQuery() throws Exception {
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs == null) {
				throw new Exception();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return rs;
	}

	/**
	 * 
	 * @param sql
	 *            String
	 * @return CachedRowSet
	 */
	public ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("SQL Query Execute Error: ");
			System.out.println(sql);
		}
		return rs;
	}

	public String getPk(String tablename) {
		try {
			DatabaseMetaData meta = conn.getMetaData();

			ResultSet rsPK = meta.getPrimaryKeys(null, null, tablename);
			rsPK.getMetaData();

			StringBuilder pks = new StringBuilder();
			while (rsPK.next()) {
				if (pks.length() > 0) {
					pks.append(",");
				}
				pks.append(rsPK.getString(4));
			}
			return pks.toString();
		} catch (SQLException sqlex) {
			return "";
		}
	}

	public boolean simpleUpdate(String sql) {
		try {
			st.execute(sql);
			return true;
		} catch (SQLException ex) {
			return false;
		}
	}

	public boolean simplePSUpdate() {
		try {
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void addBatchSql(String sql) {
		try {
			st.addBatch(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("SQL Added Execute Error: ");
			System.out.println(sql);
		}
	}

	public boolean batchUpdate() {
		try {
			conn.setAutoCommit(false);
			st.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException ex1) {
			}
			return false;
		}
	}

	public void closeAll() {
		try {
			if (null != st) {
				st.close();
			}
			if (null != ps) {
				ps.close();
			}
			if (null != conn) {
				conn.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
