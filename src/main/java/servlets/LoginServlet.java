package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
//	private DataSource datasource = null;
//	
//	public void init() throws ServletException{
//		try {
//	
//			InitialContext ctx = new InitialContext();
//			datasource = (DataSource)ctx.lookup("java:comp/env/jdbc/LiveDataSource");
//		} catch(Exception e) {
//			throw new ServletException(e.toString());
//		}
//
//	}
	
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");

	    
	    response.setContentType("text/html; charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    request.setCharacterEncoding("UTF-8");
	    
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");
	        
	        // Query to check user type based on username and password
	        String query = "SELECT * FROM Admin WHERE email=? AND password=?";
	        ps = con.prepareStatement(query);
	        ps.setString(1, username);
	        ps.setString(2, password);
	        
	        rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            // User is an Admin
	        	HttpSession session = request.getSession();
	            session.setAttribute("username", username);
	            session.setAttribute("password", password);
	            request.getRequestDispatcher("admin.jsp").forward(request, response);
	            return;
	        }
	        
	        // Check if user is a Writer
	        query = "SELECT * FROM Writer WHERE email=? AND password=?";
	        ps = con.prepareStatement(query);
	        ps.setString(1, username);
	        ps.setString(2, password);
	        
	        rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            // User is a Writer
	        	HttpSession session = request.getSession();
	        	session.setAttribute("email", username);
	            session.setAttribute("username", rs.getString("name"));
	            session.setAttribute("password", password);
	            request.getRequestDispatcher("writer.jsp").forward(request, response);
	            return;
	        }
	        
	        // Check if user is a regular User
	        query = "SELECT * FROM User WHERE email=? AND password=?";
	        ps = con.prepareStatement(query);
	        ps.setString(1, username);
	        ps.setString(2, password);
	        
	        rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            // User is a regular User
	            HttpSession session = request.getSession();
	            session.setAttribute("username", rs.getString("username"));
	            //session.setAttribute("email", email);
	            session.setAttribute("password", password);
	            request.getRequestDispatcher("home.jsp").forward(request, response);
	            return;
	        }
	        
	        // If no matches found, show error message
	        request.setAttribute("errorMessage", "Invalid username or password");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
	        dispatcher.forward(request, response);
	        
	    } catch (ClassNotFoundException | SQLException e) {
	        //System.out.println("Exception occurred: " + e.getMessage());
	    	e.printStackTrace();
	    	// Handle exceptions (log, show error message, etc.)
	    } finally {
	        // Close resources in finally block to ensure they are always closed
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            System.out.println("Error closing resources: " + e.getMessage());
	        }
	    }
	}

}
