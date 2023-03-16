$(document).ready(function() {
	// 삭제 이벤트 버튼
	$("#delbtn").click(function() {

		let no = $('input[name=no]').val();
		$.ajax({
			type: "POST",
			url: "/ChimAcademy/board/A101/delete?no=" + no,
			success: function(data) {
				if (data.result > 0) {
					alert('삭제 완료');
					location.href = "/ChimAcademy/board/A101/list";
				} else {
					alert('삭제 실패');
				}
			}
		});
	});
	// 수정 이벤트 버튼
	$("#regbtn").click(function() {
		let no = $('input[name=no]').val();
		let pg = $('input[name=pg]').val();
		
		location.href = "/ChimAcademy/board/A101/modify?no=" + no + "&pg=" + pg;
	});



});

