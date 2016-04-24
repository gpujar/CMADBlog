/*
$(document).ready(function() {
      var lock = new Auth0Lock(
      // All these properties are set in auth0-variables.js
      AUTH0_CLIENT_ID,
      AUTH0_DOMAIN
    );

    var userProfile;

    $('.form-signin').click(function(e) {
      e.preventDefault();
      lock.show({ authParams: { scope: 'openid' } });
    });
    var hash = lock.parseHash(window.location.hash);

    if (hash) {
      if (hash.error) {
        console.log("There was an error logging in", hash.error);
        alert('There was an error: ' + hash.error + '\n' + hash.error_description);
      } else {
        //save the token in the session:
        localStorage.setItem('id_token', hash.id_token);
      }
    }
	    //retrieve the profile:
    var id_token = localStorage.getItem('id_token');
    if (id_token) {
      lock.getProfile(id_token, function (err, profile) {
      if (err) {
        return alert('There was an error geting the profile: ' + err.message);
      }
      $('.login-box').hide();
      $('.logged-in-box').show();
      $('.nickname').text(profile.nickname);
      $('.nickname').text(profile.name);
      $('.avatar').attr('src', profile.picture);
            	
      });
    }

    $.ajaxSetup({
      'beforeSend': function(xhr) {
        if (localStorage.getItem('id_token')) {
          xhr.setRequestHeader('Authorization',
                'Bearer ' + localStorage.getItem('id_token'));
        }
      }
    });

    $('.btn-api').click(function(e) {
      // Just call your API here. The header will be sent
      $.ajax({
        url: 'http://localhost:3001/secured/ping',
        method: 'GET'
      }).then(function(data, textStatus, jqXHR) {
        alert("The request to the secured enpoint was successfull");
      }, function() {
        alert("You need to download the server seed and start it to call this API");
      });
    });


});
 */

$(document).ready(function() {

	$("#login").submit(function(e) {
		var postData = $(this).serializeArray();
	/*	$.each(postData, function(i, field) {
			alert(field.name + ":" + field.value + " ");
		}); */
		alert("postData  " + postData);
		$.ajax({
			url : 'http://localhost:8090/Blog/rest/user/login',
			contentType : 'application/x-www-form-urlencoded',
			type : 'POST',
			data : postData, // JSON.stringify(postData),
			success : function(data, textStatus, jqXHR) {
				// data: return data from server
				//alert('done');
				//console.log(data);
				//sessionStorage.setItem("token", "Some Value")
				var responce = JSON.stringify(data);
				alert(responce.token);
				session.setAttribute("token", responce.token);
				var url = "http://localhost:8090/Blog/blog_list.html";
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
