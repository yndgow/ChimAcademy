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
let birthVal = false;
let hpVal = false;

/* 정규식 */
// 이름 : 2자리 이상 15자리 이하 한글, 영문
const regexName = /^[가-힣|a-z|A-Z|]{2,15}$/;
// 생년월일 6자리
const regexBirth = /([0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[1,2][0-9]|3[0,1]))/;
// 휴대폰 번호 12, 13자리
const regexHp =/^(01[016789]{1})-[0-9]{3,4}-[0-9]{4}$/;

// 이름 유효성 검사
function nameCheck(){
	$('input[name=name]').keyup(function(e){
		let name = $(this).val();
		
		nameVal = regexName.test(name);
		if(nameVal == false){
			$('#msgName').show();
			$(this).css('background', '#ffffff');
		}else if(nameVal == true){
			$('#msgName').hide();
			$(this).css('background', '#dfe3ee');
		}
	});
}

// 생년월일 유효성 검사
function birthCheck(){
	$('input[name=birth]').keyup(function(e){
		let birth = $(this).val();
		
		birthVal = regexBirth.test(birth);
		if(birthVal == false){
			$('#msgBirth').show();
			$(this).css('background', '#ffffff');
		}else if(birthVal == true){
			$('#msgBirth').hide();
			$(this).css('background', '#dfe3ee');
		}
	});
}

// 휴대폰 유효성 검사
function hpCheck(){
	$('input[name=hp]').keyup(function(e){
		let hp = $(this).val();
		
		hpVal = regexHp.test(hp);
		if(hpVal == false){
			$('#msgHp').show();
			$(this).css('background', '#ffffff');
		}else if(hpVal == true){
			$('#msgHp').hide();
			$(this).css('background', '#dfe3ee');
		}
	});
}

function searchUid(){
	$('#btnSearchUid').click(function(){
		if(nameVal && birthVal && hpVal){
			let jsonData ={
				name : $('input[name=name]').val(),
				birth : $('input[name=birth]').val(),
				hp : $('input[name=hp]').val()
			};

			$.ajax({
				type: "GET",
				url: "/ChimAcademy/member/search",
				data: jsonData,
				dataType: "json",
				success: function (data) {
					alert('학번(아이디)는 '+ data.uid + '입니다.');
				},
				error : function(request, status, error ) {   // 오류가 발생했을 때 호출된다. 
					if(request.status == 400){
						alert('존재하지 않는 사용자입니다.');
					}
				
				
				}
			});
		}else{
			alert('항목을 바르게 입력해주세요.')
			if(!nameVal){} $('input[name=name]').css('background', 'rgb(255 219 234)');
			if(!birthVal) $('input[name=birth]').css('background', 'rgb(255 219 234)');
			if(!hpVal) $('input[name=hp]').css('background', 'rgb(255 219 234)');
		}
	})
}





