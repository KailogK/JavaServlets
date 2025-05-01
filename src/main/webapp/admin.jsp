<%@ page import="java.util.List, java.util.ArrayList" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Admin's home page</title>
        <link rel="stylesheet" href="./css/index.css" type="text/css">
    <style>
        .description { display: none; color: white; background-color: rgba(0, 0, 0, 0.5); padding: 10px; border-radius: 5px; margin-top: 5px; }
        .review-button { position: relative; display: inline-block; padding: 5px 10px; color: #03e9f4; font-size: 16px; text-decoration: none; text-transform: uppercase; overflow: hidden; transition: .25s; letter-spacing: 2px; background-color: transparent; border: none; }
        .review-button:hover { background: #03e9f4; color: #fff; border-radius: 5px; box-shadow: 0 0 5px #03e9f4, 0 0 25px #03e9f4, 0 0 50px #03e9f4, 0 0 100px #03e9f4; }
        .login-box { width: 800px; margin: 0 auto; }
        input[type='number'] { width: 50px; background-color: transparent; color: white; border: 1px solid white; padding: 5px; text-align: center; }
        table#bookTable { width: 100%; border-collapse: separate; border-spacing: 0 10px; }
        table#bookTable th, table#bookTable td { padding: 10px 20px; text-align: center; vertical-align: middle; }
        
        .success-message {
            background-color: #28a745;
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
    <body style="background-image: url(indexbg.jpg);">
        <div class="login-box">
            <h2 id="h2">Welcome back, admin</h2>
            <form action=AdminServlet>
              <center>
              <input type=submit name="action" class="review-button" value="Add Books">
              <br><br><br>
			  <input type=submit name="action" class="review-button" value="Delete Books">
              <br><br><br>
              <input type=submit name="action" class="review-button" value="Manage Writers">
              <br><br><br>
			  <input type=submit name="action" class="review-button" value="Manage Users">
              <br><br><br>
              <input type=submit name="action" class="review-button" value="Logout">
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
    </body>
</html>

<!-- link used: https://codepen.io/soufiane-khalfaoui-hassani/pen/LYpPWda -->