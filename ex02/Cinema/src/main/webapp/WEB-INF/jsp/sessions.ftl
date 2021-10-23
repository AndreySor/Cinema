<html>
<head>
    <style>
        <#include "css/session.css">
    </style>
    <title>Sessions</title>
</head>
<body>
<div class="header">
    <h1>Sessions</h1>
</div>
<div>
    <table class="table">
        <caption><h3>List of session</h3></caption>
        <thead>
            <tr>
                <th>Hall</th>
                <th>Film</th>
                <th>Ticket cost</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
            <#list sessions as session>
                <tr>
                    <td>${session.hall.serialNumber}</td>
                    <td>${session.film.title}</td>
                    <td>${session.ticketCost}</td>
                    <td>${session.date?datetime?string.short}</td>
                </tr>
            </#list>
        </tbody>
    </table>
</div>
<div>
    <h3>Select hall, film and ticket cost for featuring session</h3>
    <form action="/admin/panel/addSession" method="POST">
        <select name="selectedHall">
            <optgroup>
                <#list halls as hall>
                    <option value=${hall.hallId}>${hall.serialNumber}</option>
                </#list>
            </optgroup>
        </select>
        <select name="selectedFilm">
            <optgroup>
                <#list films as film>
                    <option value="${film.filmId}">${film.title}</option>
                </#list>
            </optgroup>
        </select>
        <input name="ticketCost" type="number" value="0" required>
        <input name="sessionDate" type="datetime-local" required>
        <input type="submit" name="submit" value="Submit">
    </form>
</div>
</body>
</html>