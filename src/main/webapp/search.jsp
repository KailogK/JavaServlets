<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="models.Book" %>
<%@ page import="models.Review" %>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Search Page</title>
        <link rel="stylesheet" href="./css/index.css" type="text/css">
        <style>
            body {
                background-image: url(indexbg.jpg);
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .login-box h2 {
                text-align: center;
                margin-bottom: 20px;
                color: white;
            }
            .form-container {
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            .transparent-input {
                background-color: transparent;
                border: 1px solid white;
                color: white;
                padding: 10px;
                font-size: 16px;
                width: 100%;
                margin-bottom: 10px; /* Space between input fields */
                box-sizing: border-box; /* Ensures padding and border are included in width */
                transition: border-color 0.3s ease; /* Smooth transition for border color */
            }
        	input[type='number'] { width: 50px; background-color: transparent; color: white; border: 1px solid white; padding: 5px; text-align: center; }
        	table#bookTable { width: 100%; border-collapse: separate; border-spacing: 0 10px; }
        	table#bookTable th, table#bookTable td { padding: 10px 20px; text-align: center; vertical-align: middle; }
			.description { display: none; color: white; background-color: rgba(0, 0, 0, 0.5); padding: 10px; border-radius: 5px; margin-top: 5px; }
        	.review-button { position: relative; display: inline-block; padding: 5px 10px; color: #03e9f4; font-size: 16px; text-decoration: none; text-transform: uppercase; overflow: hidden; transition: .25s; letter-spacing: 2px; background-color: transparent; border: none; }
        	.review-button:hover { background: #03e9f4; color: #fff; border-radius: 5px; box-shadow: 0 0 5px #03e9f4, 0 0 25px #03e9f4, 0 0 50px #03e9f4, 0 0 100px #03e9f4; }
        </style>
        <script>
            document.addEventListener('DOMContentLoaded', function() {
                var titleInput = document.querySelector('input[name="search_title"]');
                var authorInput = document.querySelector('input[name="search_author"]');
                var searchButton = document.querySelector('.search-button');

                // Function to toggle search button based on input length
                function toggleSearchButton() {
                    var titleValue = titleInput.value.trim();
                    var authorValue = authorInput.value.trim();

                    if (titleValue.length >= 3 || authorValue.length >= 3) {
                        searchButton.style.display = 'block';
                    } else {
                        searchButton.style.display = 'none';
                    }

                    // Change input field border color based on length
                    if (titleValue.length == 0) {
                        titleInput.style.borderColor = 'white';
                    } else if (titleValue.length < 3) {
                        titleInput.style.borderColor = 'red';
                    } else {
                        titleInput.style.borderColor = '#03e9f4';
                    }

                    if (authorValue.length == 0) {
                        authorInput.style.borderColor = 'white';
                    } else if (authorValue.length < 3) {
                        authorInput.style.borderColor = 'red';
                    } else {
                        authorInput.style.borderColor = '#03e9f4';
                    }
                }
                
                function toggleDescription(id, button) {
                    // Prevent default button action
                    event.preventDefault();

                    // Find the element with the given id
                    var element = document.getElementById(id);

                    // Toggle the display of the element (reviews section)
                    if (element.style.display === 'none' || element.style.display === '') {
                        element.style.display = 'block';
                        button.textContent = 'Hide Reviews'; // Change button text to "Hide Reviews"
                    } else {
                        element.style.display = 'none';
                        button.textContent = 'Show Reviews'; // Change button text back to "Show Reviews"
                    }
                }

                
                searchButton.addEventListener('click', function(event) {
                    var authorValue = authorInput.value.trim();
                    var titleValue = titleInput.value.trim();
                    if (authorValue.length >= 3) {
                        // Set the search_author parameter
                        var form = document.querySelector('form');
                        var searchAuthorInput = document.createElement('input');
                        searchAuthorInput.setAttribute('type', 'hidden');
                        searchAuthorInput.setAttribute('name', 'search_author');
                        searchAuthorInput.setAttribute('value', authorValue);
                        form.appendChild(searchAuthorInput);
                    } else {
                    	var form = document.querySelector('form');
                        var searchAuthorInput = document.createElement('input');
                        searchAuthorInput.setAttribute('type', 'hidden');
                        searchAuthorInput.setAttribute('name', 'search_book');
                        searchAuthorInput.setAttribute('value', titleValue);
                        form.appendChild(searchAuthorInput);
                    }
                });
            });
        </script>
    </head>
    <body>
        <div class="login-box">
            <h2>Find a book by:</h2>
            <form method="post" action="Customer">
                <div class="form-container">
                    <input type="text" name="search_title" class="transparent-input" placeholder="Title" />
                    <input type="text" name="search_author" class="transparent-input" placeholder="Author" />
                    <h3>or leave blank to get all books</h3>
					<input type="submit" class="review-button" name="action" value="Search">Search</>
                    
                </div>
            </form>
			<center>
                <button type="button" onclick="window.location.href='home.jsp'">Return to Home</button>
            </center>
        </div>       
    </body>
</html>