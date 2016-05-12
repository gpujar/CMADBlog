$(document).ready(function() {
	/*$("#logout").click(function(e) {
		var token;
		if(typeof(Storage) !== "undefined") {
			alert("storage supports");
			token = sessionStorage.getItem('token');
			}
		$.ajax({
			url : 'http://localhost:8080/Blog/rest/user/logout',
			headers: {
		        //"token":'Basic ' + "7b174366-abd2-4f45-97ea-456a87b55775"
				//"token":'Basic ' + token
				'Authorization': 'Basic ' + token
		    },
		    beforeSend: function (request)
            {
		    	request.setRequestHeader('Authorization', 'Basic ' + token);
                alert(session.getAttribute("token"));
            },
			type : 'POST',
			success : function(data, textStatus, jqXHR) {
				var url = "http://localhost:8080/Blog/index.html";
				$(location).attr('href', url);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			}
		});
		e.preventDefault();
	},*/
	
//	function(){
	
		/*$.ajax({
			url : 'http://localhost:8080/Blog/rest/blog',
			headers: {
		        //"token":'Basic ' + "7b174366-abd2-4f45-97ea-456a87b55775"
				//"token":'Basic ' + token
				'Authorization': 'Basic ' + token
		    },
		    beforeSend: function (request)
            {
		    	var token;
		    	if(typeof(Storage) !== "undefined") {
		    		alert("storage supports");
		    		token = sessionStorage.getItem('token');
		    		}
		    	request.setRequestHeader('Authorization', 'Basic ' + token);
             //   alert(session.getAttribute("token"));
            },
			type : 'GET',
			dataType : "Json",
			success : function(data, textStatus, jqXHR) {
				//var url = "http://localhost:8080/Blog/index.html";
				//$(location).attr('href', url);
				data =  JSON.stringify(data); 
				alert("Got response" + data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			}
		});*/
	
	$.ajax({
		url : 'http://localhost:8080/Blog/rest/blog/search/Blog',		
	    beforeSend: function (request)
        {
	    	var token;
	    	if(typeof(Storage) !== "undefined") {
	    		alert("storage supports");
	    		token = sessionStorage.getItem('token');
	    		}
	    	request.setRequestHeader('Authorization', 'Basic ' + token);
         //   alert(session.getAttribute("token"));
        },
		type : 'GET',
		dataType : "Json",
		success : function(data, textStatus, jqXHR) {
			//var url = "http://localhost:8080/Blog/index.html";
			//$(location).attr('href', url);
			data =  JSON.stringify(data); 
			alert("Got response" + data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR.responseText);
		}
	});
//	}
	
//	) 
});


