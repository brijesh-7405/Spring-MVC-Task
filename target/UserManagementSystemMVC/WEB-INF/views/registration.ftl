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
		<h2 class="header" style="text-decoration: underline"><#if user ?? && !faildata ??>Edit <#else>Registration </#if> Page</h2>
				<form <#if user ?? && !faildata ??>action="editServlet" <#else>action="userRegistration" </#if> method="POST" class="form-horizontal" id="myform" enctype="multipart/form-data">
				<#if message ??><span style="color:red">${message?join("<br>")}</span></#if>
		<div class="row left-gap">
		<#if user ?? && !faildata ??>
			<input type="hidden" name="userID" value="${user.userID}" id="userid">
			</#if>
			 <div class="col-md-5">
			 	<div class="form-group">
					FirstName :<input type="text" name="firstname" <#if user ??>value="${user.firstname!}"</#if> <#if faildata ??>value="${faildata.firstname!}"</#if> maxlength="50" id="firstname" class="form-control" placeholder="Enter First Name" required>
				</div>
				<div class="form-group">
					LastName :<input type="text" name="lastname" id="lastname" <#if user ??>value="${user.lastname}"</#if> <#if faildata ??>value="${faildata.lastname!}"</#if> maxlength="50" class="form-control" placeholder="Enter Last Name" required>
				</div>
				<#if !user ?? || faildata ??>
					 <div class="form-group">
					 	 Email:<input type="email" name="email" id="email" maxlength="100" <#if faildata ??>value="${faildata.email!}"</#if> class="form-control" placeholder="Enter Email" required>
					 	 <span id="error"></span>
					 </div>
					 <div class="form-group">
					 	 Password:<input type="password" placeholder="Enter Password" maxlength="14" minlength="5" <#if faildata ??>value="${faildata.password!}"</#if> maxlength="50" id="pwd" class="form-control" name="password" required>
					 </div>
					 <div class="form-group">
						 Confirm password:<input type="password" name="repass" id="repwd" class="form-control" maxlength="50" placeholder="Enter Confirm Password" required>
					 </div>
					 
					 <#else>
							<div class="form-group">
							 	Gender:
							   <div class="radio checked-radio">
										<label class="radio-inline"><input type="radio" name="Gender" value="Male" <#if user.gender=='Male'> checked </#if>>Male</label>
										<label class="radio-inline"><input type="radio" name="Gender" value="Female" <#if user.gender=='Female'>checked</#if>>Female</label>  
										<label class="radio-inline"><input type="radio" name="Gender" value="Transgender" <#if user.gender=='Transgender'>checked</#if>>Transgender</label>	 
								</div>
						 	</div>
				 </#if>
			 </div>
			<div class="col-md-2"></div>
			<div class="col-md-5">
				 <div class="form-group">
					Phone:<input type="number" name="phone" minlength="10" <#if user ??>value="${user.phone?string.computer}"</#if>  <#if faildata ??>value="${faildata.phone?string.computer}"</#if> size="10" id="phone" class="form-control" placeholder="Enter Phone Number" required>
				 </div>
				 <div class="form-group">
				 	Date of Birth: <input type="date" id="dob" class="form-control" <#if user ??>value="${user.dateofbirth!}"</#if> <#if faildata ??>value="${faildata.dateofbirth!}"</#if> name="dateofbirth" required>
				 </div>
				<#if !user ?? || faildata ??>
					 <div class="form-group">
					 	Gender:
					   <div class="radio checked-radio">
					  	    <label class="radio-inline"><input type="radio" name="Gender"  value="Male" <#if faildata ?? && faildata.gender?? && faildata.gender=='Male'> checked </#if>>Male</label>
						 	<label class="radio-inline"><input type="radio" name="Gender" value="Female" <#if faildata ?? && faildata.gender?? && faildata.gender=='Female'> checked </#if>>Female</label>
							<label class="radio-inline"><input type="radio" name="Gender" value="Transgender" <#if faildata ?? && faildata.gender?? && faildata.gender=='Transgender'> checked </#if>>Transgender</label>
						</div>
					 </div>
				</#if>
				 <div class="form-group">
				 	Language Known:
				 	<div class="checkbox checked-checkbox">
				 		   <label class="checkbox-inline" for="eng">
				 	        	<input type="checkbox" name="language" id="eng" <#if user ?? && user.language?? && user.language?contains("English")>checked</#if> <#if faildata ?? && faildata.language ?? && faildata.language?contains("English")>checked</#if> value="English">
						   English
						   </label>
						   <label class="checkbox-inline" for="hindi">
								<input type="checkbox" name="language" id="hindi" <#if user ?? && user.language?? && user.language?contains("Hindi")>checked</#if>  <#if faildata ?? && faildata.language ?? && faildata.language?contains("Hindi")>checked</#if> value="Hindi">
							Hindi
							</label>
							<label class="checkbox-inline" for="guj">
								<input type="checkbox" name="language" id="guj" <#if user ?? && user.language?? && user.language?contains("Gujarati")>checked</#if> <#if faildata ?? && faildata.language ?? && faildata.language?contains("Gujarati")>checked</#if>  value="Gujarati">
							Gujarati
							</label>
							<label class="checkbox-inline" for="chi">
								<input type="checkbox" name="language" id="chi" <#if user ?? && user.language?? && user.language?contains("Chinese")>checked</#if> <#if faildata ?? && faildata.language ?? && faildata.language?contains("Chinese")>checked</#if>  value="Chinese">
							Chinese
							</label>
				    </div>
				 </div>
			</div>
		</div> 
		<#if !user ?? || faildata ??>
		<div class="row left-gap">
			<div class="col-md-12">
				<div class="form-group">
			 		<fieldset>
    					<legend>Security Questions:</legend>
			    			1.Who was your childhood super hero?<br>
			    				 <input type="text"  class="form-control" <#if faildata ??>value="${faildata.answer1!}"</#if> placeholder="SuperMan" id="ans1" maxlength="60" name="answer1" required><br>
			    			2.What was your childhood nickname?<br>
			    				 <input type="text" class="form-control" <#if faildata ??>value="${faildata.answer2!}"</#if>  placeholder="Tom" id="ans2" maxlength="60" name="answer2" required><br>
    				</fieldset>
    			 </div>
    		 </div>
    	 </div>
    	 </#if>
    	 
    	 <div class="row left-gap">
					<div class="col-md-12">
		    			 <div class="form-group">
						    <div><label>Upload Photo:</label></div>
						    <#if user ?? && user.pic ??>
						    	<#list user.pic as userimg>
						    		<span id="${userimg.imgid}" class="delete-image">
									<span class="uploadedimage"><img src="data:image/jpg;base64,${userimg.base64Image}" class="image" width="180" height="180"/>
									<span class="del-image"><i class="material-icons">clear</i></span></span></span>
								</#list>
							</#if>
							<div class="input-images"></div>
						 </div>
					  </div>
		</div>
  	<div id="main-container">
  			<#if user ??>
  				<#list user.address as useradd>
  				<div class="container-item">
				  <div class="row left-gap" id="add-design">
					    <h3  class="head-gap">Address Field:</h3>
						<div class="col-md-5 col-sm-5 gap">
							<input type="hidden" name="addressid" value="${useradd.addressid}">
							  <div class="form-group">
								<p class="add-head">Address line1:</p>
									<input type="text" class="form-control add-head" value="${useradd.add1}" placeholder="Address" maxlength="50" name="address[${useradd?index}].add1" required>
							   </div>
							   <div class="form-group"><p class="add-head">City: </p><input type="text"  value="${useradd.city}" placeholder="Ahmedabad" id="city_0" maxlength="50" class="form-control add-head" name="address[${useradd?index}].city" required></div>
							   <div class="form-group"><p class="add-head"> Country: </p><input type="text" placeholder="India" value="${useradd.country}" id="country_0" maxlength="50" class="form-control add-head" name="address[${useradd?index}].country" required></div>
							   <div class="form-group"><a href="javascript:void(0)" class="remove-item btn btn-sm btn-danger add-head remove-data" id="remove-btn">Remove</a></div>
						</div>
						<div class="col-md-2 col-sm-2"></div>
						<div class="col-md-5 col-sm-5 gap right-gap">
							   <div class="form-group">
								<p>Address line2:</p>
									<input type="text" maxlength="50" value="${useradd.add2}" placeholder="Address" class="form-control" name="address[${useradd?index}].add2" required>
							   </div>
							   <div class="form-group"><p>State:</p><input type="text" placeholder="Gujarat" maxlength="50" value="${useradd.state}" id="state_0" class="form-control" name="address[${useradd?index}].state" required></div>
							   <div class="form-group"><p>Pincode:</p><input type="text" placeholder="371234" maxlength="6"  value="${useradd.pincode}" class="form-control" name="address[${useradd?index}].pincode" id="pincode_0" required></div>
					 	</div>
				   </div>
				</div>
				</#list>
			 <#elseif faildata ??>
  				<#list faildata.address as useradd>
  					<div class="container-item">
				  <div class="row left-gap" id="add-design">
					    <h3  class="head-gap">Address Field:</h3>
						<div class="col-md-5 col-sm-5 gap">
							<input type="hidden" name="addressid" value="${useradd.addressid}">
							  <div class="form-group">
								<p class="add-head">Address line1:</p>
									<input type="text" class="form-control add-head" value="${useradd.add1}" placeholder="Address" maxlength="50" name="address[${useradd?index}].add1" required>
							   </div>
							   <div class="form-group"><p class="add-head">City: </p><input type="text"  value="${useradd.city}" placeholder="Ahmedabad" id="city_0" maxlength="50" class="form-control add-head" name="address[${useradd?index}].city" required></div>
							   <div class="form-group"><p class="add-head"> Country: </p><input type="text" placeholder="India" value="${useradd.country}" id="country_0" maxlength="50" class="form-control add-head" name="address[${useradd?index}].country" required></div>
							   <div class="form-group"><a href="javascript:void(0)" class="remove-item btn btn-sm btn-danger add-head remove-data" id="remove-btn">Remove</a></div>
						</div>
						<div class="col-md-2 col-sm-2"></div>
						<div class="col-md-5 col-sm-5 gap right-gap">
							   <div class="form-group">
								<p>Address line2:</p>
									<input type="text" maxlength="50" value="${useradd.add2}" placeholder="Address" class="form-control" name="address[${useradd?index}].add2" required>
							   </div>
							   <div class="form-group"><p>State:</p><input type="text" placeholder="Gujarat" maxlength="50" value="${useradd.state}" id="state_0" class="form-control" name="address[${useradd?index}].state" required></div>
							   <div class="form-group"><p>Pincode:</p><input type="text" placeholder="371234" maxlength="6"  value="${useradd.pincode}" class="form-control" name="address[${useradd?index}].pincode" id="pincode_0" required></div>
					 	</div>
				   </div>
				</div>
				</#list>
				<#else>
				   <div class="container-item">
					  <div class="row left-gap" id="add-design">
						    <h3  class="head-gap">Address Field:</h3>
							<div class="col-md-5 col-sm-5 gap">
								  <div class="form-group">
									<p class="add-head">Address line1:</p>
										<input type="text" class="form-control add-head" placeholder="Address" maxlength="50" name="address[0].add1" required>
								   </div>
								   <div class="form-group"><p class="add-head">City: </p><input type="text" placeholder="Ahmedabad" id="city_0" maxlength="50" class="form-control add-head" name="address[0].city" required></div>
								   <div class="form-group"><p class="add-head"> Country: </p><input type="text" placeholder="India" id="country_0" maxlength="50" class="form-control add-head" name="address[0].country" required></div>
								   <div class="form-group"><a href="javascript:void(0)" class="remove-item btn btn-sm btn-danger add-head remove-data" id="remove-btn">Remove</a></div>
							</div>
							<div class="col-md-2 col-sm-2"></div>
							<div class="col-md-5 col-sm-5 gap right-gap">
								   <div class="form-group">
									<p>Address line2:</p>
										<input type="text" maxlength="50" placeholder="Address" class="form-control" name="address[0].add2" required>
								   </div>
								   <div class="form-group"><p>State:</p><input type="text" placeholder="Gujarat" maxlength="50" id="state_0" class="form-control" name="address[0].state" required></div>
								   <div class="form-group"><p>Pincode:</p><input type="text" placeholder="371234" maxlength="6" class="form-control" name="address[0].pincode" id="pincode_0" required></div>
						 	</div>
					   </div>
					</div>
				</#if>
	</div>
		<div class="form-group">
			<a id="add-more" href="javascript:;" class="btn btn-primary left-gap add-btn">Add More Address</a>
		 </div>
		 <div class="form-group">
			 <input type="submit" value="Submit" class="btn btn-success left-gap" id="submit-btn">
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