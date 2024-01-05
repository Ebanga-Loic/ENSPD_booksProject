dropdownTypes = $("#types");
dropdownFilieres = $("#filieres");

$(document).ready(function() {

	$("#fullDescription").richText();

	dropdownTypes.change(function() {
		dropdownFilieres.empty();
		getFilieres();
	});

	getFilieres();
});


function getFilieres() {
	typeId = dropdownTypes.val();
	url = typeModuleURL + "/" + typeId + "/filieres";

	$.get(url, function(responseJson) {
		$.each(responseJson, function(index, filieres) {
			$("<option>").val(filieres.id).text(filieres.name).appendTo(dropdownFilieres);
		});
	});
}

function checkUnique(form) {
	bookId = $("#id").val();
	bookName = $("#name").val();

	csrfValue = $("input[name='_csrf']").val();

	url = "[[@{/books/check_unique}]]";

	params = { id: bookId, name: bookName, _csrf: csrfValue };

	$.post(url, params, function(response) {
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