$(document).ready( function () {
$('#userdetails').DataTable();
var table = $('#userdetails').DataTable();

$('#userdetails').on('click', '.delete', function(){
	var answer = confirm("Are you want to delete user record?");
	if(answer==true)
	{
			var userid = +this.id; 
				$.ajax({
				url: "deleteUser",
				type: "POST",
				data: {
					  userid : userid,
				      },
				success : function(data){
					$("table tr#delete"+userid).remove();
				}
				});
	 }
});
});