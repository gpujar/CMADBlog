

$(document).ready(function() {

	$("#postblog").submit(function(e) {		
		var titleValue = $("#title").val();
		var contentValue = $("#content").val();
		if(typeof(Storage) !== "undefined") {
			token = sessionStorage.getItem('token');
		}
		var dataToSend = {title:titleValue,content:contentValue};
	//	dataToSend = dataToSend.serializeArray();
		$.ajax({
	        url: "http://localhost:8080/Blog/rest/blog",
	        method: "POST",
	        contentType: 'application/x-www-form-urlencoded',
	       /* headers: {		     
				'Authorization': 'Basic ' + token
		    },*/
	        data: dataToSend,
	        dataType: 'application/json',
	        beforeSend: function (xhr) {
	            xhr.setRequestHeader ("Authorization", "Basic " + token);
	        },
	        statusCode: {
	            404: function () {
	            	alert("Error........");
	            },
	            200: function (data) {
	            	alert("Success..........  ");
	            	var url = "http://localhost:8080/Blog/blog_list.html";
					$(location).attr('href', url);
	            }},
	        success : function(data, textStatus, jqXHR) {
				var url = "http://localhost:8080/Blog/index.html";
				$(location).attr('href', url);
			},
	         fail : function error(){
	        	 alert("Failure.......... ");
	             console.log('Error');
	         }
	    });
		e.preventDefault();
		// e.unbind(); // unbind. to stop multiple form submit.
	})
});
