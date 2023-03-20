/* 국문학과 게시판 삭제, 수정 */
$(document).ready(function() {
	// 삭제 이벤트 버튼
	$("#delbtn").click(function() {
		let no = $('input[name=no]').val();
		let con = confirm('삭제하시겠습니까?');
		if (!con) {
			return false;
		}

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

	/* 국문학과 공백 글 작성 금지 */
	// 목록 이동
	$(function() {
		$('#btnList').click(function() {
			let btnList = confirm('게시글 목록으로 이동하시겠습니까?');
			if (btnList) {
				location.href = '/ChimAcademy/board/A101/list'
			} else if (!btnList) {
			}
		})

		// 글 작성 금지
		$('#btnWrite').click(function() {
			let title = $('input[name=title]').val();
			let content = $('textarea[name=content]').val();

			if (title.trim() == "") {
				alert('제목을 작성해주세요.');
				return false;
			} else if (content.trim() == "") {
				alert('내용을 작성해주세요.');
				return false;
			}
		})
	});
});

/* 국문학과 댓글 */
// 내용이 있는지 체크
function checkEmpty(content) {
	if (content.trim()) {
		return true;
	} else {
		return false;
	}
}

// 댓글 작성
function commentadd() {
	$('#commentadd').click(() => {
		// 작성한 댓글 내용 가져오기
		let comment = $('textarea[name=comment]').val();

		// 내용이 비었는지 체크 비어있으면 return false로 추가 진행 막기
		let check = checkEmpty(comment);
		if (!check) {
			alert('댓글 내용을 작성해주세요.');
			return false;
		}

		// 보낼 객체 선언 (로그인한 사용자의  uid, 댓글내용, 부모글번호)
		let jsonData = {
			uid: $('input[name=uid]').val(),
			content: comment,
			parent: $('input[name=parent]').val()
		};
		console.log(jsonData);

		// ajax로 보내기
		$.ajax({
			type: "post",
			url: "/ChimAcademy/board/A101/comment",
			data: jsonData,
			dataType: "json",
			success: function(data) {
				// 보낸 data가 있으면 null이 아니므로 댓글등록 성공
				if (data != null) {
					alert('댓글이 등록되었습니다.')

					console.log('data:' + data);
					let tag = `<div class="view write" data-no="${data.no}">
									<h2>${data.name}</h2>
									<p class="commentTextArea">${data.content}</p>
									<div class="btn_div">
										<span>${data.rdate.substring(0, 10)}</span>
										<div>
											<button class="btn_small btn-gradient cyan mini btnCommentModify" data-no="${data.no}">수정</button>
											<button class="btn_small btn-gradient red mini btnCommentDelete" data-no="${data.no}">삭제</button>
											<button class="btn_small btnGoodComment"><i class="fas fa-thumbs-up"> 0</i></button>
											<button class="btn_small btnBadComment"><i class="fas fa-thumbs-down"> 0</i></button>
										</div>
									</div>
								</div>`;

					// 댓글등록이 성공하면 입력한 댓글만 기존 댓글 영역에 추가하기
					$('#commentsDiv').append(tag);
					$('textarea[name=comment]').val('');

					//  댓글수 늘이기
					let countComment = $('#countComments').text();
					let decreCount = Number(countComment) + 1;
					$('#countComments').text(decreCount);
				} else {
					alert('댓글이 등록 실패하였습니다.')
				}
			}
		});

	});
}

// 댓글삭제
function deleteComment() {
	$(document).on('click', '.btnCommentDelete', function() {
		// 삭제 확인
		let con = confirm('삭제하시겠습니까?');
		// 확인 값으로 false면 진행막기
		if (!con) return false;
		// 댓글의 글번호 가져오기
		let no = $(this).data('no');
		// ajax 통신
		$.ajax({
			type: 'delete',
			url: "/ChimAcademy/board/A101/comment/delete?no=" + no,
			dataType: 'json',
			success: function(data) {
				// 1이상이면 성공
				if (data.result > 0) {
					alert('삭제하였습니다.');
					// 댓글 html 지우기
					let commentDiv = $('.view.write[data-no=' + no + ']');
					commentDiv.remove();

					//  댓글수 줄이기
					let countComment = $('#countComments').text();
					let decreCount = Number(countComment) - 1;
					$('#countComments').text(decreCount);
				}


			}
		});
	});
}

// 댓글 수정 
function modifyComment() {
	$(document).on('click', '.btnCommentModify', function() {
		let no = $(this).data('no');
		// p 태그 textarea 로 바꾸기
		let commentTextArea = $('.view.write[data-no=' + no + '] .commentTextArea');
		let commentContent = commentTextArea.text().trim();
		let textarea = $('<textarea class="commentEditTextArea"></textarea>').val(commentContent);
		commentTextArea.replaceWith(textarea);
		// 버튼 숨기기
		$('.btnCommentModify').hide();
		$('.btnCommentDelete').hide();
		$('.btnGoodComment').hide();
		$('.btnBadComment').hide();
		// 버튼추가
		let btnDiv = $('.view.write[data-no=' + no + '] .btn_div > div');
		let saveBtn = $('<button class="btn_small btn-gradient green mini btnCommentSave" data-no="' + no + '">수정완료</button>');
		let cancelBtn = $('<button class="btn_small btn-gradient red mini btnCommentCancel" data-no="' + no + '">취소</button>');
		btnDiv.append(saveBtn);
		btnDiv.append(cancelBtn);
	});

	$(document).on('click', '.btnCommentSave', function() {
		let no = $(this).data('no');
		var commentEditTextArea = $('.view.write[data-no=' + no + '] .commentEditTextArea');
		var content = commentEditTextArea.val();

		let jsonData = {
			no: no,
			content: content
		};

		console.log(jsonData);

		$.ajax({
			type: 'post',
			url: '/ChimAcademy/board/A101/comment/modify',
			data: jsonData,
			dataType: 'json',
			success: function(data) {
				if (data.result > 0) {
					alert('댓글을 수정하였습니다.');
					let commentTextArea = $('<p class="commentTextArea"></p>').text(content);
					commentEditTextArea.replaceWith(commentTextArea);
					$('.view.write[data-no=' + no + '] .btnCommentSave').remove();
					$('.view.write[data-no=' + no + '] .btnCommentCancel').remove();

					$('.btnCommentModify').show();
					$('.btnCommentDelete').show();
					$('.btnGoodComment').show();
					$('.btnBadComment').show();

				} else {
					alert('댓글 수정을 실패하였습니다.');
				}
			}
		});
	})
	// 댓글 취소 이벤트 버튼
	$(document).on("click", ".btnCommentCancel", function(){
		location.reload();
	});
}

