const URLParams = new URLSearchParams(window.location.search);
let tagKeyword = URLParams.get('tagKeyword');
let options = document.querySelectorAll('option');
options.forEach(option => {
	if (option.value == tagKeyword) {
		option.setAttribute('selected', '');
	}
})

let nameKeyword = URLParams.get('nameKeyword');
let nameKeywordInput = document.querySelector('#nameKeyword');
nameKeywordInput.value = nameKeyword;

// For admin/index.html - Filter photoList by tagSet
function filterByTag(tagKeyword) {
	const URLParams = new URLSearchParams(window.location.search);
	let nameKeyword = URLParams.get('nameKeyword');
	console.log("nameKeyword: ", nameKeyword);
	
	if(nameKeyword == undefined) {
		nameKeyword = '';
	}
	
	
	
	location.href = '/admin/photos?nameKeyword='+nameKeyword+'&tagKeyword='+tagKeyword;
}

function resetSearch(nameKeyword) {
	nameKeyword.value = '';
	location.href = '/admin/photos?nameKeyword=&tagKeyword=';
}