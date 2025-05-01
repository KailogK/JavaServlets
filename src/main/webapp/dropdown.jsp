<%@ page import="java.util.List, java.util.ArrayList" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Choose the film</title>
        <link rel="stylesheet" href="./css/index.css" type="text/css">
        <style>
       		.hidden {
            	display: none;
        	}
    	</style>
    </head>
<body style="background-image: url(indexbg.jpg);">
<div class="login-box">
    <h3>Choose when and where you want to watch a film</h3>
    <form action="FilmMatchServlet" method="post" >
        <label for="films" style="color: white;">Select a Movie:</label>
        <select id="films" name="films" style="float: right;">
            <% List<String> films = (List<String>)request.getAttribute("movie_name");
               for (String option : films) { %>
                <option value="<%= option %>"><%= option %></option>
            <% } %>
       </select>
        <br><br>
        <label for="cinemas" style="color: white;">Select a Cinema Room:</label>
        <select id="cinemas" name="cinemas" style="float: right;">
            <% List<String> cinemas = (List<String>)request.getAttribute("cinema_name");
               for (String option : cinemas) { %>
                <option value="<%= option %>"><%= option %></option>
            <% } %>
        </select>
        <br><br>
        <label for="show_time" style="color: white;">Select a Show Time:</label>
        <select name="show_time" id="show_time" style="float: right;">
            <option value="12:00 PM">12:00 PM</option>
            <option value="01:00 PM">01:00 PM</option>
            <option value="02:00 PM">02:00 PM</option>
            <option value="03:00 PM">03:00 PM</option>
            <option value="04:00 PM">04:00 PM</option> 
            <option value="05:00 PM">05:00 PM</option>
            <option value="06:00 PM">06:00 PM</option>
            <option value="07:00 PM">07:00 PM</option>
            <option value="08:00 PM">08:00 PM</option>
            <option value="09:00 PM">09:00 PM</option>
            <option value="10:00 PM">10:00 PM</option>
            <option value="11:00 PM">11:00 PM</option>
        </select>
        <br><br><br>
        <center><input type="submit" value="Submit" style="width: 100px; height: 25px; font-size: 12px;"></center>
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
    </form>
    </div>
    </body>
    <script>
	window.onload = function() {
		var textElement = document.getElementById("Message");
		textElement.classList.remove("hidden");
		setTimeout(function() {
			textElement.classList.add("hidden");
			textElement.textContent = "";
		}, 4500);
	}
	var selectElement = document.getElementById("movie");
	var inputElement = document.getElementById("edit");
	
	selectElement.addEventListener("change", function() {
		inputElement.value = selectElement.value;
	});
    </script>
</html>

