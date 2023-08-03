
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Sign Up Form by Colorlib</title>

<!-- Font Icon -->
<link rel="stylesheet"
	href="fonts/material-icon/css/material-design-iconic-font.min.css">

<!-- Main css -->
<link rel="stylesheet" href="css/style.css">
</head>
<body>
    
    <input type="hidden" id="status" value="<%= request.getAttribute("status")%>">

	<div class="main">

		<!-- Sign up form -->
		<section class="signup">
			<div class="container">
				<div class="signup-content">
					<div class="signup-form">
						<h2 class="form-title">Sign up</h2>
					
						<form method="post" action="RegistrationServlet" class="register-form"
							id="register-form">
							<div class="form-group">
								<label for="name"><i
									class="zmdi zmdi-account material-icons-name"></i></label> <input
									type="text" name="name" id="name" placeholder="Your Name" required/>
							</div>
                                                                                                                <div class="form-group">
								<label for="Age"><i class="zmdi zmdi-face"></i></label>
								<input type="text" name="uage" id="uage"
									placeholder="Your Age" required/>
							</div>
							<div class="form-group">
								<label for="email"><i class="zmdi zmdi-email"></i></label> <input
									type="email" name="email" id="email" placeholder="Your Email" required/>
							</div>
							<div class="form-group">
								<label for="pass"><i class="zmdi zmdi-lock"></i></label> <input
									type="password" name="pass" id="pass" placeholder="Password" required />
							</div>
							<div class="form-group">
								<label for="re-pass"><i class="zmdi zmdi-lock-outline"></i></label>
								<input type="password" name="re_pass" id="re_pass"
									placeholder="Repeat your password" required/>
							</div>
							<div class="form-group">
								<label for="contact"><i class="zmdi zmdi-phone"></i></label>
								<input type="text" name="contact" id="contact"
									placeholder="Contact no" required/>
							</div>
                                                        <div class="form-group">
								<label for="Type"><i class="zmdi zmdi-account material-icons-name"></i></label>
								<input type="text" name="utype" id="utype"
									placeholder="User/Admin" required/>
							</div>

							<div class="form-group form-button">
								<input type="submit" name="signup" id="signup"
									class="form-submit" value="Register" />
							</div>
						</form>
					</div>
					<div class="signup-image">
						<figure>
							<img src="images/signup-image.jpg" alt="sing up image">
						</figure>
						<a href="login.jsp" class="signup-image-link">I am already
							member</a>
					</div>
				</div>
			</div>
		</section>


	</div>
	<!-- JS -->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="js/main.js"></script>

        <!--cdn links sweet alert-->
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<link rel="stylesheet" href="alert/dist/sweetalert.css">
        
        <script>
            var status = document.getElementById("status").value;
            if(status == "success"){
                swal("Congrats","Account created successfully","success");
            }
                        if(status == "Invalid Name"){
                swal("Sorry","please Enter Name","error");
            }
                        if(status == "Too Short Name"){
                swal("Sorry","Name is too short","error");
            }
                        if(status == "Invalid Email"){
                swal("Sorry","please Enter Email","error");
            }
                        if(status == "Invalid Password"){
                swal("Sorry","please Enter password","error");
            }
                         if(status == "Password not matched"){
                swal("Sorry","Password not matched please confirm!!","error");
            }
                         if(status == "Invalid Pwd Length"){
                swal("Sorry","password must be of size 6 or more","error");
            }
                        if(status == "Invalid Mobile"){
                swal("Sorry","please Enter Mobile","error");
            }
                        if(status == "Invalid Mobile Length"){
                swal("Sorry","mobile no must be of size 10 or less","error");
            }
                        if(status == "Weak Pwd"){
                swal("Sorry","Password is weak. \n\
                              Pasword must\n\
                              -> contain at least 8 characters and at most 20 characters\n\
                              -> contain at least one digit.\n\
                              -> contain at least one upper case alphabet.\n\
                              -> contain at least one lower case alphabet.\n\
                              -> contain at least one special character which includes !@#$%&*()-+=^.\n\
                              -> doesn't contian any white space","error");
            }
                        if(status == "Invalid Type"){
                swal("Sorry","You have to specify either user or admin.","error");
            }
                        if(status == "Please Enter Age"){
                swal("Sorry","You have to specify your age","error");
            }
        </script>

</body>
<!-- This templates was made by Colorlib (https://colorlib.com) -->
</html>