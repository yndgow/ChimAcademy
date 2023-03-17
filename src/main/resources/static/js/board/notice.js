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

// 수정 페이지로 이동
function btnModify(){
	$('#btnNoticeModify').click(function(){
		let no = $(this).data('no');
		location.href = '/ChimAcademy/notice/modify?no='+no;
	});
}


