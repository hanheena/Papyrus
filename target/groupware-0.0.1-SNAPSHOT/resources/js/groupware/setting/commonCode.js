function codeTypeSave() {

	$("#exampleModalToggle").modal('hide');

	$.ajax({
		url: "/papyrus/commonCode/codeTypeSave",
		type: "POST",
		data: {
			codeValueType: $("#typeValue").val(),
			codeNameType: $("#typeName").val(),
		}, success: function(data) {

			var html = '';
			var selectHtml = '';

			for (var i = 0; i < data.length; i++) {
				html += '<tr><td><input type="checkbox" name="subCheck_type" value="' + data[i].codeTypeId + '"></td>';
				html += '<td>' + data[i].codeTypeId + '</td>';
				html += '<td>' + data[i].codeValue + '</td>';
				html += '<td>' + data[i].codeName + '</td>';

				selectHtml += '<option value="' + data[i].codeValue + '">' + data[i].codeName + '</option>';
			}

			$("#typeValue").val("");
			$("#typeName").val("");
			$("#codeTypeList").html(html);
			$("#codeValueSelect").html(selectHtml);
		}, error: function() {
			console.log("에러");
		}
	});
}

function codeSave() {

	$("#exampleModalToggle1").modal('hide');

	$.ajax({
		url: "/papyrus/commonCode/codeSave",
		type: "POST",
		data: {
			codeValueType: $("#codeValueSelect option:selected").val(),
			codeNameType: $("#codeValueSelect option:selected").text(),
			codeName: $("#codeName").val(),
		}, success: function(data) {
			var html = '';

			for (var i = 0; i < data.length; i++) {
				html += '<tr><td><input type="checkbox" name="subCheck_code" value="' + data[i].codeId + '"></td>';
				html += '<td>' + data[i].codeId + '</td>';
				html += '<td>' + data[i].codeTypeId + '</td>';
				html += '<td>' + data[i].codeValue + '</td>';
				html += '<td>' + data[i].codeName + '</td>';
				html += '<td>' + data[i].seq + '</td>';
			}

			$("#codeName").val("");
			$("#codeList").html(html);

		}, error: function() {
			console.log("에러");
		}
	});
}

function codeTypeDel() {

	var codeTypeArray = new Array();

	$("input[name='subCheck_type']:checked").each(function(e) {
		codeTypeArray.push($(this).val());
	});

	codeTypeArray = codeTypeArray.join(",");

	if (codeTypeArray != "") {
		$("#delValue").find("[name=valueDel]").val("");
		$("#delValue").find("[name=valueDel]").val(codeTypeArray);

		$("#delValue").attr({
			action: "/papyrus/commonCode/codeTypeDel",
			method: "post",
		}).submit();
	}
}

function codeDel() {

	var codeArray = new Array();

	$("input[name='subCheck_code']:checked").each(function(e) {
		codeArray.push($(this).val());
	});

	codeArray = codeArray.join(",");

	if (codeArray != "") {
		$("#delValue").find("[name=valueDel]").val("");
		$("#delValue").find("[name=valueDel]").val(codeArray);

		$("#delValue").attr({
			action: "/papyrus/commonCode/codeDel",
			method: "post",
		}).submit();
	}
}