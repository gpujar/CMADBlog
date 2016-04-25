$(document).ready(function() {
	//alert(" sessionStorage.clickcount "+ sessionStorage.clickcount);
	$("#logout").click(function(e) {
		alert("Test");
		//var postData = $(this).serializeArray();
	/*	$.each(postData, function(i, field) {
			alert(field.name + ":" + field.value + " ");
		}); */
		//alert("postData  " + postData);
		var token;
		if(typeof(Storage) !== "undefined") {
		      //  if (sessionStorage.clickcount) {
		            //sessionStorage.token = json.token;
			token = sessionStorage.getItem('token');
		      //  }
			}
		$.ajax({
			url : 'http://localhost:8080/Blog/rest/user/logout',
			headers: {
		        //"token":'Basic ' + "7b174366-abd2-4f45-97ea-456a87b55775"
				"token":'Basic ' + token
		    },
		   /* beforeSend: function (request)
            {
		    	alert("Test 2");
                //request.setRequestHeader("Authority", authorizationToken);
		    	request.setRequestHeader('Authorization', 'Basic ' + "7b174366-abd2-4f45-97ea-456a87b55775");
                alert(session.getAttribute("token"));
                
            },*/
			type : 'POST',
			//data : postData, // JSON.stringify(postData),
			success : function(data, textStatus, jqXHR) {
				// data: return data from server
				//alert('done');
				//console.log(data);
				var url = "http://localhost:8080/Blog/index.html";
				$(location).attr('href', url);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			}
		});
		
		/*var url = "http://localhost:8090/Blog/blog_list.html";
		$(location).attr('href', url); */
		// STOP default action
		e.preventDefault();
		// e.unbind(); // unbind. to stop multiple form submit.
	}) 
});
