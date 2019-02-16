$(function(){	
	$("#residence").on("click",function(){
		AjaxZip3.zip2addr('zipcode','','address','address');
	});
	
	$('#jquery_reset_perfect').on('click', function(){
		  $('.form-control').val('');
		});
	
});