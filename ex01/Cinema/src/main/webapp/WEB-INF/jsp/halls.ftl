<html>
<head>
    <style>
        <#include "css/style.css">
    </style>
    <title>Halls List</title>
</head>
<body>
<h1>HALLS</h1>
<div>
    <table class="rwd-table">
        <tr>
            <th>Serial number</th>
            <th>Number of seats</th>
        </tr>
        <#list halls as hall>
        <tr>
            <td>${hall.serialNumber}</td>
            <td>${hall.seatsNumber}</td>
        </tr>
        </#list>
    </table>
</div>
<br>
<br>
<input type="button" value="Add new Hall"
       onclick="window.location.href = '/admin/panel/addNewHall'"/>
</body>
</html>
