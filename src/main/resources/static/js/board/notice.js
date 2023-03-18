/**
 * 날짜 : 2023/03/17
 * 이름 : 김지홍
 * 내용 : 공지사항 자바스크립 파일
 * 
 */
$(function(){
	asideDropDown();
	backList();
	savePgStorage();
	btnDelete();
});

// aside 드롭다운 
function asideDropDown(){
	$('#side > li > ol').hide();

    $('#side > li > a').click(function(){
        $(this).siblings().slideToggle(300);
    });	
}

// 페이지 번호 세션스토리지 저장
function savePgStorage(){
	$('.page-link').click(function(e){
		e.preventDefault();
		let pg = parseInt($(this).data('page')) || 1;
		sessionStorage.setItem('pg', pg);
		window.location.href = $(this).attr('href');	
	})
}

// 페이지 목록으로 이동
function backList(){
	$('#btnNoticeList').click(()=>{
		
		//history.back();
		let pg = sessionStorage.getItem('pg') || 1;
		//console.log(pg);
		window.location.href = '/ChimAcademy/notice?pg='+pg;
	});
}

// 내용이 있는지 체크
function checkEmpty(content){
		if(content.trim()){
			return true;
		}else{
			return false;
		}
}

function noticeWrite(){
	$('#btnNoticeWrite').click(function(e){
		e.preventDefault();
		
		let title = $('input[name=title]').val();
		let content = $('textarea[name=content]').val();
		let cond1 = checkEmpty(title);
		let cond2 = checkEmpty(content);
		if(!cond1){
			alert('제목을 작성해주세요.');
			return false;
		} else if(!cond2){
			alert('내용을 작성해주세요.');
		} 
		
		if(cond1 && cond2){
			$('#formNoticeWrite').submit();
		}
	})
}




// 수정 페이지로 이동
function btnModify(){
	$('#btnNoticeModify').click(function(){
		let no = $(this).data('no');
		location.href = '/ChimAcademy/notice/modify?no='+no;
	});
}

// 공지사항 삭제
function btnDelete(){
	$('#btnNoticeDelete').click(function(){
		let con = confirm('삭제하시겠습니까?');
		let no = $(this).data('no');
		
		if(con){
			$.ajax({
				type: "DELETE",
				url: "/ChimAcademy/notice/delete/" + no,
				dataType: "json",
				success: function (data) {
					if(data.result > 0){
						alert('삭제하였습니다.');
						let pg = sessionStorage.getItem('pg') || 1;
						location.href = '/ChimAcademy/notice?pg='+pg;
					}else{
						alert('삭제 실패하였습니다.');
					}
				}
			});
		}
	});
}

// 댓글 작성
function insertRep(){
	$('#btnInsertComment').click(()=>{
		// 작성한 댓글 내용 가져오기
		let comment = $('textarea[name=comment]').val();

		// 내용이 비었는지 체크 비어있으면 return false로 추가 진행 막기
		let check = checkEmpty(comment);
		if(!check){
			alert('댓글 내용을 작성해주세요.');
			return false;
		}
		
		// 보낼 객체 선언 (로그인한 사용자의  uid, 댓글내용, 부모글번호)
		let jsonData = {
			uid : $('input[name=uid]').val(),
			content : comment,
			parent : $('input[name=parent]').val()
		};
		console.log(jsonData);

		// ajax로 보내기
		$.ajax({
			type: "post",
			url: "/ChimAcademy/notice/comment/insert",
			data: jsonData,
			dataType: "json",
			success: function (data) {
				// 보낸 data가 있으면 null이 아니므로 댓글등록 성공
				if(data != null){
					alert('댓글이 등록되었습니다.')	
					
					console.log(data);
					let tag = `<div class="view write">
									<h2>${data.name}</h2>
									<textarea name="">${data.content}</textarea>
									<div class="btn_div">
										<span>${data.rdate.substring(0, 10)}</span>
										<div>
											<button class="btn_small btn_comment">답글보기</button>
											<button class="btn_small"><i class="fas fa-thumbs-up"> 0</i></button>
											<button class="btn_small"><i class="fas fa-thumbs-down"> 0</i></button>
										</div>
									</div>
								</div>`;

					// 댓글등록이 성공하면 입력한 댓글만 기존 댓글 영역에 추가하기
					$('#commentsDiv').append(tag);
					$('textarea[name=comment]').val('');
				}else{
					alert('댓글이 등록 실패하였습니다.')	
				}
				
				
			}
		});
		
	});
}


