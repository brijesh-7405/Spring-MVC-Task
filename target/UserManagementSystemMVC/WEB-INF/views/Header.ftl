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
		<nav class="navbar-fixed-top">
			<div class="container-fluid default-header">
			    <div class="row">
				   <div class="col-md-10 col-sm-10 set-data"><p class="userheading"><i>User Management System</i></p></div>
				   <#if !Session.USER??>
				   		<div class="col-md-1 col-sm-1 set-data"><a href="index" class="header-tag">Home</a></div>
				   		<div class="col-md-1 col-sm-1 set-data"><a href="registration" class="header-tag">Signup</a></div>
				   <#else>
				   		<div class="col-md-1 col-sm-1 set-data"></div>
				   		<div class="col-md-1 col-sm-1 set-data"><a href="logOut" class="btn btn-danger">LogOut</a></div>
				   </#if>
				</div>
				</div>
		</nav>
</body>
</html>