<html>
<head>
    <title>${info.film.title}</title>
</head>
<body>
<h2>${info.film.title}</h2>
<div>
  <p><strong>Description: </strong> ${info.film.description}</p>
  <p><strong>Age restriction: </strong>${info.film.ageRestriction}</p>
  <p><strong>Release year: </strong>${info.film.releaseYear}</p>
  <p><strong>Hall #</strong>${info.hall.serialNumber}</p>
  <p><strong>Seats available: </strong>${info.hall.seatsNumber}</p>
</div>
<div>
  <img src="data:image/png;base64, ${info.film.poster}" width="300" height="400">
</div>
</body>
</html>
