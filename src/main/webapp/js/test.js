$("#login").submit(function(){
    alert("Submitted");
});


/* $.ajax({
    type: "POST",
    url: "http://localhost/RESTServiceCRUD/BookService.svc/SaveBook/0",
    data: JSON.stringify(bookData),
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    processData: true,
    success: function (data, status, jqXHR) {
        alert("success..." + data);
    },
    error: function (xhr) {
        alert(xhr.responseText);
    }
}); */