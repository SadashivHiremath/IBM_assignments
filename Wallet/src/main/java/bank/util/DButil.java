package bank.util;

public class DButil {

    public static java.sql.Connection getConnection()
            throws ClassNotFoundException, java.sql.SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return java.sql.DriverManager.getConnection("jdbc:mysql://localhost/bankdata","root","root");
    }
}
