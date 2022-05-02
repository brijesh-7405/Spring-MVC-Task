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
<h3 class="title-name">Welcome ${USER.firstname} ${USER.lastname}</h3>
<span class="buttons">
   <a href="UserDetails" class="btn button-dg">EditProfile</a>
 </span>
<#include "Footer.ftl"> 
</body>
</html>