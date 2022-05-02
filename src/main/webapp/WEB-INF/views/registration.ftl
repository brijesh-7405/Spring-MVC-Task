<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
	<#include "assets/library/bootstrap/css/bootstrap.min.css">
    <#include "assets/css/custom.css">
    <#include "assets/css/responsive.css">
    <#include "assets/dist/image-uploader.min.css">
  </style>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<#include "Header.ftl">
<section>
	<div class="container Form-Section">
		<#if user != null>
				<h2 class="edit-header" style="text-decoration: underline">Edit Page</h2>
				<form action="EditServlet" method="POST" class="form-horizontal" id="myform" enctype="multipart/form-data">
		<#else>
				<h2 class="header" style="text-decoration: underline">Registration Page</h2>
				<form action="UserRegistration" method="POST" class="form-horizontal" id="myform" enctype="multipart/form-data">
		</#if>
		<span style='color:red'>${message}</span>
		<div class="row left-gap">
			<input type="hidden" name="userid" id="userid" value="${userID}">
			 <div class="col-md-5">
			 	<div class="form-group">
					FirstName :<input type="text" name="firstname" maxlength="50" id="firstname" class="form-control" placeholder="Enter First Name" required>
				</div>
				<div class="form-group">
					LastName :<input type="text" name="lastname" id="lastname" maxlength="50" class="form-control" placeholder="Enter Last Name" required>
				</div>
				<#if user==null>
					 <div class="form-group">
					 	 Email:<input type="email" name="email" id="email" maxlength="100" value="${formdata.email}" class="form-control" placeholder="Enter Email" required>
					 	 <span id="error"></span>
					 </div>
					 <div class="form-group">
					 	 Password:<input type="password" placeholder="Enter Password" value="${formdata.password}" maxlength="50" id="pwd" class="form-control" name="password" required>
					 </div>
					 <div class="form-group">
						 Confirm password:<input type="password" name="repass" id="repwd" class="form-control" maxlength="50" placeholder="Enter Confirm Password" required>
					 </div>
				<#else>
					<div class="form-group">
				 	Gender:
				   <div class="radio checked-radio">
					      <#if user.gender=='Male' || formdata.gender=='Male'>
							<label class="radio-inline"><input type="radio" name="Gender" value="Male" checked>Male</label>
						  <#else>
						  	<label class="radio-inline"><input type="radio" name="Gender" value="Male">Male</label>
						  </#if>
					  	  <#if user.gender=='Female' || formdata.gender=='Female'>
							<label class="radio-inline"><input type="radio" name="Gender" value="Female" checked>Female</label>
						  <#else>
						  	<label class="radio-inline"><input type="radio" name="Gender" value="Female">Female</label>
						  </#if>
						  <#if user.gender=='Transgender' || formdata.gender=='Transgender'>
							<label class="radio-inline"><input type="radio" name="Gender" value="Transgender" checked>Transgender</label>
						  <#else>
						  	<label class="radio-inline"><input type="radio" name="Gender" value="Transgender">Transgender</label>
						  </#if>
					</div>
				 </div>
				 </#if>
			 </div>
			<div class="col-md-2"></div>
			<div class="col-md-5">
				 <div class="form-group">
					Phone:<input type="text" name="phone" maxlength="10" value="${formdata.phone}${user.phone}${phonenumber}" size="10" id="phone" class="form-control" placeholder="Enter Phone Number" required>
				 </div>
				 <div class="form-group">
				 	Date of Birth: <input type="date" id="dob" class="form-control" value="${formdata.dateofbirth}${user.dateofbirth}" name="birthdate" required>
				 </div>
				 <#if user==null>
				 <div class="form-group">
				 	Gender:
				   <div class="radio checked-radio">
				  	 <#if formdata.gender=='Male'>
							<label class="radio-inline"><input type="radio" name="Gender" value="Male" checked>Male</label>
					<#else>
						  	<label class="radio-inline"><input type="radio" name="Gender" value="Male">Male</label>
					</#if>
					 <#if formdata.gender=='Female'>
							<label class="radio-inline"><input type="radio" name="Gender" value="Female" checked>Female</label>
					<#else>
						  	<label class="radio-inline"><input type="radio" name="Gender" value="Female">Female</label>
					</#if>
					<#if formdata.gender=='Transgender'>
							<label class="radio-inline"><input type="radio" name="Gender" value="Transgender" checked>Transgender</label>
					<#else>
						  	<label class="radio-inline"><input type="radio" name="Gender" value="Transgender">Transgender</label>
					</#if>
					</div>
				 </div>
				 </#if>
				 <div class="form-group">
				 	Language Known:
				 	<div class="checkbox checked-checkbox">
				 	    <label class="checkbox-inline">
						 		<input type="checkbox" name="lang"  value="English">
						    English
					    </label>
						<label class="checkbox-inline">
							    <input type="checkbox" name="lang" id="myCheck"  value="Hindi">
							Hindi
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="lang" id="myCheck"  value="Gujarati">
							Gujarati	
						</label>
						<label class="checkbox-inline">
							<input type="checkbox" name="lang" id="myCheck" value="Chinese">
							Chinese
						</label>
				    </div>
				 </div>
			</div>
		</div> 
    <#if user==null>
		<div class="row left-gap">
			<div class="col-md-12">
				<div class="form-group">
			 		<fieldset>
    					<legend>Security Questions:</legend>
			    			1.Who was your childhood super hero?<br>
			    				 <input type="text"  class="form-control"  placeholder="SuperMan" id="ans1" value="${formdata.answer1}" maxlength="60" name="q1" required><br>
			    			2.What was your childhood nickname?<br>
			    				 <input type="text" class="form-control"  placeholder="Tom" id="ans2" value="${formdata.answer2}" maxlength="60" name="q2" required><br>
    				</fieldset>
    			 </div>
    		 </div>
    	 </div>
     </#if>
			   	<#--<div class="row left-gap">
					<div class="col-md-12">
		    			 <div class="form-group">
						    <div><label>Upload Photo:</label></div>
						    <c:choose> 
								<c:when test="${user != null}">
									 <c:forEach items="${user.image}" var='userimg' >
									 <span id="${userimg.imgid}" class="delete-image">
									     <span class="uploadedimage"><img src="data:image/jpg;base64,${userimg.base64Image}" class="image" width="180" height="180"/>
									     <span class="del-image"><i class="material-icons">clear</i></span></span></span>
								     </c:forEach>
								     <div class="input-images"></div>
								</c:when>
								<c:otherwise> 
								 <div class="input-images"></div>
								</c:otherwise>
							</c:choose>
						</div>
					  </div>
				</div>-->
  <div id="main-container">
    <#--<c:choose> 
		<c:when test="${user != null || formdata !=null}">
			 <c:choose> 
			 <c:when test="${requestScope.formdata !=null}">
				<c:forEach items="${requestScope.formdata.address}" var='useradd'>
					<div class="container-item">
						  <div class="row left-gap" id="add-design">
							    <h3  class="head-gap">Address Field:</h3>
								<div class="col-md-5 col-sm-5 gap">
								<input type="hidden" name="addid" value="${useradd.addressid}">
									  <div class="form-group">
										<p class="add-head">Address line1:</p>
											<input type="text" class="form-control add-head"  placeholder="Address" maxlength="50" value="${useradd.add1}"  name="address1" required>
									   </div>
									   <div class="form-group"><p class="add-head">City: </p><input type="text" placeholder="Ahmedabad" value="${useradd.city}" maxlength="50" class="form-control add-head" name="city" required></div>
									    <div class="form-group"><p class="add-head"> Country: </p><input type="text" placeholder="India" value="${useradd.country}" maxlength="50" class="form-control add-head" name="country" required></div>
									   <div class="form-group"><a href="javascript:void(0)" class="remove-item btn btn-sm btn-danger add-head remove-data" id="remove-btn">Remove</a></div>
							 	</div>
								<div class="col-md-2 col-sm-2"></div>
								<div class="col-md-5 col-sm-5 gap right-gap">
									   <div class="form-group">
										<p>Address line2:</p>
											<input type="text" class="form-control" placeholder="Address" maxlength="50" value="${useradd.add2}" name="address2" required>
									   </div>
								    <div class="form-group"><p>State:</p><input type="text" placeholder="Gujarat" maxlength="50" value="${useradd.state}"  class="form-control" name="state" required></div>
								    <div class="form-group"><p>Pincode:</p><input type="text" placeholder="371234" maxlength="6" value="${useradd.pincode}" class="form-control" name="pincode" id="pincode_0" required></div>
							 	</div>
						 	</div>
					  </div>
				</c:forEach>
			 </c:when>
			 <c:otherwise>
				<c:forEach items="${user.address}" var='useradd' >
				   <div class="container-item">
					   <div class="row left-gap" id="add-design">
						    <h3  class="head-gap">Address Field:</h3>
							<div class="col-md-5 col-sm-5 gap">
								<input type="hidden" name="addid" value="${useradd.addressid}">
								  <div class="form-group">
									<p class="add-head">Address line1:</p>
										<input type="text" class="form-control add-head" placeholder="Address" maxlength="50" value="${useradd.add1}"  name="address1" required>
								   </div>
								   <div class="form-group"><p class="add-head">City: </p><input type="text" placeholder="Ahmedabad" value="${useradd.city}" maxlength="50" class="form-control add-head" name="city" required></div>
								   <div class="form-group"><p class="add-head"> Country: </p><input type="text" placeholder="India" value="${useradd.country}" maxlength="50" class="form-control add-head" name="country" required></div>
								   <div class="form-group"><a href="javascript:void(0)" class="remove-item btn btn-sm btn-danger add-head remove-data" id="remove-btn">Remove</a></div>
						    </div>
							<div class="col-md-2 col-sm-2"></div>
							<div class="col-md-5 col-sm-5 gap right-gap">
								   <div class="form-group">
									<p>Address line2:</p>
										<input type="text" class="form-control" placeholder="Address" maxlength="50" value="${useradd.add2}" name="address2" required>
								   </div>
								   <div class="form-group"><p>State:</p><input type="text" placeholder="Gujarat" maxlength="50" value="${useradd.state}"  class="form-control" name="state" required></div>
								   <div class="form-group"><p>Pincode:</p><input type="text" placeholder="371234" maxlength="6" value="${useradd.pincode}" class="form-control" name="pincode" id="pincode_0" required></div>
						    </div>
					   </div>
		 		 </div>
	            </c:forEach>
			  </c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise> -->
			   <div class="container-item">
				  <div class="row left-gap" id="add-design">
					    <h3  class="head-gap">Address Field:</h3>
						<div class="col-md-5 col-sm-5 gap">
							  <div class="form-group">
								<p class="add-head">Address line1:</p>
									<input type="text" class="form-control add-head" placeholder="Address" maxlength="50" name="address1" required>
							   </div>
							   <div class="form-group"><p class="add-head">City: </p><input type="text" placeholder="Ahmedabad" id="city_0" maxlength="50" class="form-control add-head" name="city" required></div>
							   <div class="form-group"><p class="add-head"> Country: </p><input type="text" placeholder="India" id="country_0" maxlength="50" class="form-control add-head" name="country" required></div>
							   <div class="form-group"><a href="javascript:void(0)" class="remove-item btn btn-sm btn-danger add-head remove-data" id="remove-btn">Remove</a></div>
						</div>
						<div class="col-md-2 col-sm-2"></div>
						<div class="col-md-5 col-sm-5 gap right-gap">
							   <div class="form-group">
								<p>Address line2:</p>
									<input type="text" maxlength="50" placeholder="Address" class="form-control" name="address2" required>
							   </div>
							   <div class="form-group"><p>State:</p><input type="text" placeholder="Gujarat" maxlength="50" id="state_0" class="form-control" name="state" required></div>
							   <div class="form-group"><p>Pincode:</p><input type="text" placeholder="371234" maxlength="6" class="form-control" name="pincode" id="pincode_0" required></div>
					 	</div>
				   </div>
				</div>
		<#--</c:otherwise>
		</c:choose>-->
	</div>
		<div class="form-group">
			<a id="add-more" href="javascript:;" class="btn btn-primary left-gap add-btn">Add More Address</a>
		 </div>
		 <div class="form-group">
			 <#if user != null>
					<input type="submit" value="Update" class="btn btn-success left-gap" id="submit-btn">
			  <#else>
					<input type="submit" value="Submit" class="btn btn-success left-gap" id="submit-btn">
			 </#if>
			<input type="reset" class="btn btn-info">
		 </div>	
	</form>
</div>	
</section>
<#include "Footer.ftl">
<script>
		<#include "assets/js/jquery-3.6.0.min.js">
		<#include "assets/js/cloneData.js">	
		<#include "assets/dist/image-uploader.min.js">
		<#include "assets/js/custom.js">
		<#include "assets/js/validation.js">
</script>
</body>
</html>