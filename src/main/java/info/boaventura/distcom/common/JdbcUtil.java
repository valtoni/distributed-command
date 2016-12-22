package info.boaventura.distcom.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe utilitária para fechar conexões. 
 * 
 * @author Valtoni Boaventura
 *
 */
public final class JdbcUtil {

	public static void close(Connection conn) {
		if (conn == null) {
			return;
		}
		try {
			conn.close();
		} catch (SQLException e) {
		}
	}

	public static void close(PreparedStatement preparedStatement) {
		close(preparedStatement, false);
	}
	
	public static void close(PreparedStatement preparedStatement, boolean closeConnection) {
		if (preparedStatement == null) {
			return;
		}
		if (closeConnection) {
			try {
				close(preparedStatement.getConnection());
			} catch (SQLException e) {
			}
		}
		try {
			preparedStatement.close();
		} catch (SQLException e) {
		}
	}

	public static void close(Statement statement) {
		close(statement, false);
	}
	
	public static void close(Statement statement, boolean closeConnection) {
		if (statement == null) {
			return;
		}
		if (closeConnection) {
			try {
				close(statement.getConnection());
			} catch (SQLException e) {
			}
		}
		try {
			statement.close();
		} catch (SQLException e) {
		}
	}

	public static void close(ResultSet resultSet) {
		close(resultSet, false, false);
	}

	public static void close(ResultSet resultSet, boolean closeStatement) {
		close(resultSet, closeStatement, false);
	}
	
	public static void close(ResultSet resultSet, boolean closeStatement, boolean closeConnection) {
		if (resultSet == null) {
			return;
		}
		if (closeConnection && !closeStatement) {
			throw new RuntimeException("Cannot close connection without close statement: please check your settings");
		}

		Statement statement;
		Connection connection;
		try {
			statement = resultSet.getStatement();
		} catch (SQLException e) {
			statement = null;
		}
		try {
			connection = statement.getConnection();
		} catch (SQLException e) {
			connection = null;
		}
		if (closeStatement) {
			close(statement);
		}
		if (closeConnection) {
			close(connection);
		}

	}
	
}
