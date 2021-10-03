<html>
<head>
    <style>
        <#include "css/style.css">
    </style>
    <title>Films List</title>
</head>
<body>
<h1>Films</h1>
<div>
    <table class="rwd-table">
        <tr>
            <th>Title</th>
            <th>Release year</th>
            <th>Age restriction</th>
            <th>Description</th>
            <th>Poster</th>
            <th></th>
        </tr>

        <#list films as film>
            <form name="film" action="/admin/panel/addPoster" method="POST">
                <tr>
                    <td>${film.title}</td>
                    <td>${film.releaseYear}</td>
                    <td>${film.ageRestriction}</td>
                    <td>${film.description}</td>
                    <td>${film.poster}</td>
                    <td><input type="url" value="Add Poster" id=${film.getFilmId()}/></td>
                </tr>
            </form>
        </#list>
    </table>
</div>
<br>
<br>
<input type="button" value="Add new Film"
       onclick="window.location.href = '/admin/panel/addNewFilm'"/>
</body>
</html>
