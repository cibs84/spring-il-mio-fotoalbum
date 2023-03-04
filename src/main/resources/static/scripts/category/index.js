function ingredientList() {
    axios
        .get('http://localhost:8080/api/ingredienti')
        .then((res) => {
            console.log('elenco ingredienti ok', res);

            // SE non ci sono ingredienti
            if (res.data.length > 0) {
                document.querySelector('#no_ingredients').classList.add("d-none");
            } else {
                document.querySelector('#ingredients').classList.add("d-none");
            }

            document.querySelector("#ingredients").innerHTML = '';
            res.data.forEach(ingrediente => {
                document.querySelector("#ingredients").innerHTML += `
                <input type="checkbox" class="form-check-input" value="${ingrediente.id}" 
				id="ingrediente_${ingrediente.id}">
				<label class="form-check-label me-3" for="ingrediente_${ingrediente.id}">${ingrediente.name}</label>
                `;
            })            
        })
        .catch((res) => {
            console.log("Errore nella creazione della lista Ingredienti: ", res.response.data.errors);
        });
}