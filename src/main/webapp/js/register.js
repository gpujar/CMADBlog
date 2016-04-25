// $("#ajaxform").submit(function(event){
//	 alert("Test  123");
 // document.newForm.action = "http://localhost:8090/CMAD_Blog_Proj/rest/user/post";

//		alert("Test");
	//var postData = $(this).serializeArray();
	//console.log(postData);

	//console.log("Sending Data");
	//var formURL = $(this).attr("action");
	/*var JSONObject = {"firstName" : "Giriyappa",
			"lastName" : "Pujar",
			"id":"102",
			"emailId" : "giri.in.java@gmail.com"	
		};*/
	//var jsonData = JSON.parse( JSONObject );
//	$.ajax(
//	{
//	url : "http://localhost:8090/CMAD_Blog_Proj/rest/user/post",
//	//dataType: 'json',
//	contentType: "application/json",
//	type: 'POST',
//	data : {"firstName" : "Giriyappa",
//		"lastName" : "Pujar",
//		"id":"102",
//		"emailId" : "giri.in.java@gmail.com"	
//	},
//	success:function(data, textStatus, jqXHR) 
//	{
//	// data: return data from server
//		//console.log(data);
//	},
//	error: function(jqXHR, textStatus, errorThrown) 
//	{
//	// if fails     
//	}
//	});
	//e.preventDefault(); // STOP default action
	//e.unbind(); // unbind. to stop multiple form submit.

//});

// callback handler for form submit


$(document).ready(function(){
	
	/*$("#ajaxform").validate({
        rules: {
            "form-email": {
                required: true,
                email: true
            },  
            "form-password": {
                required: true,
                minlength: 6
            } 
        }
    }); */
	
        
        
	
$("#ajaxform").submit(function(e)
{
	alert("Test");
	
var postData = $(this).serializeArray();
$.each(postData, function(i, field){
    alert(field.name + ":" + field.value + " ");
});

alert("postData  "+postData)

//var formURL = $(this).attr("action");
//var jsonData = JSON.parse( formURL );
$.ajax(
{
url : 'http://localhost:8090/Blog/rest/user',
contentType: 'application/x-www-form-urlencoded',
type: 'POST',
data : postData, //JSON.stringify(postData),

success:function(data, textStatus, jqXHR) 
{
// data: return data from server
	// alert('done');
	alert(data);
	//var responce = JSON.stringify(data);
	var json = JSON.parse(data);
	alert(json["token"]);
	if(typeof(Storage) !== "undefined") {
	      //  if (sessionStorage.clickcount) {
	            sessionStorage.token = json.token;
	      //  }
		}
	//session.setAttribute("token", responce.token);
	alert(sessionStorage.token);
	var url = "http://localhost:8090/Blog/blog_list.html";
	$(location).attr('href', url);
},
error: function(jqXHR, textStatus, errorThrown) 
{
	console.log(jqXHR.responseText);
}
});
 // STOP default action
e.preventDefault();
// e.unbind(); // unbind. to stop multiple form submit.
})
});
//$("#ajaxform").submit(); // Submit  the FORM
