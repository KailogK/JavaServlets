<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Writer's Page</title>
        <link rel="stylesheet" href="./css/index.css" type="text/css">
    </head>
    <style>
        .description { display: none; color: white; background-color: rgba(0, 0, 0, 0.5); padding: 10px; border-radius: 5px; margin-top: 5px; }
        .review-button { position: relative; display: inline-block; padding: 5px 10px; color: #03e9f4; font-size: 16px; text-decoration: none; text-transform: uppercase; overflow: hidden; transition: .25s; letter-spacing: 2px; background-color: transparent; border: none; }
        .review-button:hover { background: #03e9f4; color: #fff; border-radius: 5px; box-shadow: 0 0 5px #03e9f4, 0 0 25px #03e9f4, 0 0 50px #03e9f4, 0 0 100px #03e9f4; }
        .login-box { width: 800px; margin: 0 auto; }
        input[type='number'] { width: 50px; background-color: transparent; color: white; border: 1px solid white; padding: 5px; text-align: center; }
        table#bookTable { width: 100%; border-collapse: separate; border-spacing: 0 10px; }
        table#bookTable th, table#bookTable td { padding: 10px 20px; text-align: center; vertical-align: middle; }
    </style>
    <body style='background-image: url(indexbg.jpg);'>
        <div class="login-box">
            <h2 id="h2">Welcome back, <%= session.getAttribute("username") %></h2>
            <form action=WriterServlet>
              <center>
              <input type=submit name="action" class="review-button" value="See your books">
              <br><br><br>
              <input type=submit name="action" class="review-button" value="Logout">
              </center>
            </form>
         </div>       
    </body>
</html>
