$(document).ready(function() {
	
      
	$('.input-images').imageUploader({
		   imagesInputName: 'image',
		});

        $('#add-more').cloneData({
		  mainContainerId:'main-container',
		
		  cloneContainer:'container-item',
		
		  removeButtonClass:'remove-item',
		  
		  maxLimit: 0,
		
			minLimit: 1,
		
		 	removeConfirm:true,
		
		  removeConfirmMessage:'Are you sure want to delete?',
		  
		  regexName:  /(^.+?)([\[\d{1,}\]]{1,})(.+$)/i
		
		});
	
		let count=0;
		$("#add-btn").click(function(){
			count++;
		});
		$("#remove-btn").hide();
		if(count>0)
		{
			$("#remove-btn").show();
		}
		
		$("#email").keyup(function(){
			$.ajax({
				url:"checkUserExistDone",
				type:'POST',
				data:$("#myform").serialize(),
				cache:false,
				datatype:"html",
				success:function(response){
					$("#error").html(response);
				}
				
			})
		})
      
      });
      



