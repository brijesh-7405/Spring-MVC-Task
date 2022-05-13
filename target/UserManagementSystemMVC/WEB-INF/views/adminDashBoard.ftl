<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
	<#include "assets/library/DataTables/datatables.min.css">
	<#include "assets/library/bootstrap/css/bootstrap.min.css">
	<#include "assets/css/datatable.css">
    <#include "assets/css/responsive.css">
</style>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<h3 class="title-name" style="text-decoration: underline">Welcome ${USER.firstname}</h3>
<div class="table-design">
	<table id="userdetails">
		<thead>
			<tr>
			<th>UserID</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Email</th>
			<th>Phone</th>
			<th>Date Of Birth</th>
			<th>Gender</th>
			<th>Language Known</th>
			<th>EditUser</th>
			<th>DeleteUser</th>
			</tr>
		</thead>
		<tbody>
			<#list UsersList as user>
			<tr id="delete${user.userID}">
			    <td>${user.userID}</td>
			    <td>${user.firstname}</td>
			    <td>${user.lastname}</td>
			    <td>${user.email}</td>
			    <td>${user.phone?string.computer}</td>
			    <td>${user.dateofbirth}</td>
			    <td>${user.gender}</td>
			    <td>${user.language}</td>
			    <td><a href="userDetails?userid=${user.userID}" id="Edit-btn" class="btn">Edit</a></td>
			    <td><button type="button" id="${user.userID}" class="btn delete">Delete</button></td>
			 </tr>
			</#list>
		</tbody>
	</table>
</div>
<span class="admin-buttons">
		<a href="registration" class="btn btn-info admin-btn">Add New User</a>
		<a href="logOut" class="btn btn-danger admin-btn">LogOut</a>
</span>

<script>
		<#include "assets/js/jquery-3.6.0.min.js">
		<#include "assets/library/DataTables/datatables.min.js">	
		<#include "assets/js/datatable.js">
</script>
</body>
</html>