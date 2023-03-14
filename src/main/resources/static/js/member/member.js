/**
 * 날짜 : 2023/03/13
 * 이름 : 김지홍
 * 내용 : 로그인, 가입하기 페이지 함수 모음
 */
$(function(){
	
});

// 이미지 슬라이드
function loginSlide(){
	$(".slider").bxSlider( {
    	speed: 500,        // 이동 속도를 설정
    	auto: true,        // 자동 실행 여부
        autoHover: true,   // 마우스 호버시 정지 여부
        controls: false    // 이전 다음 버튼 노출 여부
    });
}
// 메시지 호출
function alertMsg(msg){
	if(msg == 100){
		alert('비밀번호가 틀리거나, 존재하지 않는 사용자입니다.');
	}else if(msg == 200){
		alert('안전하게 로그아웃 되었습니다.');
	}
}

let nameVal = false;

// 이름 유효성 검사
function nameCheck(){
	
	//console.log(name);
	$('input[name=name]').keyup(function(e){
		let name = $(this).val();
		console.log(name);
	});

}