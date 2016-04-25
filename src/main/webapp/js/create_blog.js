

$(document).ready(function() {

	$("#postblog").submit(function(e) {
		alert("Test this .........  ");
		//var postData = $(this).serializeArray();
	/*	$.each(postData, function(i, field) {
			alert(field.name + ":" + field.value + " ");
		}); */
		//alert("postData  " + postData);
		var titleValue = $("#title").val();
		var contentValue = $("#content").val();
		//alert(sessionStorage.setItem('token', data.token));
		if(typeof(Storage) !== "undefined") {
		      //  if (sessionStorage.clickcount) {
		            //sessionStorage.token = json.token;
			token = sessionStorage.getItem('token');
			alert(token);
		      //  }
			}
		$.ajax({
	        url: "http://localhost:8090/Blog/rest/blog",
	        method: "POST",
	        data: {"title":titleValue,"content":contentValue},
	      //  dataType: 'application/json',
	        contentType: "application/json",
	        beforeSend: function (xhr) {
	            xhr.setRequestHeader ("Authorization", "Basic " + token);
	        },
	         success: function(result){
	              alert(result);
	             var url = "http://localhost:8090/Blog/blog_list.html";
	      		$(location).attr('href', url);
	         },
	         fail : function error(){
	             console.log('Error');
	         }
	    });
		/*var url = "http://localhost:8090/Blog/blog_list.html";
		$(location).attr('href', url); */
		// STOP default action
		e.preventDefault();
		// e.unbind(); // unbind. to stop multiple form submit.
	})
});
