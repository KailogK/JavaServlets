<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Customer Page</title>
    <link rel="stylesheet" href="./css/index.css" type="text/css">
    <style>
        .description { display: none; color: white; background-color: rgba(0, 0, 0, 0.5); padding: 10px; border-radius: 5px; margin-top: 5px; }
        .review-button { position: relative; display: inline-block; padding: 5px 10px; color: #03e9f4; font-size: 16px; text-decoration: none; text-transform: uppercase; overflow: hidden; transition: .25s; letter-spacing: 2px; background-color: transparent; border: none; }
        .review-button:hover { background: #03e9f4; color: #fff; border-radius: 5px; box-shadow: 0 0 5px #03e9f4, 0 0 25px #03e9f4, 0 0 50px #03e9f4, 0 0 100px #03e9f4; }
        .login-box { width: 800px; margin: 0 auto; }
        input[type='number'] { width: 50px; background-color: transparent; color: white; border: 1px solid white; padding: 5px; text-align: center; }
        table#bookTable { width: 100%; border-collapse: separate; border-spacing: 0 10px; }
        table#bookTable th, table#bookTable td { padding: 10px 20px; text-align: center; vertical-align: middle; }

        /* Positioning the Cart button */
        .cart-button {
            position: absolute;
            top: 20px;
            right: 20px;
            padding: 10px 20px;
            background-color: #03e9f4;
            color: white;
            font-size: 16px;
            text-decoration: none;
            border-radius: 5px;
            display: inline-block;
        }
        .cart-button:hover {
            background-color: #03b0f4;
        }

        /* Success message styling */
        .success-message {
            background-color: #28a745;
            color: white;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            text-align: center;
        }

        /* Info message styling (new) */
        .info-message {
            background-color: #007bff;
            color: white;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            text-align: center;
        }
    </style>

    <script>
        // Function to hide the success and info messages after 5 seconds
        function hideMessages() {
            var successMessageDiv = document.getElementById('successMessage');
            var infoMessageDiv = document.getElementById('infoMessage');
            
            if (successMessageDiv) {
                setTimeout(function() {
                    successMessageDiv.style.display = 'none';
                }, 5000); // 5 seconds
            }

            if (infoMessageDiv) {
                setTimeout(function() {
                    infoMessageDiv.style.display = 'none';
                }, 5000); // 5 seconds
            }
        }

        // Call hideMessages on page load
        window.onload = hideMessages;
    </script>
</head>
<body style='background-image: url(indexbg.jpg);'>
    <div class="login-box">
        <h2 id="h2">Welcome back, <%= session.getAttribute("username") %></h2>

        <form action="Customer" method="get">
          <center>
            <input type="submit" class="review-button" name="action" value="Show Books">
            <br><br><br>
            <input type="submit" class="review-button" name="action" value="Find By Author">
            <br><br><br>
            <input type="submit" class="review-button" name="action" value="Logout">
          </center>
        </form>
    </div>       

    <% 
        // Display the success message if it's in the session
        String successMessage = (String) session.getAttribute("purchaseSuccessMessage");
        if (successMessage != null) {
    %>
        <div id="successMessage" class="success-message">
            <%= successMessage %>
        </div>
        <% 
            // Remove the message from the session so it only shows once
            session.removeAttribute("purchaseSuccessMessage");
        %>
    <% 
        }
    %>

    <% 
        // Display the info message if it's in the session
        String infoMessage = (String) session.getAttribute("infoMessage");
        if (infoMessage != null) {
    %>
        <div id="infoMessage" class="info-message">
            <%= infoMessage %>
        </div>
        <% 
            // Remove the message from the session so it only shows once
            session.removeAttribute("infoMessage");
        %>
    <% 
        }
    %>

    <% 
        // Get the cart from session
        List<String> cart = (List<String>) session.getAttribute("cart");
        
        // If cart is not null and has items, show the Cart button
        if (cart != null && !cart.isEmpty()) {
    %>
        <!-- Display Cart Button in the top-right corner -->
        <a href="Customer?action=View+Cart" class="cart-button">View Cart ( <%= cart.size() %> )</a>
    <% 
        }
    %>
</body>
</html>
