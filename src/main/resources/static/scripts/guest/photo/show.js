console.log("photo/show.js OK");

// Prende l'id dalla query string che proviene da index.html
const URLParams = new URLSearchParams(window.location.search);
const photoId = URLParams.get('photoId');
console.log("photoId: ", photoId);

showPhoto(photoId);
showComments(photoId);


//#############
//  FUNCTIONS
//#############

// SHOW PHOTO
function showPhoto(id) {
	console.log('http://localhost:8080/api/photos/' + id);
    axios
        .get('http://localhost:8080/api/photos/' + id)
        .then((res) => {
            console.log('richiesta ok', res.data);

			const photo = res.data;

			// Prepare category string to be displayed in html
		    let cats = '';
		    photo.categories.forEach(category => {
		    	cats += category.name + ', ';
		    });
		    cats = cats.slice(0, cats.length-2);
		                
		    document.querySelector('#photoId').innerHTML = photo.id;
		    document.querySelector('#photoTitle').innerHTML = photo.title;
		    document.querySelector('#photoTag').innerHTML = photo.tag;
		    document.querySelector('#photoCategories').innerHTML = cats;
		    document.querySelector('#photoDescription').innerHTML = photo.description;
		
		    document.querySelector('#photoUrl').src = photo.url;
		    document.querySelector('#modalPhotoUrl').src = photo.url;
		    document.querySelector('#photoUrl').alt = photo.title;
        })
        .catch((res) => {
            console.error('errore nella richiesta: ', res);
        });
}

// SHOW COMMENTS
function showComments(id) {
    axios
        .get('http://localhost:8080/api/photos/' + id)
        .then((res) => {
            console.log('richiesta ok', res.data);

			const photo = res.data;

			// IF Comments not found
		    console.log("photo.comments: ", photo.comments);
		    if (photo.comments.length > 0) {
		        document.querySelector('#no_comments').classList.add("d-none");
		    } else {
		        document.querySelector('#commentList').classList.add("d-none");
		    }
		    
		    document.querySelector('#commentList').innerHTML = '';
		    photo.comments.forEach(comment => {
		        console.log(comment);
		        document.querySelector('#commentList').innerHTML += `
		        <div>
			        <div class="card mb-4">
					  <div class="card-body">
					    <div class="authorComment fw-bold">${comment.author}</div>
					    <p class="contentComment">${comment.content}</p>
					  </div>
					</div>
		        </div>
		        ` 
		    });
        })
        .catch((res) => {
            console.error('showComments(id) ERROR: ', res);
        });
}

function addComment(photoId) {
	const id = photoId.innerText;
	console.log("photoId: ", id);

	const url = `http://localhost:8080/api/comments/photos/${id}/create`;
	
	axios
		.post(url, {
			author: document.querySelector("#authorComment").value,
			content: document.querySelector("#contentComment").value
		})
		.then(res => {
			console.log("Insert Comment OK: ", res);
			resetValidationErrors();
			
			// Reset comment form fields
			document.querySelector('#authorComment').value = '';
			document.querySelector('#contentComment').value = '';
			
			showComments(id);
			
			// Scroll page show.html to comment list
			const commentList = document.querySelector("#commentList");
			commentList.scrollIntoView();
		})
		.catch(res => {
			console.error("Insert Comment ERROR: ", res);
			showValidationErrors(res.response.data.errors);
		})
		
}

// SHOW VALIDATION ERRORS
function showValidationErrors(errorList) {
    console.error("Errori di validazione: ", errorList);

    resetValidationErrors();
    errorList.forEach(error => {
        document.querySelector("#validation_errors").innerHTML += "<li>"+error.defaultMessage+"</li>";
        document.querySelector(`#${error.field}_err`).innerHTML += "<li>"+error.defaultMessage+"</li>";
    })
}

// RESET VALIDATION ERRORS
function resetValidationErrors() {
    document.querySelector("#validation_errors").innerHTML = '';
    // gets all html elements that have the id with the suffix _err
    document.querySelectorAll("[id$=_err]").forEach(element => { 
        element.innerHTML = "";
    });
}