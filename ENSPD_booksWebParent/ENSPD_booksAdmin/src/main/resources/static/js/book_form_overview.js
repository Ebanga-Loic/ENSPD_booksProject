dropdownTypes = $("#types");
dropdownFilieres = $("#filieres");

$(document).ready(function() {

	$("#fullDescription").richText();

	dropdownTypes.change(function() {
		dropdownFilieres.empty();
		getFilieres();
	});

	getFilieresForNewForm();
});

function getFilieresForNewForm() {
	filiereIdField = $("#filiereId");
	editMode = false;

	if (filiereIdField.length) {
		editMode = true;
	}

	if (!editMode) getFilieres();
}


function getFilieres() {
	typeId = dropdownTypes.val();
	url = typeModuleURL + "/" + typeId + "/filieres";

	$.get(url, function(responseJson) {
		$.each(responseJson, function(index, filiere) {
			$("<option>").val(filiere.id).text(filiere.name).appendTo(dropdownFilieres);
		});
	});
}

function checkUnique(form) {
	bookId = $("#id").val();
	bookName = $("#name").val();

	csrfValue = $("input[name='_csrf']").val();

	params = { id: bookId, name: bookName, _csrf: csrfValue };

	$.post(checkUniqueUrl, params, function(response) {
		if (response == "OK") {
			form.submit();
		} else if (response == "Duplicate") {
			showWarningModal("Il existe un autre livre portant le nom " + bookName);
		} else {
			showErrorModal("Réponse inconnue du serveur.");
		}

	}).fail(function() {
		showErrorModal("Ne peut se connecter au serveur.");
	});

	return false;
}