package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Created by richard on 3/29/15.
 */
public class Servlet extends javax.servlet.http.HttpServlet {

    private static final long serialVersionUID = 1L;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/cs319";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "cs329";

}
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {

        }
        String sql;

        BufferedReader br = request.getReader();
        String str;
        // parse input recieved to a json array.
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        JSONArray jArr = new JSONArray();

        System.out.println(sb.toString());

        JSONObject json = new JSONObject(sb.toString());
        String condition = json.getString("condition");
        String username = json.getString("username");
        String password = json.getString("password");

        if (condition.equals("register")) {
            try {

                Statement stmt = conn.createStatement();

                sql = "SELECT * FROM users WHERE username = '" + username
                        + "';";
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    return;
                }

                sql = "INSERT INTO users VALUES('" + username + "', '"
                        + password + "', '" + "');";
                stmt.executeUpdate(sql);

                // Clean-up environment
                stmt.close();
                conn.close();
            } catch (Exception se) {
                // Handle errors for JDBC
                se.printStackTrace();
            }
        }

        if (condition.equals("set")) {
            try {

                Statement stmt = conn.createStatement();

                sql = "INSERT INTO ALARMS VALUES( '"
                        + json.getString("username")
                        + "', '"
                        + json.getString("time")
                        + "', '"
                        + "' )";
                stmt.executeUpdate(sql);

                // Clean-up environment
                stmt.close();
                conn.close();
            } catch (Exception se) {
                // Handle errors for JDBC
                se.printStackTrace();
            }

        } else if (condition.equals("remove")) {
            try {

                Statement stmt = conn.createStatement();

                sql = "DELETE INTO ALARMS VALUES('" + username + "', '"
                        + password + "', '" + json.getString("time") + "');";
                stmt.executeUpdate(sql);

                // Clean-up environment
                stmt.close();
                conn.close();
            } catch (Exception se) {
                // Handle errors for JDBC
                se.printStackTrace();
            }
        }



                // retuns result
                PrintWriter out = response.getWriter();
                System.out.println((new (jjdl.AlarmsToRemove()));
                out.print(jArr.toString());
                out.close();

                // Clean-up environment
                stmt.close();
                conn.close();

    }
}
