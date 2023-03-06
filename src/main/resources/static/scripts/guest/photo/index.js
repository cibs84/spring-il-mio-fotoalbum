console.log("photo/index.js OK");

photoList(nameKeyword, tagKeyword);

const tagList = [];

function photoList(nameKeyword, tagKeyword = "") {
	const nameKey = nameKeyword.value;
	const tagKey = tagKeyword.value;
	console.log("nameKeyword: ", nameKeyword);
	console.log("tagKeyword: ", tagKeyword);
	console.log("nameKey: ", nameKey);
	console.log("tagKey: ", tagKey);
	
	
	
    axios
        .get('http://localhost:8080/api/photos?nameKeyword=' + nameKey + '&tagKeyword=' + tagKey) // method e endpoint
        .then((res) => {
            // codice da eseguire se la richiesta è andata a buon fine
            console.log('Richiesta Ok: ', res);

            // SE non ci sono photos
            if (!res.data) {
				console.log("IFFFFFFFFFFFFFFFFFFFFF");
                document.querySelector('#photos').classList.add("d-none");
                document.querySelector('#no_photos').classList.remove("d-none");
            } else {
				console.log("ELSEEEEEEEEEEEEEEEEEEEEEEEEEEE");
				document.querySelector('#photos').classList.remove("d-none");
                document.querySelector('#no_photos').classList.add("d-none");
                
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
	                
	                // Creates 'listTag'
	                if(tagList.includes(photo.tag) === false) {
						tagList.push(photo.tag);
					}
	            });
	            console.log("TAGLIST: ", tagList);
				// TAG SELECT
                const tagSelect = document.querySelector('#tagKeyword');
                tagSelect.innerHTML = '';

				const optionDisabled = document.createElement("option");
				optionDisabled.innerHTML = 'Filter by tag';
				optionDisabled.value = "";
				optionDisabled.setAttribute('disabled', '');
				optionDisabled.setAttribute('selected', '');

				const optionAllTags = document.createElement("option");
				optionAllTags.innerHTML = 'All Tags';
				optionAllTags.value = "";
				
				tagSelect.appendChild(optionDisabled);
				tagSelect.appendChild(optionAllTags);

	            tagList.forEach((tag, index) => {
					window['optTag_'+index] = document.createElement("option");
					window['optTag_'+index].innerHTML = tag;
					window['optTag_'+index].value = tag;
					
					tagSelect.appendChild(window['optTag_'+index]);
				})
            }

        })
        .catch((err) => {
            // codice da eseguire se la richiesta non è andata a buon fine
            console.error('Errore nella richiesta: ', err);
            alert("Erroreeee");
        })
}