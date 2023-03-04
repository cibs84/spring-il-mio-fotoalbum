console.log("photo/index.js OK");

photoList();

function photoList() {
    axios
        .get('http://localhost:8080/api/photos') // method e endpoint
        .then((res) => {
            // codice da eseguire se la richiesta è andata a buon fine
            console.log('Richiesta Ok: ', res);

            // SE non ci sono photos
            if (res.data.length > 0) {
                document.querySelector('#no_photos').classList.add("d-none");
            } else {
                document.querySelector('#photos').classList.add("d-none");
            }

            document.querySelector('#photos_table').innerHTML = '';
            res.data.forEach(photo => {
                console.log(photo.categories);
                
                // Prepare category string to be displayed in html
                let cats = '';
                photo.categories.forEach(category => {
                	cats += category.name + ', ';
                });
                cats = cats.slice(0, cats.length-2);
                
                document.querySelector('#photos_table').innerHTML += `
                <tr onclick="location.href = './photos/'+${photo.id}+'?photoId='+${photo.id};"
                	style="cursor:pointer;">
			      <td>${photo.id}</td>
			      <td>${photo.title}</td>
			      <td>${photo.description}</td>
			      <td>${photo.tag}</td>
			      <td>${cats}</td>
			      <td>
                    <img src="${photo.url}" width="100">
                  </td>
                </tr>
                `;

            });
        })
        .catch((err) => {
            // codice da eseguire se la richiesta non è andata a buon fine
            console.error('Errore nella richiesta: ', err);
        })
}