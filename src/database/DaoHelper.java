package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by wzhuo on 2015/11/14.
 */
public class DaoHelper {

    public static Connection getConn(){
        Connection conn=null;
        String url="jdbc:mysql://localhost:3306/filmdata";
        String user="root";
        String pwd="";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn= DriverManager.getConnection(url,user,pwd);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    public static void closeConn(Connection conn) {
        if (conn!=null){
            try {
                conn.close();
                conn=null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
