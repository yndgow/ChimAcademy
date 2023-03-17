/* 국문학과 게시판 삭제, 수정 */
$(document).ready(function() {
	// 삭제 이벤트 버튼
	$("#delbtn").click(function() {
	
	});

	// 수정 이벤트 버튼
	$("#regbtn").click(function() {
		let no = $('input[name=no]').val();
		let pg = $('input[name=pg]').val();	

		location.href = "/ChimAcademy/board/A101/modify?no=" + no + "&pg=" + pg;
	});

/* 국문학과 게시판 댓글 */
	$("#commentadd").click(function() {
		let jsonData ={
			content: $('textarea[name=comment]').val(),
			uid: $('input[name=uid]').val(),
			parent: $('input[name=no]').val()
		};

		$.ajax({
			type: "POST",
			url:"/ChimAcademy/board/A101/comment" ,
			data: jsonData,
			datatype:"json",
			success: function(comments) {
				alert('댓글이 작성되었습니다!')
				console.log(comments);
				$("#addcomments").empty();

				let tag = '';
				comments.forEach(function(comment){
					tag += `<div">
							<h2>${comment.name}</h2>
							<p>${comment.content}</p>
							<div class="btn_div">
								<span>${comment.rdate}</span>
								<div>
									<button class="btn_small btn_comment">답글보기</button>
									<button class="btn_small">
										<i class="fas fa-thumbs-up"> 200</i>
									</button>
									<button class="btn_small">
										<i class="fas fa-thumbs-down"> 10</i>
									</button>
								</div>
							</div>
						</div>`;
				})

				$("#addcomments").append(tag);

				// textarea 비우기
				$('textarea[name=comment]').val('');


			}
		});

	});
});