<html>
<head>
    <style>
        <#include "css/sessionSearching.css">
    </style>
    <title>Sessions searching</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3/jquery.min.js" ></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#searchField").keyup(function (e) {

                $("#result").html('');
                e.preventDefault();

                let url = $("#info").attr('action');
                let rq = $("#searchField").val();

                $.ajax({
                    type: "GET",
                    url: url,
                    contentType: "application/json",
                    dataType: "json",
                    data: {filmName: rq},
                    success: function (data) {
                        $.each(data.sessions, function (key, value) {
                            $("#result").append('<div><p>'+value.film.name+'</p><p>'+value.dateTime+'</p></div>')
                        });
                    },
                    error: function (jqXhr, textStatus, errorMessage) {
                        console.log("Error", errorMessage);
                        $("#result").append('<p>Not found any sessions</p>');
                    }
                });
            })
        })
    </script>
</head>
<body>
<form action="/sessions/search" id="info">
    <input name="filmName" type="text" placeholder="Search..." id="searchField">
    <button id="submitRequest">Search</button>
</form>
<div class="list-of-film" id="result">
</div>
</body>
</html>