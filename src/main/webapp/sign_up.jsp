<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign Up</title>
<jsp:include page="import_bootstrap.jspf" />
<jsp:include page="import_jquery.jspf" />
<script>
$(document).ready(function() {
	$('#submit_create_account').click( function(event){
		var first_name_form = $('#first_name_form').val();
		var last_name_form = $('#last_name_form').val();
		var email_form = $('#email_form').val();
		var password_form = $('#password_form').val();
		$.ajax({
            type:'POST',
            data: {
            	firstName : first_name_form,
            	lastName : last_name_form,
            	email : email_form,
            	password : password_form,
            	action : 'create_new_account'
            },
            dataType: 'json',
            url:'UserController',
            success: function(responseText){
            	$("#first_name_form").val("");
                $("#last_name_form").val("");
                $("#email_form").val(""); 
                $("#password_form").val("");
                alert("success create account");
            },
            error: function(jqXHR, textStatus, message) {
            	alert("error create account");
            }
        });
        
    });
});	
</script>
</head>
<body>

<jsp:include page="navbar.jsp" />
<div class="mt-5"></div>

<div class="row d-flex justify-content-center">

    <div class="col-auto bg-light border">

        <h1 class="text-center bg-light text-black px-0 py-3 mx-0 my-0 text-uppercase fw-bold">sign up</h1>

        <!-- Form -->
        <form>

            <div class="row text-uppercase fw-bold">
                <!-- Field -->
                <div class="col">
                    <div class="form-group">
                        <label for="first_name_form">First Name</label>
                        <input type="text" class="form-control" id="first_name_form" name="first_name_form" maxlength="20" placeholder="example name" required="required">

                        <label for="email_form">Email</label>
                        <input type="email" class="form-control" id="email_form" name="email_form" placeholder="example@example.com" required="required">                        
                    </div>
                </div>
                <div class="col">
                    <div class="form-group">
                        <label for="last_name_form">Last Name</label>
                        <input type="text" name="last_name_form" id="last_name_form" class="form-control" maxlength="20" placeholder="example name" required="required">

                        <label for="password_form">Password</label>
                        <input type="password" class="form-control" id="password_form" name="password_form" placeholder="Ex4mp1&" required="required">
                    </div>
                </div>
                
            </div>
            <!-- Button -->
            <div class="row py-1">
                <div class="col text-center">
                    <input type="button" id="submit_create_account" class="btn btn-danger btn-lg" value="CREATE ACCOUNT">
                </div>
            </div>
            <div class="row py-1">
                <div class="col text-center">
                    <p class="py-0 my-0">Already have an account?</p>
                    <a class="btn btn-secondary" href="UserController?action=go_to_login_page">SIGN IN</a>
                </div>
            </div>

        </form>

    </div>

</div>

</body>
</html>