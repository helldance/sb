	function loadEditModal(p) {
		$("#edit-modal").removeClass("extra-wide");
		var $op_modal_body = $("#edit-modal > div.modal-body");
		$op_modal_body.empty();
		var href=encodeURI($(p).attr("href"));
		$op_modal_body.load(href);
		$("#primary-modal").modal("hide");
		$("#edit-modal").modal("show");
		return false;
	}

	function cancelEditModal(p) {
		$("#edit-modal").modal("hide");
		$("#primary-modal").modal("show");
		return false;
	}

	function cancelPrimaryModal(p) {
		$("#primary-modal").modal("hide");
		return false;
	}

