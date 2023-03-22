/**
 * 
 */
$(function(){
    
});

function selectMajors(){
	$('select[name=depCode]').change(function (e) { 
		e.preventDefault();
	
		let depCode = $(this).val();

		let url = '/ChimAcademy/lecture/'+depCode;

		$.ajax({
			type: "get",
			url: url,
			dataType: "json",
			success: function (data) {
				console.log(data);

				let major = $('select[name=majorCodeSel]');
				major.empty();

				let tag = `<option value="">-</option>`;
				data.forEach(e => {
					tag += `<option value="${e.majorCode}">${e.majorName}</option>`;
				});
				major.append(tag);

			}
		});
		
	});
}