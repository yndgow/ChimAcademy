$(function() {

	$('#btnCommentInsert').click(function() {

		let jsonData = {
			content: $('#content').val(),
			uid: $('input[name=uid]').val(),
			parent: $('input[name=no]').val()
		};

		$.ajax({
			type: 'POST',
			url: '/ChimAcademy/board/P701/comment',
			data: jsonData,
			dataType: 'json',
			success: function(comments) {
				alert('댓글 작성에 성공했습니다.');
				$('#commentArea').empty();
				comments.forEach(function(comment) {
					var html = `
			                <div class="view write">
			                  <h2>${comment.uid}</h2>
			                  <p>${comment.content}</p>
			                  <span>${comment.rdate}</span>
			                  <div class="btn_end">
			              `;
					if (comment.uid == $('input[name=uid]').val()) {
						html += `
					          <button class='btn_small btn_comment'>수정</button>
					          <button class='btn_small btn_comment delete' data-no="${comment.no}">삭제</button>
					        `;
					}
					html += `
					            <button class="btn_small"><i class="fas fa-thumbs-up"> 200</i></button>
					            <button class="btn_small"><i class="fas fa-thumbs-down"> 10</i></button>
					          </div>
					        </div>
					      `;
					$('#commentArea').append(html);
				});
			}
		});


	});
	
	$('#deleteBoard').click(function(){
		if (!confirm("정말로 삭제하시겠습니까?")) {
            return false;
        } else {
            return true;
        }
	})


	$(document).on("click", ".delete", function() {
		alert('삭제');
		let no = $(this).data('no');
		let parent = $('input[name="no"]').val();
		$.ajax({
			type: 'GET',
			url: '/ChimAcademy/board/P701/commentDelete?no=' + no + '&parent=' + parent,
			success: function(comments) {
				alert('성공')
				$('#commentArea').empty();
				console.log(comments);
				comments.forEach(function(comment) {
					var html = `
			                <div class="view write">
			                  <h2>${comment.uid}</h2>
			                  <p>${comment.content}</p>
			                  <span>${comment.rdate}</span>
			                  <div class="btn_end">
				              `;
					if (comment.uid == $('input[name=uid]').val()) {
						html += `
						          <button class='btn_small btn_comment'>수정</button>
						          <button class='btn_small btn_comment delete' data-no="${comment.no}">삭제</button>
						        `;
					}
					html += `
						            <button class="btn_small"><i class="fas fa-thumbs-up"> 200</i></button>
						            <button class="btn_small"><i class="fas fa-thumbs-down"> 10</i></button>
						          </div>
						        </div>
						      `;
					$('#commentArea').append(html);
				});
			}
		});

	})
});


//댓글 수정
$(document).on('click', '.btn_comment', function() {
  var commentBox = $(this).closest('.write');
  var content = commentBox.find('p').text();
  var commentNo = commentBox.find('.delete').data('no');
  
  // Replace the text with an input field
  commentBox.find('p').replaceWith('<input type="text" class="edit_content" value="' + content + '">');
  
  // Replace the 수정 button with a 저장 button
  $(this).replaceWith('<button class="btn_small btn_save" data-no="' + commentNo + '">저장</button>');
});

$(document).on('click', '.btn_save', function() {
  var commentBox = $(this).closest('.write');
  var content = commentBox.find('.edit_content').val();
  var commentNo = $(this).data('no');
  var btn_save = $(this); // 새로운 변수 추가
  
  $.ajax({
    type: 'PUT',
    url: '/ChimAcademy/board/P701/updatecomment',
    data: {
		no:commentNo,
	    content: content
    },
    dataType: 'json',
    success: function(comment) {
      commentBox.find('.edit_content').replaceWith('<p>' + comment.content + '</p>');
      btn_save.replaceWith('<button class="btn_small btn_comment">수정</button>');
    }
  });
});


//글 내용이없을경우
$(document).ready(function() {
	$('#writeBtn').click(function(e) {
		e.preventDefault(); // 기본 동작인 submit 방지
		var title = $('input[name="title"]').val(); // 제목 값 가져오기
		var content = $('textarea[name="content"]').val() // 게시글 값 가져오기
		if (title.trim() == '' || content.trim() == '') { // 제목과 게시글이 비어있는 경우
			alert('제목과 게시글을 입력해주세요.');
			return false; // 폼 제출 방지
		} else {
			$('#commentForm').submit(); // 폼 제출
		}
	});
});


