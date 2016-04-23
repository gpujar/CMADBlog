 function formAction()
{
  document.newForm.action = "http://localhost:8090/CMAD_Blog_Proj/rest/user";
} 

// callback handler for form submit
$("#ajaxform").submit(function(e)
{
var postData = $(this).serializeArray();
console.log(postData);
console.log("Sending Data");
//var formURL = $(this).attr("action");
var JSONObject = {"firstName" : "Giriyappa",
		"lastName" : "Pujar",
		"id":"102",
		"emailId" : "giri.in.java@gmail.com"	
	};
var jsonData = JSON.parse( JSONObject );
$.ajax(
{
url : "http://localhost:8090/CMAD_Blog_Proj/rest/user",
dataType: 'json',
type: 'post',
data : jsonData,
success:function(data, textStatus, jqXHR) 
{
// data: return data from server
	console.log(data);
},
error: function(jqXHR, textStatus, errorThrown) 
{
// if fails     
}
});
e.preventDefault(); // STOP default action
e.unbind(); // unbind. to stop multiple form submit.
});
//$("#ajaxform").submit(); // Submit  the FORM
