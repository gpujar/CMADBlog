
$(document).ready(function() {

	$("#login").submit(function(e) {
		var postData = $(this).serializeArray();
	/*	$.each(postData, function(i, field) {
			alert(field.name + ":" + field.value + " ");
		}); */
		alert("postData  " + postData);
		$.ajax({
			url : 'http://localhost:8080/Blog/rest/user/login',
			contentType : 'application/x-www-form-urlencoded',
			type : 'POST',
			data : postData, // JSON.stringify(postData),
			success : function(data, textStatus, jqXHR) {				
				//session.setAttribute("token", responce.token);
				//var json = JSON.parse(data);
				//alert(json["token"]);
				//alert(data.token);
				alert(data.token);
				if(typeof(Storage) !== "undefined") {
				      //  if (sessionStorage.clickcount) {
				            //sessionStorage.token = json.token;
					sessionStorage.setItem('token', data.token);
				      //  }
					}else{
						alert("Storage doesn't supports......");
					}
				//session.setAttribute("token", responce.token);
				//alert(sessionStorage.token);
				var url = "http://localhost:8080/Blog/blog_list.html";
				$(location).attr('href', url);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			}
		});
		// STOP default action
		e.preventDefault();
		// e.unbind(); // unbind. to stop multiple form submit.
	})
});
