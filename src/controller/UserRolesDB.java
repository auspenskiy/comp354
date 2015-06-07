package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import model.Project;

public class UserRolesDB extends DataManager
{
	public static void create(String connectionString)
	{
		Connection c = null;
		Statement stmt = null;
		try
		{
			c = getConnection(connectionString);

			stmt = c.createStatement();
			String sql = "CREATE TABLE USERROLES " + "(USERID 		INTEGER,"
					+ " PROJECTID 	INTEGER," + " ROLEID  	INTEGER,"
					+ " PRIMARY KEY(USERID,PROJECTID),"
					+ " FOREIGN KEY(USERID) REFERENCES USERS(ID),"
					+ " FOREIGN KEY(PROJECTID) REFERENCES PROJECTS(ID),"
					+ " FOREIGN KEY(ROLEID) REFERENCES USERSROLESDICT(ROLEID))";
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		}
		catch (SQLException e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	//Role id 1 = manager
		public static void insert(String connectionString,
				int userID, int projectID, int roleID)
		{
			Connection c = null;
			Statement stmt = null;
			try
			{
				c = getConnection(connectionString);
				c.setAutoCommit(false);

				stmt = c.createStatement();
				String sql = "INSERT INTO USERROLES (USERID, PROJECTID, ROLEID) "
						+ "VALUES ( "
						+ userID
						+ ", "
						+ projectID
						+ ", "
						+ roleID
						+ ")";
				stmt.executeUpdate(sql);
				stmt.close();
				c.commit();
				c.close();
			}
			catch (SQLException e)
			{
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
			catch (Exception e)
			{
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
		
		public static int getProjectManagerIDByProjectID(String connectionString, int id)
		{
			int projectManagerID = 0;
			Connection c = null;
			Statement stmt = null;
			try
			{
				c = getConnection(connectionString);
				c.setAutoCommit(false);

				stmt = c.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT * FROM USERROLES WHERE PROJECTID = " + id + " AND ROLEID = 1;");
				projectManagerID = rs.getInt("USERID");
				rs.close();
				stmt.close();
				c.close();
			}
			catch (SQLException e)
			{
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
			catch (Exception e)
			{
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
			return projectManagerID;
		}
}
