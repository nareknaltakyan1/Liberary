package server;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import users.User;

public class SqlConnection {

	private static Connection conn;

	public static Connection getConnection() throws SQLException, IOException {
		if (conn == null) {

			Properties props = new Properties();
			try (InputStream in = Files.newInputStream(Paths.get(
					"C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\LiberaryWebApplication\\WebContent\\database.properties"))) {
				props.load(in);
			}
			String url = props.getProperty("url");
			String username = props.getProperty("username");
			String password = props.getProperty("password");
			conn = DriverManager.getConnection(url, username, password);
		}
		return conn;
	}

	public static void InsertUser(String login, String password) {
		try (Connection conn = SqlConnection.getConnection()) {

			String sql = "INSERT INTO Users (login, password) Values (?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, password);

			int rows = preparedStatement.executeUpdate();

			System.out.printf("%d rows added", rows);
		}

		catch (Exception ex) {
			System.out.println("Command failed...");

			System.out.println(ex);
		}
	}

	public static User SelectUser(String login, String password) throws SQLException, IOException {
		User user = null;
		try (Connection conn = SqlConnection.getConnection()) {
			PreparedStatement preparedStatement = conn
					.prepareStatement("SELECT * FROM Users WHERE login = ? and password = ?");
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				int id = resultSet.getInt("Id");
				String userLogin = resultSet.getString("login");
				String userPassword = resultSet.getString("password");
				System.out.printf("%d. %s - %s \n", id, userLogin, userPassword);
				user = new User(id, userLogin);

			}
		} catch (Exception ex) {
			System.out.println("Command failed...");

			System.out.println(ex);
		}
		return user;
	}

	public static List<String> SelectUserList() throws SQLException, IOException {
		List<String> list = new LinkedList<String>();
		try (Connection conn = SqlConnection.getConnection()) {
			PreparedStatement preparedStatement = conn
					.prepareStatement("SELECT login FROM Users");

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String userLogin = resultSet.getString("login");
				list.add(userLogin);
			
			}
		} catch (Exception ex) {
			System.out.println("Command failed...");

			System.out.println(ex);
		}
		return list;
	}
}
