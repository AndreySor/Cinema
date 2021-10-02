<html>
<head>
    <title>Add Film</title>
</head>
<body>
<#if errorMessage??>
    <div style="color:red;font-style:italic;">
        ${errorMessage}
    </div>
</#if>

<div>
    <fieldset>
        <legend>Add Film</legend>
        <form name="film" action="/admin/panel/saveNewFilm" method="POST">
            Title          : <input type="text" name="title" /><br/>
            Release year   : <input type="number" name="releaseYear" /><br/>
            Age restriction: <input type="number" name="ageRestriction" /><br/>
            Description    : <input type="text" name="description" /><br/>
            Poster         : <input type="file" name="poster" /><br/>
            <input type="submit" value="Create"/>
            <input type="button" value="Exit"
                   onclick="window.location.href = '/admin/panel/films'"/>
        </form>
    </fieldset>
</div>
</body>

</html>