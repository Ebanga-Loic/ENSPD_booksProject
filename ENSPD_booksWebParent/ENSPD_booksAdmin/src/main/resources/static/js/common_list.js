function clearFilter() {
	window.location = moduleURL;
}

function showDeleteConfirmModal(link, entityName) {
	entityId = link.attr("entityId");

	$("#yesButton").attr("href", link.attr("href"));
	$("#confirmText").text("Etes-vous sûr de vouloir supprimer ceci"
		+ entityName + " ID " + entityId + "?");
	$("#confirmModal").modal();
}