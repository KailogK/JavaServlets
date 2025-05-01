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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Book;
import models.Review;

/**
 * Servlet implementation class Customer
 */
@WebServlet("/WriterServlet")
public class WriterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public WriterServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();
        String username = (String) session.getAttribute("email");
        String password = (String) session.getAttribute("password");
        
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Showing Books</title>");
        out.println("<link rel='stylesheet' href='./css/index.css' type='text/css'>");

        out.println("<style>");
        out.println(".description { display: none; color: white; background-color: rgba(0, 0, 0, 0.5); padding: 10px; border-radius: 5px; margin-top: 5px; }");
        out.println(".review-button { position: relative; display: inline-block; padding: 5px 10px; color: #03e9f4; font-size: 10px; text-decoration: none; text-transform: uppercase; overflow: hidden; transition: .25s; letter-spacing: 2px; background-color: transparent; border: none; }");
        out.println(".review-button:hover { background: #03e9f4; color: #fff; border-radius: 5px; box-shadow: 0 0 5px #03e9f4, 0 0 25px #03e9f4, 0 0 50px #03e9f4, 0 0 100px #03e9f4; }");
        out.println(".login-box { width: 800px; margin: 0 auto; }");
        out.println("input[type='number'] { width: 50px; background-color: transparent; color: white; border: 1px solid white; padding: 5px; text-align: center; }");
        out.println("table#bookTable { width: 100%; border-collapse: separate; border-spacing: 0 10px; }");
        out.println("table#bookTable th, table#bookTable td { padding: 10px 20px; text-align: center; vertical-align: middle; }");
        out.println(".transparent-input { background-color: transparent; border: 1px solid white; color: white; padding: 5px; font-size: 14px; width: 200px; margin-right: 10px; }");
        out.println(".transparent-button { background-color: transparent; border: 1px solid white; color: white; padding: 5px 10px; font-size: 14px; cursor: pointer; transition: background-color 0.3s ease; }");
        out.println(".transparent-button:hover { background-color: rgba(255, 255, 255, 0.2); }");
        out.println("</style>");
        out.println("<script>");
	    out.println("function toggleDescription(id, button) {");
	    out.println("  event.preventDefault();");
	    out.println("  var element = document.getElementById(id);");
	    out.println("  if (element.style.display === 'none' || element.style.display === '') {");
	    out.println("    element.style.display = 'block';");
	    out.println("    button.textContent = 'Hide Reviews';");
	    out.println("  } else {");
	    out.println("    element.style.display = 'none';");
	    out.println("    button.textContent = 'Show Reviews';");
	    out.println("  }");
	    out.println("}");

        out.println("document.addEventListener('DOMContentLoaded', function() {");
        out.println("    var inputs = document.querySelectorAll('#bookTable input[type=\"number\"]');");
        out.println("    inputs.forEach(input => {");
        out.println("        input.addEventListener('input', checkInputs);");
        out.println("    });");
        out.println("    checkInputs(); // Initial check in case there are default values");
        out.println("});");
        out.println("</script>");

        out.println("</head>");
        out.println("<body style='background-image: url(indexbg.jpg);'>");
        out.println("<div class='login-box'>");

        try {
            if (action.equals("See your books")) {
                out.println("<h2 id='h2'>Your books</h2>");
                out.println("<form action=WriterServlet>");
                out.println("<center>");
                out.println("<table id='bookTable' style='color: white;'>");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th scope='col'>Title</th>");
                out.println("<th scope='col'>Price</th>");
                out.println("<th scope='col'>Rating/5</th>");
                out.println("<th scope='col'>Sales</th>");
                out.println("<th scope='col'>Date</th>");
                out.println("<th scope='col'>Reviews</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");

                List<Book> books = findBooksByUsernameAndPassword(username, password);

                int index = 0;
                while(index < books.size()) {
                	String htmlRow;
                    if (books.get(index).getReviews().size() != 0) {htmlRow = createHTMLRow(books.get(index).getTitle(), books.get(index).getPrice(), (books.get(index).getRate()*1.0)/books.get(index).getReviews().size(), books.get(index).getSales(), findReviewsByBookId(books.get(index).getId()), books.get(index).getPublishDate(), index); }
                    else { htmlRow = createHTMLRow(books.get(index).getTitle(), books.get(index).getPrice(), 0, books.get(index).getSales(), findReviewsByBookId(books.get(index).getId()), books.get(index).getPublishDate(), index); }
                    out.println(htmlRow);
                    index += 1;
                }

                out.println("</tbody>");
                out.println("</table>");
                out.println("</center>");
                out.println("</form>");
            } else if (action.equals("Logout")) {
                session.invalidate();
                response.sendRedirect("index.jsp");
            }
        } catch(Exception e) {
            out.println("Database connection problem");
            System.out.println(e);
        }
	    out.println("<center><button type='button' onclick=\"window.location.href='writer.jsp'\">Return to Home</button>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
    
    private List<Book> findBooksByUsernameAndPassword(String username, String password) throws SQLException, ClassNotFoundException {
        List<Book> books = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

            // Query to fetch books based on writer's username and password
            String query = "SELECT b.bookId, b.title, b.price, b.rate, b.sales, b.writerId, b.publishDate, r.initialText " +
                            "FROM Book b LEFT JOIN Review r ON b.bookId = r.bookId " +
                            "WHERE b.writerId = (SELECT writerId FROM Writer WHERE email = ? AND password = ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            Map<Integer, Book> bookMap = new HashMap<>();

            while (rs.next()) {
                int bookId = rs.getInt("bookId");
                String title = rs.getString("title");
                int price = rs.getInt("price");
                Integer rate = rs.getInt("rate");
                Integer sales = rs.getInt("sales");
                String writerId = rs.getString("writerId");
                Date publishDate = rs.getDate("publishDate");

                // Check if the book is already in the map
                Book currentBook = bookMap.get(bookId);
                if (currentBook == null) {
                    // Create a new book if it's not yet added
                    currentBook = new Book(bookId, title, null, writerId, price, rate, sales, new ArrayList<>(), publishDate);
                    bookMap.put(bookId, currentBook);
                }

                // Check if there is a review for this book
                String reviewText = rs.getString("initialText");
                if (reviewText != null) {
                    Review review = new Review(reviewText);
                    currentBook.getReviews().add(review);
                }
            }

            // Add all books from the map to the final list
            books.addAll(bookMap.values());

        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return books;
    }

    
    private List<Review> findReviewsByBookId(int bookId) throws SQLException, ClassNotFoundException {
        List<Review> reviews = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

            // Query to fetch reviews for a specific book
            String query = "SELECT reviewId, initialText, writerAnswer FROM Review WHERE bookId = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int reviewId = rs.getInt("reviewId");
                String initialText = rs.getString("initialText");
                String writerAnswer = rs.getString("writerAnswer");

                // Create a Review object and add it to the list
                Review review = new Review(reviewId, initialText, writerAnswer);
                reviews.add(review);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }

        return reviews;
    }


    private String printReviews(List<Review> reviews) {
        String text = "";
        int counter = 1;
        for (Review review : reviews) {
            text += counter + ".\n" + review.toString();
            counter++;
            if (review.getWriterText()==null) {
                text += "<div style='margin-top: 10px;'>";
                text += "<input type='text' name='commentField' class='transparent-input' placeholder='Enter your comment'>";
                text += "<button class='transparent-button' type=submit name='action' value='See your books'>Submit " + "(" + (counter-1) + ")" + "</button>";
                text += "</div>";
            }
        }
        return text;
    }

    private String createHTMLRow(String title, Integer price, double rate, Integer sales, List<Review> reviews, Date publishDate, int index) {
        StringBuilder row = new StringBuilder();
        
        // Create the main book row with details
        row.append("<tr>");
        row.append("<td scope='row' data-label='title'>" + title + "</td>");
        row.append("<td data-label='price'>" + price + "$</td>");
        row.append("<td data-label='rate'>" + rate + "</td>");
        row.append("<td data-label='sales'>" + sales + "</td>");
        row.append("<td data-label='date'>" + publishDate + "</td>");
        row.append("<td data-label='description'><button class='review-button' onclick=\"toggleDescription('description" + index + "', this)\">Show Reviews</button></td>");
        row.append("</tr>");
        
        // Add the hidden row for reviews
        row.append("<tr>");
        row.append("<td colspan='6'>");
        row.append("<div id='description" + index + "' class='description' style='display:none;'>");
        
        // Display each review and check for writer's reply
        for (Review review : reviews) {
            row.append("<p>User Review: " + review.getInitialText() + "</p>");
            
            // Check if the writer has responded
            if (review.getWriterText() != null && !review.getWriterText().isEmpty()) {
                row.append("<p>Writer Response: " + review.getWriterText() + "</p>");
            } else {
                // If no response, provide input for writer to answer
                row.append("<form method='post' action='WriterServlet?action=submitAnswer&reviewId=" + review.getId() + "'>");
                row.append("<input type='text' name='writerAnswer' placeholder='Enter your answer here'>");
                row.append("<button type='submit' class='review-button'>Submit Answer</button>");
                row.append("</form>");
            }
        }
        
        row.append("</div>");
        row.append("</td>");
        row.append("</tr>");
        
        return row.toString();
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("action: " + action);

        if (action != null && action.equals("submitAnswer")) {
            int reviewId = Integer.parseInt(request.getParameter("reviewId"));
            String writerAnswer = request.getParameter("writerAnswer");

            if (writerAnswer != null && !writerAnswer.trim().isEmpty()) {
                Connection con = null;
                PreparedStatement ps = null;

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

                    // Update the review with the writer's answer
                    String updateQuery = "UPDATE Review SET writerAnswer = ? WHERE reviewId = ?";
                    ps = con.prepareStatement(updateQuery);
                    ps.setString(1, writerAnswer);
                    ps.setInt(2, reviewId);
                    ps.executeUpdate();

                    // Redirect back to the "See your books" page
                    response.sendRedirect("WriterServlet?action=See+your+books");
                } catch (Exception e) {
                    e.printStackTrace();
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
    }

}
