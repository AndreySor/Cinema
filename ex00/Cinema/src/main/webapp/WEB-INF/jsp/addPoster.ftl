<html>
<head>
    <title>Add Hall</title>
</head>
<body>
<#if errorMessage??>
    <div style="color:red;font-style:italic;">
        ${errorMessage}
    </div>
</#if>

<div>
    <fieldset>
        <legend>Add Poster</legend>
        <form name="poster" action="/admin/panel/addPoster" method="POST" enctype="multipart/form-data">
            Poster: <input type="file" name="poster" /><br/>
            <input type="submit" value="Create"/>
            <input type="button" value="Exit"
                   onclick="window.location.href = '/admin/panel/films'"/>
        </form>
    </fieldset>
</div>
</body>

</html>