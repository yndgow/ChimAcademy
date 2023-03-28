/**
 * 날짜 : 2023/03/28
 * 이름 : 김지홍
 * 내용 : 교수 자바스크립트 함수
 */
$(function(){
    
});
function aside(){
	$('#side > li > ol').hide();

    $('#side > li > a').click(function(){
        $(this).siblings().slideToggle(300);
    });
}
function popupOpen(){
	$('.btn_plan').click(function(){
		// 팝업 오픈
		// 팝업주소
		let url = "/ChimAcademy/professor/class/write";
		// 팝업 제목
		let name = "강의계획서";
		// 화면 중앙에 팝업 시키기 위한 값 구하기
		let width = 1220;
		let height = 760;
		let left = (screen.width/2)-(width/2);
		let top  = (screen.height/2)-(height/2);
		// 팝업 옵션(크기, 위치)
		let option = `width = ${width}, height = ${height}, top = ${top}, left = ${left}`;
		
		// 넘길 데이터 오브젝트 만들기 
		let lecture = {};
		lecture.lecName = $(this).closest('tr').find('td:nth-child(2)').text();
		lecture.lecCode = $(this).closest('tr').find('td:nth-child(3)').text();
		// 세션스토리지에 저장
		setData(lecture);
		// 팝업 열기
		window.open(url, name, option);
	});
}

function submitSyllabus(){
	$('#btnSumitSyllabus').click(function(e){
		e.preventDefault();
		let title = $('#formControlInputTitle').val();
		let content = $('#formControlTextareaContent').val();
		
		if(!title.trim()){
			alert('제목을 입력해주세요.');
			return false;	
		}
		if(!content.trim()){
			alert('내용을 입력해주세요.');
			return false;
		}
		
		
		
	});
}

function setData(data){
	
	sessionStorage.setItem('data', JSON.stringify(data));
}