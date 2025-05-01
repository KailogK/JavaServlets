package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (username != null && password != null && email != null) {
            try {
                // Database connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", 
                    "root", 
                    "mypass"
                );

                // SQL query to insert the user
                String sql = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, email);

                // Execute the query
                int rowsInserted = ps.executeUpdate();

                if (rowsInserted > 0) {
                    // Redirect to a success page or message
                    response.sendRedirect("index.jsp");
                } else {
                    // Redirect to an error page or message
                    response.sendRedirect("error.html");
                }

                ps.close();
                con.close();
                
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                // Handle exceptions
                response.sendRedirect("error.html");
            }
        } else {
            response.sendRedirect("error.html");
        }
    }
}
