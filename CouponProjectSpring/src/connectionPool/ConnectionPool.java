package connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 * this class creates the connection pool
 * 
 * 
 *
 */


public class ConnectionPool {
	
	
	private Stack<Connection> connections = new Stack<>();

	private static int counter = 0;
	
	private static ConnectionPool instance = new ConnectionPool();

	public static ConnectionPool getInstance() {
		return instance;
	}


	private ConnectionPool() {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < 10; i++) {
			try {
				connections.push(DriverManager.getConnection("jdbc:derby://localhost:1527/DB;create=true"));
				counter+=1;
			} 
			catch (SQLException e) {System.out.println(e.getMessage());}
		}
	}

	public Connection getConnection() throws InterruptedException {
		synchronized (connections){

			if(connections.isEmpty()) {
				connections.wait();

			}

			return connections.pop();
		}
	}
	public void restoreConnection(Connection conn) throws SQLException {
		synchronized (connections) {
			connections.push(conn);
			
			connections.notify();
			
			
		}
	}
	public void closeAllConnections(){
		for(Connection conn:connections) {
			while(connections.size()<counter) {
				try {
					connections.wait();
				} catch (InterruptedException e) {}
			}
			synchronized(connections) {
				try {
					conn.close();
				} catch (SQLException e) { }
			}
		}
	}
}
