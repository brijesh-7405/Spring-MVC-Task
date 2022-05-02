<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
	<#include "assets/library/bootstrap/css/bootstrap.min.css">
    <#include "assets/css/custom.css">
    <#include "assets/css/responsive.css">
  </style>
</head>
<body>
<#include "Header.ftl">
<section>
	<div class="container">
		<div class="row">
			<div class="col-sm-3 col-md-3 "></div>
			<div class="col-sm-5 col-md-5 forgot-section">
				<h1 class="heading" style="text-decoration: underline">Find Your Account</h1>
					<form action="ForgotPwd" method="POST">
						<span style="color:red">${message!""}</span>
						<div class="form-group">
							Email :<input type="email" placeholder="Enter Email-ID"  class="form-control" name="email" required>
						</div>
						<div class="form-group">
						 	Date of Birth: <input type="date" id="birthday" class="form-control" name="birthdate" required>
						 </div>
						 <div class="form-group">
					 		<fieldset>
		    					<legend>Security Questions:</legend>
					    			1.Who was your childhood super hero?<br>
					    				 <input type="text" placeholder="Spider-man" class="form-control" name="q1" required><br>
					    			2.What was your childhood nickname?<br>
					    				 <input type="text" placeholder="Tom" class="form-control"  name="q2" required><br>
		    				</fieldset>
		    			 </div>
						<div class="form-group">
						<input type="submit" class="form-control login-btn" value="Submit">
						</div>
					</form>
			 </div>
			 <div class="col-sm-4 col-md-4"></div>
		</div>
	</div>
</section>
<#include "Footer.ftl">
</body>
</html>