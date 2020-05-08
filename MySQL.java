package your.package.here;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Code in this class is written by xShequ/Scott. (in 2020)
 * @author Scott/xShequ
 * You can use this code in any way you like and you DO NOT need to credit me.
 * I'm not responsible for any damage that this code will cause!
 * Have fun!
 */

public class MySQL {

    // Setting private values for the MySQL data connection.
    private String HOST = "";
    private String DATABASE = "";
    private String USER = "";
    private String PASSWORD = "";
    private Connection connection;

    /**
     *
     * @param host | Value used to specify the host. ( For example "localhost". )
     * @param database | Value used to specify the mysql database. ( For example "test". )
     * @param user | Value used to specify the mysql user. ( For example "root". )
     * @param password | Value used to specify the mysql password. ( For example "SuperSecurePassword123". )
     */
    public MySQL(String host, String database, String user, String password) {
        this.HOST = host;
        this.DATABASE = database;
        this.USER = user;
        this.PASSWORD = password;
        connect();
    }

    // The actual connection method.
    public void connect() {
        // Try to connect to MySQL. Print SQLException if the data or connection is invalid.
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + this.HOST + ":3306/" + this.DATABASE + "?autoReconnect=true", this.USER, this.PASSWORD);
            System.out.println("=============================================");
            System.out.println("|                                           |");
            System.out.println("|             MYSQL-CONNECTER               |");
            System.out.println("|      Connection was established! :)       |");
            System.out.println("|                                           |");
            System.out.println("=============================================");
        } catch (SQLException e) {
            System.out.println("=============================================");
            System.out.println("|                                           |");
            System.out.println("|             MYSQL-CONNECTER               |");
            System.out.println("|   Error while establishing connection!    |");
            System.out.println("|                                           |");
            System.out.println("=============================================");
            System.out.println("[ERROR]: " + e.getMessage());
        }
    }

    // Close the MySQL connection. Print SQLException if the connection could not be closed.
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("[MYSQL] Connection was successfully closed!");
            }
        } catch (SQLException exception) {
            System.out.println("[MySQL] Error while closing connection!");
            System.out.println("[MYSQL] Error: " + exception.getMessage());
        }
    }

    /**
     *
     * @param qry | Specify the query string.
     *
     * !!! REMEMBER MYSQL SYNTAX !!!
     */
    public void update(String qry) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(qry);
            statement.close();
        } catch (SQLException exception) {
            connect();
            exception.printStackTrace();
        }
    }

    /**
     * Simple boolean to check if MySQL is connected.
     * @return boolean
     */
    public boolean isConnected() {
        return connection != null;
    }

    /**
     * !!! REMEMBER MYSQL SYNTAX !!!
     * @param qry | Specify the query string.
     * @return ResultSet | Returns the actual set result specified by the query.
     */
    public ResultSet query(String qry) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(qry);
        } catch (SQLException exception) {
            connect();
            System.out.println("[ERROR] MySQL has thrown an unexpected SQLException!");
            System.out.println("[ERROR] Error:" + exception.getMessage());
        }
        return resultSet;
    }
}
