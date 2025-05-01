package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Book;
import models.Review;
import models.User;
import models.Writer;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        // Get the action parameter from the form
        String action = request.getParameter("action");
        
        // Get the session and set the action attribute
        HttpSession session = request.getSession();
        if (action != null) {
            session.setAttribute("action", action);
        }

        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");
        		PreparedStatement ps = con.prepareStatement("");){
        	
        }catch(Exception e) {
        	
        }
        
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Showing Books</title>");
        out.println("<link rel='stylesheet' href='./css/index.css' type='text/css'>");

        out.println("<style>");
        out.println(".description { display: none; color: white; background-color: rgba(0, 0, 0, 0.5); padding: 10px; border-radius: 5px; }");
        out.println(".review-button { display: inline-block; text-align: center; color: #03e9f4; font-size: 12px; text-decoration: none; text-transform: uppercase; overflow: hidden; transition: .25s; letter-spacing: 2px; background-color: transparent; border: none; }");
        out.println(".review-button:hover { background: #03e9f4; color: #fff; border-radius: 5px; box-shadow: 0 0 5px #03e9f4, 0 0 25px #03e9f4, 0 0 50px #03e9f4, 0 0 100px #03e9f4; }");
        out.println(".login-box { width: 1000px; margin: 0 auto; }");
        out.println(".login-box2 { width: 500px; margin: 0 auto; }");
        out.println("input[type='number'] { width: 50px; background-color: transparent; color: white; border: 1px solid white; padding: 5px; text-align: center; }");
        out.println("table#bookTable { width: 100%; border-collapse: separate; border-spacing: 0 10px; }");
        out.println("table#bookTable th, table#bookTable td { padding: 10px 20px; text-align: center; vertical-align: middle; }");
        out.println(".transparent-input { background-color: transparent; border: 1px solid white; color: white; padding: 5px; font-size: 14px; width: 200px; margin-right: 10px; }");
        out.println(".transparent-button { background-color: transparent; border: 1px solid white; color: white; padding: 5px 10px; font-size: 14px; cursor: pointer; transition: background-color 0.3s ease; }");
        out.println(".transparent-button:hover { background-color: rgba(255, 255, 255, 0.2); }");
        out.println(".add-book-form { width: 300px; margin: 0 auto; text-align: center; }"); 
        out.println(".add-book-form .form-group { margin-bottom: 10px; }");
        out.println(".add-book-form .form-group label { display: block; text-align: left; color: white; }");
        out.println(".add-book-form .form-group input[type='text'], .add-book-form .form-group input[type='number'] { width: 100%; padding: 5px; background-color: transparent; color: white; border: 1px solid white; text-align: left; }");
        out.println("</style>");
        out.println("<script>");

        out.println("</script>");

        out.println("</head>");
        out.println("<body style='background-image: url(indexbg.jpg);'>");
        out.println("<div class='login-box'>");
        
        try {
        	if (action.equals("Delete Books")) {
        	    out.println("<h2 id='h2'>Book list</h2>");
        	    out.println("<style>");
        	    out.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; }");
        	    out.println("th, td { padding: 15px; text-align: left; border-bottom: 1px solid #ddd; text-align: center; }");
        	    out.println("</style>");
        	    out.println("<center>");
        	    out.println("<table id='bookTable' style='color: white;'>");
        	    out.println("<thead>");
        	    out.println("<tr>");
        	    out.println("<th scope='col'>Author</th>");
        	    out.println("<th scope='col'>Title</th>");
        	    out.println("<th scope='col'>Price</th>");
        	    out.println("<th scope='col'>Rate</th>");
        	    out.println("<th scope='col'>Sales</th>");
        	    out.println("<th scope='col'>Date</th>");
        	    out.println("<th scope='col'>Manage</th>");
        	    out.println("</tr>");
        	    out.println("</thead>");
        	    out.println("<tbody>");
        	    
        	    // Get the list of books
        	    List<Book> books = findAllBooks();
        	    
        	    for (Book book : books) {
        	        out.println("<tr>");
        	        out.println("<td>" + book.getWriter() + "</td>");
        	        out.println("<td>" + book.getTitle() + "</td>");
        	        out.println("<td>" + book.getPrice() + "</td>");
        	        out.println("<td>" + book.getRate() + "</td>");
        	        out.println("<td>" + book.getSales() + "</td>");
        	        out.println("<td>" + book.getPublishDate() + "</td>");
        	        out.println("<td>");
        	        
        	        // Separate form for each book with hidden field containing book ID
        	        out.println("<form action='AdminServlet?action=processDeleteBook' method='post'>");
        	        out.println("<input type='hidden' name='bookTitle' value='" + book.getTitle() + "'>");
        	        out.println("<button type='submit' class='review-button'>Delete</button>");
        	        out.println("</form>");
        	        
        	        out.println("</td>");
        	        out.println("</tr>");
        	    }

        	    out.println("</tbody>");
        	    out.println("</table>");
        	    out.println("</center>");
        	    
        	} else if(action.equals("Add Books")) {
        	    out.println("<h2>Add New Book</h2>");
        	    out.println("<form action='AdminServlet?action=processAddBook' method='post' class='add-book-form'>");
        	    
        	    out.println("<div class='form-group'>");
        	    out.println("<label for='title'>Title:</label>");
        	    out.println("<input type='text' id='title' name='title' required>");
        	    out.println("</div>");
        	    
        	    out.println("<div class='form-group'>");
        	    out.println("<label for='writer'>Writer:</label>");
        	    
        	    out.println("<div class='user-box'>");
        	    out.println("<select id='writer' name='writer' required>");
        	    
        	    // Fetch list of writers from the database
        	    List<Writer> writers = findAllWriters(); // Assuming you have a method that returns all writers
        	    for (Writer writer : writers) {
        	        out.println("<option value='" + writer.getName() + "'>" + writer.getName() + "</option>");
        	    }
        	    
        	    out.println("</select>");
        	    out.println("</div>");
        	    out.println("</div>");
        	    
        	    out.println("<div class='form-group'>");
        	    out.println("<label for='price'>Price:</label>");
        	    out.println("<input type='number' id='price' name='price' min='0' max='2000' required>");
        	    out.println("</div>");
        	    
        	    out.println("<input type='submit' value='Add Book' class='review-button'>");
        	    out.println("</form>");
        	
        	} else if (action.equals("Manage Writers")) {
                out.println("<h2 id='h2'>Writer list</h2>");
                out.println("<style>");
                out.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; }");
                out.println("th, td { padding: 15px; text-align: left; border-bottom: 1px solid #ddd; text-align: center; }");
                out.println("</style>");
                out.println("<center>");
                out.println("<table id='writerTable' style='color: white;'>");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th scope='col'>Number</th>");
                out.println("<th scope='col'>Writer</th>");
                out.println("<th scope='col'>Demote to User</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");

                // Retrieve the list of writers
                List<Writer> writers = findAllWriters();

                int index = 0;
                for (Writer writer : writers) {
                    out.println("<tr>");
                    out.println("<td>" + (index + 1) + "</td>");
                    out.println("<td>" + writer.getName() + "</td>");
                    out.println("<td>");

                    // Separate form for each writer row
                    out.println("<form action='AdminServlet?action=demote' method='post'>");
                    out.println("<input type='hidden' name='email' value='" + writer.getEmail() + "'>");
                    out.println("<button type='submit' class='review-button'>Demote</button>");
                    out.println("</form>");

                    out.println("</td>");
                    out.println("</tr>");
                    index++;
                }

                out.println("</tbody>");
                out.println("</table>");
                out.println("</center>");
                
            } else if(action.equals("Manage Users")) {
            	out.println("<h2 id='h2'>User list</h2>");
                out.println("<style>");
                out.println("table { width: 80%; margin: 20px auto; border-collapse: collapse; }");
                out.println("th, td { padding: 15px; text-align: left; border-bottom: 1px solid #ddd; text-align: center; }");
                out.println("</style>");
                out.println("<center>");
                out.println("<table id='userTable' style='color: white;'>");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th scope='col'>Number</th>");
                out.println("<th scope='col'>User</th>");
                out.println("<th scope='col'>Promote to Writer</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");

                // Create User objects
                List<User> users = findAllUsers();

                int index = 0;
                for (User user : users) {
                    out.println("<tr>");
                    out.println("<td>" + (index + 1) + "</td>");
                    out.println("<td>" + user.getUsername() + "</td>");
                    out.println("<td>");
                    
                    // Separate form for each user row
                    out.println("<form action='AdminServlet?action=promote' method='post'>");
                    out.println("<input type='hidden' name='email' value='" + user.getEmail() + "'>");
                    out.println("<button type='submit' class='review-button'>Promote</button>");
                    out.println("</form>");
                    
                    out.println("</td>");
                    out.println("</tr>");
                    index++;
                }

                out.println("</tbody>");
                out.println("</table>");
                out.println("</center>");
            } else if (action.equals("Logout")) {
                session = request.getSession(false);  
                session.invalidate();
                response.sendRedirect("index.jsp");
            }
        } catch(Exception e) {
            out.println("Database connection problem");
            System.out.println(e);
        }
	    out.println("<center><button type='button' onclick=\"window.location.href='admin.jsp'\">Return to Home</button>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
	}
	
	private List<User> findAllUsers() throws SQLException, ClassNotFoundException {
	    List<User> users = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

	        String query = "SELECT * FROM User";
	        ps = con.prepareStatement(query);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            String email = rs.getString("email");
	            String password = rs.getString("password");
	            String username = rs.getString("username");

	            users.add(new User(password, email, username));
	        }
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return users;
	}
	
	private List<Writer> findAllWriters() throws SQLException, ClassNotFoundException {
	    List<Writer> writers = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

	        String query = "SELECT * FROM Writer";
	        ps = con.prepareStatement(query);
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            String writerId = rs.getString("writerId");
	            String email = rs.getString("email");
	            String name = rs.getString("name");
	            String password = rs.getString("password");

	            writers.add(new Writer(password, email, name, writerId));
	        }
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return writers;
	}
	
	private List<Book> findAllBooks() throws SQLException, ClassNotFoundException {
	    List<Book> books = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement psBooks = null;
	    ResultSet rsBooks = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

	        String queryBooks = "SELECT b.bookId, b.title, b.price, b.rate, b.sales, b.writerId, b.publishDate " +
	                            "FROM Book b";
	        psBooks = con.prepareStatement(queryBooks);
	        rsBooks = psBooks.executeQuery();

	        while (rsBooks.next()) {
	            int bookId = rsBooks.getInt("bookId");
	            String title = rsBooks.getString("title");
	            int price = rsBooks.getInt("price");
	            Integer rate = rsBooks.getInt("rate");
	            Integer sales = rsBooks.getInt("sales");
	            String writerId = rsBooks.getString("writerId");
	            Date publishDate = rsBooks.getDate("publishDate");

	            // Fetch reviews for the current book
	            List<Review> reviews = new ArrayList<>();

	            // Fetch writer name for the current book
	            String query = "SELECT name FROM Writer WHERE writerId = ?";
	            PreparedStatement ps = con.prepareStatement(query);
	            ps.setString(1, writerId);
	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                String writer = rs.getString("name");
	             // Create the book object and add it to the list
		            books.add(new Book(title, writer, writerId, price, rate, sales, reviews, publishDate));
	            }

	            
	        }
	    } finally {
	        try {
	            if (rsBooks != null) rsBooks.close();
	            if (psBooks != null) psBooks.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return books;
	}	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println(action.toString());
	    HttpSession session = request.getSession();

        if (action != null && action.equals("demote")) {
            String writerEmail = request.getParameter("email");  // Retrieve the writer's email
            System.out.println("writerEmail: " + writerEmail);

            if (writerEmail != null && !writerEmail.isEmpty()) {
                try {
                    demoteToUser(writerEmail);  // Demote the writer
                    response.sendRedirect("AdminServlet?action=Manage Writers");
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid writer email");
            }
        } else if (action != null && action.equals("promote")) {
            String userEmail = request.getParameter("email");  // Retrieve the email
            System.out.println("userEmail: " + userEmail);

            if (userEmail != null && !userEmail.isEmpty()) {
                try {
                    promoteToWriter(userEmail);
                    response.sendRedirect("AdminServlet?action=Manage Users");
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user email");
            }
        } else if (action != null && action.equals("processAddBook")) {
            String title = request.getParameter("title");
            String writerName = request.getParameter("writer");
            int price = Integer.parseInt(request.getParameter("price")); // Assuming price is an integer

            if (title != null && !title.isEmpty() && writerName != null && !writerName.isEmpty()) {
                try {
                    // Step 1: Retrieve writerId based on writerName
                    String writerId = getWriterIdByName(writerName);

                    if (writerId != null) {
                        // Step 2: Add the book with writerId
                        addBook(title, writerId, price);

                        // Redirect to a success page or manage books page
            	        session.setAttribute("purchaseSuccessMessage", "Book added successfully!");
                        response.sendRedirect("admin.jsp");
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Writer with name " + writerName + " not found.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle database error appropriately
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required parameters (title, writer, price)");
            }
        } else if (action != null && action.equals("processDeleteBook")) {
            String bookTitle = request.getParameter("bookTitle");
            System.out.println("Book ID to delete: " + bookTitle);

            if (bookTitle != null && !bookTitle.isEmpty()) {
                try {
                    deleteBookByTitle(bookTitle);
                    
                    // Redirect to the main page after successful deletion
        	        session.setAttribute("purchaseSuccessMessage", "Book deleted successfully!");
                    response.sendRedirect("admin.jsp");
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book Title");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }
    
    private String getWriterIdByName(String writerName) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String writerId = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

            String query = "SELECT writerId FROM Writer WHERE name = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, writerName);
            rs = ps.executeQuery();

            if (rs.next()) {
                writerId = rs.getString("writerId");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Error retrieving writerId for writer name: " + writerName, e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return writerId;
    }
    
    private void deleteBookByTitle(String title) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Step 1: Load the MySQL driver and connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

            // Step 2: Find the bookId based on the title
            String findBookIdQuery = "SELECT bookId FROM Book WHERE title = ?";
            ps = con.prepareStatement(findBookIdQuery);
            ps.setString(1, title);
            rs = ps.executeQuery();

            int bookId = -1;
            if (rs.next()) {
                bookId = rs.getInt("bookId");
            } else {
                // No book found with the given title
                System.out.println("No book found with the given title: " + title);
                return;
            }

            // Step 3: Delete related reviews before deleting the book
            String deleteReviewsQuery = "DELETE FROM Review WHERE bookId = ?";
            ps = con.prepareStatement(deleteReviewsQuery);
            ps.setInt(1, bookId);
            ps.executeUpdate();

            // Step 4: Delete the book using the bookId
            String deleteBookQuery = "DELETE FROM Book WHERE bookId = ?";
            ps = con.prepareStatement(deleteBookQuery);
            ps.setInt(1, bookId);
            ps.executeUpdate();

            System.out.println("Book with title '" + title + "' and its associated reviews have been deleted successfully.");

        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Error deleting book and reviews for title: " + title, e);
        } finally {
            // Close the resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void addBook(String title, String writerId, int price) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");
            
            String query = "INSERT INTO Book (title, price, rate, publishDate, writerId, sales) VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, title);
            ps.setInt(2, price);
            ps.setInt(3, 0);
            ps.setString(4, writerId);
            ps.setInt(5, 0);
            
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Error adding book with title: " + title, e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void promoteToWriter(String userEmail) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        System.out.println("promoteToWriter: " + userEmail);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

            //Retrieve the user's details
            String retrieveUserQuery = "SELECT * FROM User WHERE email = ?";
            ps = con.prepareStatement(retrieveUserQuery);
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //Insert into Writer table with the retrieved details
                String insertWriterQuery = "INSERT INTO Writer (writerId, email, name, password) VALUES (?, ?, ?, ?)";
                ps = con.prepareStatement(insertWriterQuery);
                ps.setString(1, generateUniqueWriterId());
                ps.setString(2, rs.getString("email"));
                ps.setString(3, rs.getString("username"));
                ps.setString(4, rs.getString("password"));
                ps.executeUpdate();

                // Step 3: Delete the user from the User table
                String deleteUserQuery = "DELETE FROM User WHERE email = ?";
                ps = con.prepareStatement(deleteUserQuery);
                ps.setString(1, userEmail);
                ps.executeUpdate();
            } else {
                throw new SQLException("User with email " + userEmail + " not found.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Error promoting user to writer with email: " + userEmail, e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void demoteToUser(String email) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

            // Step 1: Retrieve writer details
            String selectQuery = "SELECT * FROM writer WHERE email = ?";
            ps = con.prepareStatement(selectQuery);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String writerId = rs.getString("writerId");

                // Step 2: Delete books written by this writer
                String deleteBooksQuery = "DELETE FROM book WHERE writerId = ?";
                ps = con.prepareStatement(deleteBooksQuery);
                ps.setString(1, writerId);
                ps.executeUpdate();

                // Step 3: Insert into the user table
                String insertQuery = "INSERT INTO user (email, password, username) VALUES (?, ?, ?)";
                ps = con.prepareStatement(insertQuery);
                ps.setString(1, rs.getString("email"));
                ps.setString(2, rs.getString("password"));
                ps.setString(3, rs.getString("name"));
                ps.executeUpdate();

                // Step 4: Delete the writer from the writer table
                String deleteWriterQuery = "DELETE FROM writer WHERE email = ?";
                ps = con.prepareStatement(deleteWriterQuery);
                ps.setString(1, email);
                ps.executeUpdate();
            } else {
                throw new SQLException("Writer with email " + email + " not found.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Error demoting writer with email: " + email, e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    private String generateUniqueWriterId() {
        // Implement logic to generate a unique writerId, e.g., using UUID.randomUUID() or another suitable method
        return UUID.randomUUID().toString().substring(0, 8); // Example: Generate a random string
    }

    private void deleteWriter(String writerId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

            String query = "DELETE FROM Writer WHERE writerId = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, writerId);
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Error deleting writer with ID: " + writerId, e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
