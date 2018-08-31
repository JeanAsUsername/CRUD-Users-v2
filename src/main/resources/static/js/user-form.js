
document.addEventListener("DOMContentLoaded", function() {
	
	/* Add a new language */
	
	let languageIndex = document.querySelectorAll('.form__language').length - 1;
	
	const addButton = document.querySelector('.add__submit'),
		  languageSelect = document.querySelector('.add__select')
	
	function addLanguage() {
		
		let languageList = document.querySelector('.form__languages'),
			// create the language
			language = document.querySelector('.dummy__language').cloneNode(true),
			// get the delete button
			deleteButton = language.querySelector('.options__delete'),
			// get language data from the option
			languageName = languageSelect.options[languageSelect.selectedIndex].value
			languageId = languageSelect.options[languageSelect.selectedIndex].dataset.languageid
			
			// remove the dummy class (display:none)
			language.classList.remove('dummy__language')
			
			// change the language data
			language.querySelector('.data__id').value= languageId
			language.querySelector('.data__id').name= 'languages[' + languageIndex + '].id'
			
			language.querySelector('.language__name').textContent = languageName
			
			// delete from options
			deleteFromOptions(languageName)
			
			// set the data to the delete button, this is set to the option tag when the event is fired
			deleteButton.dataset.languagename = languageName
			deleteButton.dataset.languageid = languageId
			
			// delete listener
			deleteButton.addEventListener('click', (e) => {
				deleteFromLanguages(e)
			})
			
			// insert the new language
			languageList.append(language)
			
		languageIndex++
	}
	
	// delete the inserted language from the select
	function deleteFromOptions(languageName, languageId) {
		
		let options = languageSelect.querySelectorAll('option');
		
		options.forEach((option) => {
			if (option.value === languageName) {
				languageSelect.removeChild(option)
			}
		})
		
	}
	
	// delete language
	
	let deleteButtons = document.querySelectorAll('.options__delete')
	
	function deleteFromLanguages(e) {
		
		let language = e.target.parentNode.parentNode,
			languageId = e.target.dataset.languageid,
			languageName = e.target.dataset.languagename,
			// restore the option node
			option = document.createElement('option')
			
		// set the data to the node
		option.value = languageName
		option.textContent = languageName
		option.dataset.languageid = languageId
		
		// remove the language
		language.remove()
		
		// append the option node
		languageSelect.appendChild(option)
		
		languageIndex--
	}
	
	// events
	addButton.addEventListener('click', addLanguage)
	
	deleteButtons.forEach((deleteButton) => {
		
		let languageName = deleteButton.dataset.languagename,
			languageId = deleteButton.dataset.languageid
		
		deleteButton.addEventListener('click', (e) => {
			
			deleteFromLanguages(e)
			
		})
	})
	
	
	// ----------------------------------------------------------
})