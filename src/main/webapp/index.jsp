<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Login</title>
        
        <link rel="stylesheet" href="./css/index.css" type="text/css">
    </head>
    <body style="background-image: url('indexbg.jpg');">
        <div class="login-box">
            <h2 id="h2">Login</h2>
            <form method="post" action="login">
              <input type="hidden" name="requestType" value="Login" />
              <div class="user-box">
                <input type="text" id="input1" name="username" oninput="check()">
                <label>e-mail</label>
              </div>
              <div class="user-box">
                <input type="password" id="input2" name="password" oninput="check()">
                <label>password</label>
              </div>
              <div id="errorMessage" style="color: red;">
              	<% if (request.getAttribute("errorMessage") != null) { %>
      			<%= request.getAttribute("errorMessage") %>
    			<% } %>
              </div>
			  <div id="Message" style="color: lime;">
              	<% if (request.getAttribute("Message") != null) { %>
      			<%= request.getAttribute("Message") %>
    			<% } %>
              </div>
              <center><input type="submit" value="Submit" class="a" id="btn" disabled></center>
              <center><b href="register.html" id="Reg" onmouseover="changeText()" onclick="redirect()">
                dont have an account ?
              </b></center>
            </form>
          </div>       
    </body>
    <script>
    function changeText() {
    	var link = document.getElementById("Reg");
    	link.textContent = "Register";
    }

    function redirect() {
       	window.location.href = "register.html";
    }

    function check() {
        var input1 = document.getElementById("input1");
        var input1Value = input1.value;
  
        var input2 = document.getElementById("input2");
        var input2Value = input2.value;
  
        var button = document.getElementById("btn");

        if (input1Value.length < 6 || input2Value.length < 6) {
          button.disabled = true;
        }
        else {
          button.disabled = false;
        }
        
        document.getElementById("errorMessage").textContent = '';
        document.getElementById("Message").textContent = '';
      }
    </script>
</html>

<!-- link used: https://codepen.io/soufiane-khalfaoui-hassani/pen/LYpPWda -->