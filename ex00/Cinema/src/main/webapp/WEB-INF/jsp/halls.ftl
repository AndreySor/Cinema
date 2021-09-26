<#import "/spring.ftl" as spring/>
<html>
<head>
    <title>Halls List</title>
</head>
<body>
    <h1>HALLS</h1>
    <table>
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
</body>
</html>
