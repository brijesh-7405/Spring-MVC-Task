$(document).ready(function() {
	
  		 $('#myform').on('click', '.delete-image', function(){
			var answer = confirm("Are you want to delete image?");
				if(answer==true)
				{
						var imageid = +this.id; 
						$.ajax({
						url: "removeImage",
						type: "POST",
						data: {
							   imgId : imageid,
							  }, 
						success : function(data){
						$("#"+imageid).remove();
						}
					    });
				 }
      		});
      		
			$("#firstname").keyup(function(e){
				e.preventDefault();
				$(".error").remove();
				var fname = $('#firstname').val();
			    for(let i=0;i < fname.length;i++)
			    {
			     var ch = fname.charAt(i);
			     if (!(ch>='a'&&ch<='z') && !(ch>='A'&&ch<='Z')) 
			     {
					  $('#firstname').after('<span class="error">*Only Alphabet are Allowed</span>');
					  break;
			     }
			    }
			  });
			    
			  $("#lastname").keyup(function(e){
						e.preventDefault();
						$(".error").remove();
						 var lname = $('#lastname').val();
					  for(let i=0;i < lname.length;i++){
					     var ch = lname.charAt(i);
					     if (!(ch>='a'&&ch<='z') && !(ch>='A'&&ch<='Z')) 
					     {
							  $('#lastname').after('<span class="error">*Only Alphabet are Allowed</span>');
							  break;
					     }
					    }
			    });
			  
			  
			  $("#pwd").keyup(function(e){
						$(".error").remove();
						 var pwd = $('#pwd').val();
					  var regex = "^(?=.*[0-9])"
		            + "(?=.*[a-z])(?=.*[A-Z])"
		            + "(?=.*[@#$%^&+=])"
		            + "(?=\\S+$).{8,20}$";
					  if(pwd.length<5 || pwd.length>14)
					  {
						   $('#pwd').after('<span class="error">*Password must be 5 to 14 character</span>');
					  }
					  else if(!pwd.match(regex))
					  {
						$('#pwd').after('<span class="error">*Password must be strong</span>');
						
					  }
			  });
			   $("#repwd").keyup(function(e){
						$(".error").remove();
						 var pwd = $('#pwd').val();
						 var repwd = $('#repwd').val();
					  if(repwd != pwd)
					  {
						 $('#repwd').after('<span class="error">*Password should be same</span>');
					  }
				});
			$("#email").keyup(function(e){
					$(".error").remove();
					var mail = $("#email").val();
					var mailFormat="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
	        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	        
					if(!mail.match(mailFormat)){
						$('#email').after('<span class="error">*Please enter valid Email</span>');
					}
			});
			$("#phone").keyup(function(e){
					$(".error").remove();
					var phone = $("#phone").val();
				if(phone.match("[^0-9]"))
				{
					$('#phone').after('<span class="error">*Only Numbers are allowed</span>');
				}
			});
			
			$(".checked-radio").mouseover(function(){
				$(".r-btn-error").remove();
				if($("input[name='Gender']:checked").length == 0)
				{
					$(".checked-radio").after('<span class="r-btn-error">*Required Field</span>');
				}
			})
			$(".checked-checkbox").mouseover(function(){
				$(".btn-error").remove();
				if($("input[name='language']:checked").length == 0)
				{
					$(".checked-checkbox").after('<span class="btn-error">*Required Field</span>');
				}
			})
			 $('input[type="file"]').change(function(e) {
				 var files = e.target.files;
				 filesLength = files.length ;
           		for (var i = 0; i < filesLength ; i++) 
            	{
					$(".error").remove();
	                var file = e.target.files[i].name;
	                 var extn = file.substring(file.lastIndexOf('.') + 1).toLowerCase();
	                 if (extn == "gif" || extn == "png" || extn == "jpg" || extn == "jpeg")
             		{}
             		else{
						$('input[type="file"]').after('<span class="error">*Please Select only images!</span>');
					}
	                }
			});
			
			     $("#pincode_0").keyup(function(){
					$(".error").remove();
							var pincode = $("#pincode_0").val();
							if(pincode.match("[^0-9]"))
							{
								$('#pincode_0').after('<span class="error">*Only Numbers are allowed</span>');
							}
	   				 })
	    
			var count=0;
			$(".add-btn").click(function(){
					count++;
					for(let i=1;i<=count;i++)
					{
						$("#pincode_"+i).keyup(function(e){
							$(".error").remove();
							console.log("#pincode_"+i)
							var pincode = $("#pincode_"+i).val();
							if(pincode.match("[^0-9]"))
							{
								$('#pincode_'+i).after('<span class="error">*Only Numbers are allowed</span>');
							}
						})
					}
				});
});