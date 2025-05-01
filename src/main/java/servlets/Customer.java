package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Book;
import models.CartItem;
import models.Review;

/**
 * Servlet implementation class Customer
 */
@WebServlet("/Customer")
public class Customer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Customer() {
        super();
    }

    ArrayList<Integer> orders = new ArrayList<>();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        
        if (action != null && action.equals("Buy Books")) {
            String[] quantities = request.getParameterValues("quantity");
            if (quantities != null) {
                List<Integer> quantitiesList = new ArrayList<>();
                for (String quantity : quantities) {
                    try {
                        quantitiesList.add(Integer.parseInt(quantity));
                    } catch (NumberFormatException e) {
                        quantitiesList.add(0);
                    }
                }
                session.setAttribute("quantities", quantitiesList);
                System.out.println("Quantities: " + quantitiesList);
            }
            response.sendRedirect("home.jsp");
            //return;
        }

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Showing Books</title>");
        out.println("<link rel='stylesheet' href='./css/index.css' type='text/css'>");

        // Add custom CSS for the review buttons
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

        out.println("function checkInputs() {");
        out.println("    var inputs = document.querySelectorAll('#bookTable input[type=\"number\"]');");
        out.println("    var buyButton = document.getElementById('buyButton');");
        out.println("    var showButton = false;");
        out.println("    inputs.forEach(input => {");
        out.println("        if (input.value > 0) {");
        out.println("            showButton = true;");
        out.println("        }");
        out.println("    });");
        out.println("    buyButton.style.display = showButton ? 'block' : 'none';");
        out.println("}");

        out.println("document.addEventListener('DOMContentLoaded', function() {");
        out.println("    var inputs = document.querySelectorAll('#bookTable input[type=\"number\"]');");
        out.println("    inputs.forEach(input => {");
        out.println("        input.addEventListener('input', checkInputs);");
        out.println("    });");
        out.println("    checkInputs(); // Initial check in case there are default values");
        out.println("});");
        
        out.println("function toggleAddToCart() {");
        out.println("    var inputs = document.querySelectorAll('input[type=\"number\"]');");
        out.println("    var addToCartButton = document.getElementById('addToCartButton');");
        out.println("    var showButton = false;");
        out.println("    inputs.forEach(function(input) {");
        out.println("        if (parseInt(input.value) > 0) {");
        out.println("            showButton = true;");
        out.println("        }");
        out.println("    });");
        out.println("    if (showButton) {");
        out.println("        addToCartButton.style.display = 'block';");
        out.println("    } else {");
        out.println("        addToCartButton.style.display = 'none';");
        out.println("    }");
        out.println("}");

        out.println("</script>");

        out.println("</head>");
        out.println("<body style='background-image: url(indexbg.jpg);'>");
        try {
        	System.out.println("Action is " + action);
        	if (action.equals("Show Books")) {
        	    List<Book> books = getBooks();

        	    List<String> cart = (List<String>) session.getAttribute("cart");

        	    if (cart != null && !cart.isEmpty()) {
        	        out.println("<a href='Customer?action=View+Cart' class='cart-button'>View Cart ( " + cart.size() + " )</a>");
        	    }
        	    out.println("<div class='login-box'>");
        	    out.println("<h2 id='h2'>Book list</h2>");
        	    out.println("<center>");
                out.println("<form method='post' action='Customer?action=addToCart'>");

        	    out.println("<table id='bookTable' style='color: white;'>");
        	    out.println("<thead>");
        	    out.println("<tr>");
        	    out.println("<th scope='col'>Title</th>");
        	    out.println("<th scope='col'>Author</th>");
        	    out.println("<th scope='col'>Price</th>");
        	    out.println("<th scope='col'>Quantity</th>");
        	    out.println("<th scope='col'>Rating/5</th>");
        	    out.println("<th scope='col'>Reviews</th>");
        	    out.println("</tr>");
        	    out.println("</thead>");
        	    out.println("<tbody>");

        	    int index = 0;
        	    for (Book book : books) {
        	        out.println("<tr>");
        	        out.println("<td>" + book.getTitle() + "</td>");
        	        out.println("<td>" + book.getWriter() + "</td>");
        	        out.println("<td>" + book.getPrice() + "$</td>");

        	        // Input for quantity (part of the addToCart form)
        	        out.println("<td><input type='number' name='quantity_" + index + "' id='quantity_" + index + "' min='0' value='0' onchange='toggleAddToCart()'></td>");

        	        if (book.getReviews().size() != 0) { out.println("<td>" + (book.getRate()*1.0)/book.getReviews().size() + "</td>"); }
        	        else { out.println("<td>0.0</td>"); }

        	        // Show Reviews button
        	        out.println("<td><button type='button' class='review-button' onclick=\"toggleDescription('description" + index + "', this)\">Show Reviews</button></td>");
        	        out.println("</tr>");

        	        // Hidden div for the reviews (starts hidden)
        	        out.println("<tr><td colspan='6'>");
        	        out.println("<div id='description" + index + "' class='description' style='display:none;'>");

        	        // Display the existing reviews
        	        List<Review> reviews = book.getReviews();
        	        for (Review review : reviews) {
        	            out.println("<p>User: " + review.getInitialText() + "</p>");
        	            if (review.getWriterText() != null && !review.getWriterText().isEmpty()) {
        	                out.println("<p>Writer: " + review.getWriterText() + "</p>");
        	            }
        	        }

        	        out.println("</div>");
        	        out.println("</td></tr>");

        	        index++;
        	    }

        	} else if (action.equals("View Cart")) {
        	    out.println("<div class='login-box'>");
        	    // Retrieve the cart from session
        	    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        	    // Check if the cart is empty or null
        	    if (cart == null || cart.isEmpty()) {
        	        session.setAttribute("infoMessage", "Your cart has been emptied.");
        	        response.sendRedirect("home.jsp");
        	    }

        	    out.println("<h2 id='h2'>Your orders</h2>");
        	    out.println("<center>");
        	    out.println("<table id='bookTable' style='color: white;'>");
        	    out.println("<thead>");
        	    out.println("<tr>");
        	    out.println("<th scope='col'>Title</th>");
        	    out.println("<th scope='col'>Price</th>");
        	    out.println("<th scope='col'>Quantity</th>");
        	    out.println("<th scope='col'>Total Price</th>");
        	    out.println("<th scope='col'>Action</th>"); // New column for the remove button
        	    out.println("</tr>");
        	    out.println("</thead>");
        	    out.println("<tbody>");

        	    double totalOrderPrice = 0;
        	    for (CartItem item : cart) {
        	        String title = item.getTitle();
        	        double price = item.getPrice();
        	        int quantity = item.getQuantity();
        	        double totalPrice = price * quantity;

        	        totalOrderPrice += totalPrice;

        	        out.println("<tr>");
        	        out.println("<td data-label='Title'>" + title + "</td>");
        	        out.println("<td data-label='Price'>" + price + "$</td>");
        	        out.println("<td data-label='Quantity'>" + quantity + "</td>");
        	        out.println("<td data-label='Total Price'>" + totalPrice + "$</td>");

        	        // Add the "Remove" button next to each item
        	        out.println("<td data-label='Action'>");
        	        out.println("<form method='post' action='Customer?action=removeFromCart'>");
        	        out.println("<input type='hidden' name='bookTitle' value='" + title + "'>");
        	        out.println("<button type='submit' class='remove-button'>Remove</button>");
        	        out.println("</form>");
        	        out.println("</td>");
        	        out.println("</tr>");
        	    }

        	    out.println("<tr>");
        	    out.println("<td colspan='3' style='text-align:right;'><strong>Total:</strong></td>");
        	    out.println("<td>" + totalOrderPrice + "$</td>");
        	    out.println("</tr>");
        	    out.println("</tbody>");
        	    out.println("</table>");

        	    out.println("<form method='post' action='Customer?action=Buy'>");
        	    out.println("<button type='submit'>Buy</button>");
        	    out.println("</center>");
        	    out.println("</form>");
        	} else if (action.equals("Find By Author")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("search.jsp");
                dispatcher.forward(request, response);
            } else if (action.equals("Logout")) { 
                session.invalidate();
                response.sendRedirect("index.jsp");
            }
        } catch(Exception e) {
            out.println("Database connection problem");
            System.out.println(e);
        }
        
        out.println("</tbody>");
        out.println("</table>");

        // Add to Cart button (in its own form)
        out.println("<div style='display:none;' id='addToCartButton'>");
        out.println("<form method='post' action='Customer?action=addToCart'>");
        out.println("<button type='submit' class='review-button'>Add to Cart</button>");
        out.println("</div>");
        out.println("</center>");
        out.println("</form>"); // Close the Add to Cart form

        out.println("<center><button type='button' onclick=\"window.location.href='home.jsp'\">Return to Home</button>");
        out.println("</center>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
    
    private List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

            // Query to get books
            String bookQuery = "SELECT bookId, title, writerId, price, rate, sales, publishDate FROM Book";
            ps = con.prepareStatement(bookQuery);
            rs = ps.executeQuery();

            while (rs.next()) {
                int bookId = rs.getInt("bookId");
                String title = rs.getString("title");
                String writerId = rs.getString("writerId");
                int price = rs.getInt("price");
                int rate = rs.getInt("rate");
                int sales = rs.getInt("sales");
                Date publishDate = rs.getDate("publishDate");

                // Fetch writer name based on writerId
                String writerQuery = "SELECT name FROM Writer WHERE writerId = ?";
                PreparedStatement psWriter = con.prepareStatement(writerQuery);
                psWriter.setString(1, writerId);
                ResultSet rsWriter = psWriter.executeQuery();
                String writerName = "";
                if (rsWriter.next()) {
                    writerName = rsWriter.getString("name");
                }
                rsWriter.close();
                psWriter.close();

                // Fetch reviews for the current book
                String reviewQuery = "SELECT initialText, writerAnswer FROM Review WHERE bookId = ?";
                PreparedStatement psReview = con.prepareStatement(reviewQuery);
                psReview.setInt(1, bookId);
                ResultSet rsReview = psReview.executeQuery();

                List<Review> reviews = new ArrayList<>();
                while (rsReview.next()) {
                    String initialText = rsReview.getString("initialText");
                    String writerAnswer = rsReview.getString("writerAnswer");
                    Review review = new Review(initialText);
                    review.setWriterText(writerAnswer);
                    reviews.add(review);
                }
                rsReview.close();
                psReview.close();

                // Create and add the book to the list
                Book book = new Book(bookId, title, writerName, writerId, price, rate, sales, reviews, publishDate);
                books.add(book);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
    
    public List<Book> filterBooks(List<Book> books, String searchTitle, String searchWriterName) {
        // Use temporary final variables inside the lambda expression
        final String finalSearchTitle = (searchTitle != null) ? searchTitle.trim().toLowerCase() : null;
        final String finalSearchWriterName = (searchWriterName != null) ? searchWriterName.trim().toLowerCase() : null;

        // Remove books that don't match the search criteria
        books.removeIf(book -> {
            boolean titleDoesNotMatch = (finalSearchTitle != null && !book.getTitle().toLowerCase().contains(finalSearchTitle));
            boolean writerDoesNotMatch = (finalSearchWriterName != null && !book.getWriter().toLowerCase().contains(finalSearchWriterName));

            // Return true if either the title or writer doesn't match, meaning the book should be removed
            return titleDoesNotMatch || writerDoesNotMatch;
        });

        return books; // Return the filtered list
    }




    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("action (doPost): " + action);

        if (action != null) {
        	if (action.equals("addToCart")) {
        	    HttpSession session = request.getSession();
        	    
        	    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        	    if (cart == null) {
        	        cart = new ArrayList<>();
        	    }

        	    List<Book> books = getBooks();
        	    for (int i = 0; i < books.size(); i++) {
        	        String quantityParam = request.getParameter("quantity_" + i);
        	        if (quantityParam != null && !quantityParam.isEmpty()) {
        	            int quantity = Integer.parseInt(quantityParam);
        	            if (quantity > 0) {
        	                Book book = books.get(i);
        	                
        	                // Add the book to the cart (bookId, title, price, quantity)
        	                CartItem item = new CartItem(book.getId(), book.getTitle(), book.getPrice(), quantity);
            	            System.out.println("bookId" + book.getId());
        	                cart.add(item);
        	            }
        	        }
        	    }

        	    // Store the cart back in the session
        	    session.setAttribute("cart", cart);
        	    
        	    System.out.println(cart.toString());

        	    // Redirect back to the book list or to a cart page
        	    response.sendRedirect("home.jsp");
        	} else if (action.equals("Buy")) {
        	    HttpSession session = request.getSession();
        	    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        	    if (cart == null || cart.isEmpty()) {
        	        PrintWriter out = response.getWriter();
        	        out.println("<h2>Your cart is empty. Nothing to buy.</h2>");
        	        return;
        	    }

        	    Connection con = null;
        	    PreparedStatement ps = null;
        	    ResultSet rs = null;

        	    try {
        	        Class.forName("com.mysql.cj.jdbc.Driver");
        	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

        	        // Loop through each item in the cart
        	        for (CartItem item : cart) {
        	            int bookId = item.getBookId();
        	            int quantity = item.getQuantity();
        	            System.out.println("bookId" + bookId);
        	            System.out.println("quantity" + quantity);
        	            // Fetch the current sales from the Book table
        	            String getSalesQuery = "SELECT sales FROM Book WHERE bookId = ?";
        	            ps = con.prepareStatement(getSalesQuery);
        	            ps.setInt(1, bookId);
        	            rs = ps.executeQuery();

        	            if (rs.next()) {
        	                int currentSales = rs.getInt("sales");
        	                int newSales = currentSales + quantity;

        	                // Update the sales column in the Book table
        	                String updateSalesQuery = "UPDATE Book SET sales = ? WHERE bookId = ?";
        	                ps = con.prepareStatement(updateSalesQuery);
        	                ps.setInt(1, newSales);
        	                ps.setInt(2, bookId);
        	                ps.executeUpdate();
        	            }
        	        }

        	        // Clear the cart after purchase
        	        session.removeAttribute("cart");

        	        // Redirect to a success page or show a success message
        	        session.setAttribute("purchaseSuccessMessage", "Purchase completed successfully!");
        	        response.sendRedirect("home.jsp");

        	    } catch (Exception e) {
        	        e.printStackTrace();
        	    } finally {
        	        try {
        	            if (rs != null) rs.close();
        	            if (ps != null) ps.close();
        	            if (con != null) con.close();
        	        } catch (SQLException e) {
        	            e.printStackTrace();
        	        }
        	    }
        	} else if (action.equals("removeFromCart")) {
        	    String bookTitle = request.getParameter("bookTitle");
        	    HttpSession session = request.getSession();

        	    // Retrieve the cart from the session
        	    List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        	    if (cart != null) {
        	        // Find and remove the item with the given title
        	        cart.removeIf(item -> item.getTitle().equals(bookTitle));

        	        // Update the session
        	        session.setAttribute("cart", cart);
        	    }

        	    // Redirect back to the cart view
        	    response.sendRedirect("Customer?action=View+Cart");
        	} else if (action.equals("Search")) {
        	    String searchTitle = request.getParameter("search_title");
        	    String searchAuthor = request.getParameter("search_author");

        	    List<Book> books = getBooks();

        	    // Retrieve books based on search criteria (title, author, or both)
        	    books = filterBooks(books, searchTitle, searchAuthor);

        	    // Now we are dynamically generating the HTML response
        	    response.setContentType("text/html");
        	    PrintWriter out = response.getWriter();

        	    out.println("<!DOCTYPE html>");
        	    out.println("<html>");
        	    out.println("<head>");
        	    out.println("<meta charset=\"utf-8\">");
        	    out.println("<title>Search Page</title>");
        	    out.println("<link rel=\"stylesheet\" href=\"./css/index.css\" type=\"text/css\">");
        	    out.println("<style>");
        	    out.println("body {background-image: url(indexbg.jpg); display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0;}");
        	    out.println(".login-box h2 {text-align: center; margin-bottom: 20px; color: white;}");
        	    out.println(".form-container {display: flex; flex-direction: column; align-items: center;}");
        	    out.println(".transparent-input {background-color: transparent; border: 1px solid white; color: white; padding: 10px; font-size: 16px; width: 100%; margin-bottom: 10px;}");
        	    out.println("table#bookTable { width: 100%; border-collapse: separate; border-spacing: 0 10px;}");
        	    out.println("table#bookTable th, table#bookTable td { padding: 10px 20px; text-align: center; vertical-align: middle;}");
        	    out.println(".description { display: none; color: white; background-color: rgba(0, 0, 0, 0.5); padding: 10px; border-radius: 5px; margin-top: 5px; }");
        	    out.println(".review-button { position: relative; display: inline-block; padding: 5px 10px; color: #03e9f4; font-size: 16px; text-decoration: none; text-transform: uppercase; overflow: hidden; transition: .25s; letter-spacing: 2px; background-color: transparent; border: none; }");
        	    out.println(".review-button:hover { background: #03e9f4; color: #fff; border-radius: 5px; box-shadow: 0 0 5px #03e9f4, 0 0 25px #03e9f4, 0 0 50px #03e9f4, 0 0 100px #03e9f4; }");
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
        	    out.println("</script>");
        	    out.println("</head>");
        	    out.println("<body>");
        	    out.println("<div class=\"login-box\">");

        	    // Search form
        	    out.println("<h2>Find a book by:</h2>");
        	    out.println("<form method=\"post\" action=\"Customer\">");
        	    out.println("<div class=\"form-container\">");
        	    out.println("<input type=\"text\" name=\"search_title\" class=\"transparent-input\" placeholder=\"Title\" />");
        	    out.println("<input type=\"text\" name=\"search_author\" class=\"transparent-input\" placeholder=\"Author\" />");
        	    out.println("<input type=\"submit\" class=\"review-button\" name=\"action\" value=\"Search\">");
        	    out.println("</div>");
        	    out.println("</form>");

        	    // Search results
        	    out.println("<h2>Search Results:</h2>");
        	    out.println("<table id=\"bookTable\" style=\"color: white;\">");
        	    out.println("<thead>");
        	    out.println("<tr>");
        	    out.println("<th scope=\"col\">Title</th>");
        	    out.println("<th scope=\"col\">Author</th>");
        	    out.println("<th scope=\"col\">Price</th>");
        	    out.println("<th scope=\"col\">Reviews</th>");
        	    out.println("</tr>");
        	    out.println("</thead>");
        	    out.println("<tbody>");

        	    if (books != null && !books.isEmpty()) {
        	        int index = 0;
        	        for (Book book : books) {
        	            out.println("<tr>");
        	            out.println("<td>" + book.getTitle() + "</td>");
        	            out.println("<td>" + book.getWriter() + "</td>");
        	            out.println("<td>" + book.getPrice() + "$</td>");

        	            // Reviews button with dynamic ID
        	            out.println("<td><button type=\"button\" class=\"review-button\" onclick=\"toggleDescription('description" + index + "', this)\">Show Reviews</button></td>");
        	            out.println("</tr>");

        	            // Hidden div for reviews
        	            out.println("<tr>");
        	            out.println("<td colspan=\"4\">");
        	            out.println("<div id=\"description" + index + "\" class=\"description\" style=\"display:none;\">");

        	            List<Review> reviews = book.getReviews();
        	            for (Review review : reviews) {
        	                out.println("<p>User: " + review.getInitialText() + "</p>");
        	                if (review.getWriterText() != null && !review.getWriterText().isEmpty()) {
        	                    out.println("<p>Writer: " + review.getWriterText() + "</p>");
        	                }
        	            }

        	            // Form for submitting a review
        	            out.println("<form method='post' action='Customer?action=submitReview&bookId=" + book.getId() + "'>");
        	            out.println("<input type='hidden' name='bookTitle' value='" + book.getTitle() + "'>");
        	            out.println("<input type='text' name='userReview' placeholder='Enter your review'>");
        	            
        	            out.println("<button type='submit' class='review-button'>Submit Review</button>");
        	            out.println("<div style='margin-top:10px;'>");
        	            out.println("<label for='rating_" + index + "'>Rate: </label>");
        	            out.println("<input type='range' name='rating' id='rating_" + index + "' min='1' max='5' value='3' oninput='this.nextElementSibling.value = this.value'>");
        	            out.println("<output>3</output>"); // This will display the current slider value
        	            out.println("</div>");
        	            out.println("</form>");


        	            out.println("</div>");
        	            out.println("</td>");
        	            out.println("</tr>");

        	            index++;
        	        }
        	    } else {
        	        out.println("<tr>");
        	        out.println("<td colspan=\"4\">No books found for the given search query.</td>");
        	        out.println("</tr>");
        	    }

        	    out.println("</tbody>");
        	    out.println("</table>");
                out.println("<center><button type='button' onclick=\"window.location.href='home.jsp'\">Return to Home</button>");
        	    out.println("</div>");
        	    out.println("</body>");
        	    out.println("</html>");
        	} else {
        	    HttpSession session = request.getSession();
                // Handle review submission
        		System.out.println("submitReview");
                String userReview = request.getParameter("userReview");
                String bookTitle = request.getParameter("bookTitle");
                String ratingStr = request.getParameter("rating");
                System.out.println(ratingStr);

                // Check if the review and book title are not empty
                if (userReview != null && !userReview.trim().isEmpty() && bookTitle != null && !bookTitle.trim().isEmpty()) {
                    Connection con = null;
                    PreparedStatement ps = null;
                    ResultSet rs = null;

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", "root", "mypass");

                        String getBookQuery = "SELECT bookId, rate FROM Book WHERE title = ?";
                        ps = con.prepareStatement(getBookQuery);
                        ps.setString(1, bookTitle);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            int bookId = rs.getInt("bookId");
                            int currentRating = rs.getInt("rate"); // Retrieve the current rating

                            // Add the new rating to the current rating
                            int updatedRating = currentRating + Integer.valueOf(ratingStr);

                            // Insert the new review into the Review table
                            String insertReviewQuery = "INSERT INTO Review (initialText, bookId) VALUES (?, ?)";
                            ps = con.prepareStatement(insertReviewQuery);
                            ps.setString(1, userReview);
                            ps.setInt(2, bookId);
                            ps.executeUpdate();

                            // Update the book rating in the Book table
                            String updateBookRatingQuery = "UPDATE Book SET rate = ? WHERE bookId = ?";
                            ps = con.prepareStatement(updateBookRatingQuery);
                            ps.setInt(1, updatedRating); // Set the cumulative rating
                            ps.setInt(2, bookId); // Identify the book by bookId
                            ps.executeUpdate();
                	        session.setAttribute("purchaseSuccessMessage", "Review submitted successfully!");
                	        response.sendRedirect("home.jsp");

                        } else {
                            System.out.println("Error: Book not found.");
                            response.sendRedirect("Customer?action=Show+Books");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (rs != null) rs.close();
                            if (ps != null) ps.close();
                            if (con != null) con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {System.out.println(userReview + bookTitle);}
            }
        }
    }
}
