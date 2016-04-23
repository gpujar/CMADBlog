$.ajax({
        url: "http://localhost:8080/MyWebService/api/myService/jsonpost",
        method: "POST",
        data: jsonObj,
        dataType: 'application/json',
        contentType: "application/json",
         success: function(result){
              alert(result);
         },
         fail : function error(){
             console.log('Error');
         }
    });