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



/* 정규식 */
// 이름 : 2자리 이상 15자리 이하 한글, 영문
const regexName = /^[가-힣|a-z|A-Z|]{2,15}$/;
// 생년월일 6자리
const regexBirth = /([0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[1,2][0-9]|3[0,1]))/;
// 휴대폰 번호 12, 13자리
const regexHp =/^(01[016789]{1})-[0-9]{3,4}-[0-9]{4}$/;
// 비밀번호 8자이상 숫자, 소문자, 대문자, 특수문자 하나씩 포함
const regexPw = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
const regexEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

/* 아이디찾기 */
let nameVal = false;
let birthVal = false;
let hpVal = false;

/* 가입하기 */
let pwVal = false;
let emailVal = false;
let pwEqual = false;
let addr1Val = false;
let emailCodeVal = false;

// 이름 유효성 검사
function nameCheck(){
	$('input[name=name]').on('keyup focusout',function(){
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
	$('input[name=birth]').on('keyup focusout',function(){
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
	$('input[name=hp]').on('keyup focusout',function(){
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

// 학번(아이디) 찾기 
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
					if(!data){
						alert('존재하지 않는 사용자입니다.');
						return false;
					} 
					alert('학번(아이디)는 '+ data.uid + '입니다.');
					
					sessionStorage.setItem('data', JSON.stringify(data));
					window.location.href = '/ChimAcademy/member/join';
					
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

// 세션스토리지를 활용하여 데이터 넘기기
function memSessStorage(){
	const dataStr = sessionStorage.getItem('data');
	const data = JSON.parse(dataStr);
			
	if(data.name) $('input[name=name]').val(data.name);
	if(data.uid) $('input[name=uid]').val(data.uid);
	if(data.hp) $('input[name=hp]').val(data.hp);
	if(data.birth) $('input[name=birth]').val(data.birth);
		
	sessionStorage.clear();
		
}

// 비밀번호 유효성 검사
function pwCheck(){
	$('input[name=pass]').on('keyup focusout', function(){
		let pw = $(this).val();
		
		pwVal = regexPw.test(pw);
		
		if(pwVal == false){
			$('#msgPw1').show();
			$(this).css('background', '#ffffff');
		}else if(pwVal == true){
			$('#msgPw1').hide();
			$(this).css('background', '#dfe3ee');
		}

	});
}

// 비밀번호 일치 검사
function pwEqualCheck(){
	$('input[name=pass2]').on('keyup focusout',function(){
		let pw = $('input[name=pass]').val();
		let pw2 = $(this).val();
		
		pw === pw2 ? pwEqual = true : pwEqual = false; 	
		
		if(pwEqual == false){
			$('#msgPw2').show();
			$(this).css('background', '#ffffff');
		}else if(pwEqual == true && pw2 != ""){
			$('#msgPw2').hide();
			$(this).css('background', '#dfe3ee');
		}
	});
}

// 이메일 유효성 검사
function emailCheck(){
	$('input[name=email]').on('keyup focusout',function(){
		let email = $(this).val();
		
		emailVal = regexEmail.test(email);
		if(emailVal == false){
			$('#msgEmail').show();
			$(this).css('background', '#ffffff');
		}else if(emailVal == true){
			$('#msgEmail').hide();
			$(this).css('background', '#dfe3ee');
		}
	});
}

let emailCode;

// 인증 이메일 발송
function emailAuth(){
	$('#btnEmailAuth').click(function(){
		
		let jsonData = {email : $('input[name=email]').val()};
		
		$.ajax({
			type: "GET",
			url: "/ChimAcademy/member/email",
			data: jsonData,
			dataType: "json",
			async: false,
			success: function (data) {
				if(data > 0){
					alert('이미 존재하는 이메일입니다.');
					emailVal = false;
					$('input[name=email]').css('background', '#ffffff');
					return false;		
				}
			}
		});
		
		if(emailVal){
			emailCodeVal = false;
			$('#trEmailAuth').show();
			alert('인증번호가 발송되었습니다. 이메일의 인증번호를 확인해주세요.');
			$(this).attr("disabled", true);
			$(this).css('background', '#8b9dc3');	
			$('footer').css('position', 'static');
			setTimeout(function() {
				$('#btnEmailAuth').attr("disabled", false);
				$('#btnEmailAuth').css('background', '#3b5998');
			},5000);
			
			let jsonData = {email :$('input[name=email]').val()};
			
			$.ajax({
				type: "GET",
				url: "/ChimAcademy/member/emailCode",
				data: jsonData,
				dataType: "json",
				success: function (data) {
					emailCode = data;
				}
			});
		}else{
			alert('입력하신 이메일을 확인해주세요.');
		}
		
	});	
}

// 인증 이메일 코드 확인
function emailCodeConfirm(){
	$('#btnCodeConfirm').click(function(){
		let inputCode = $('input[name=emailCode]').val();
		if(inputCode == emailCode){
			alert('인증번호가 확인되었습니다.');
			$('footer').css('position', 'absolute');
			$('#trEmailAuth').hide();
			$('input[name=email]').attr('readonly', true);
			emailCodeVal = true;	
		}else{
			alert('인증번호가 일치하지 않습니다.');
		}
		
	});
}

// 다음 주소 api
function execDaumPostcode() {
	$('#btnZip').click(function(){
		new daum.Postcode({
	        oncomplete: function(data) {
	            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
	            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	            var addr = ''; // 주소 변수
	            var extraAddr = ''; // 참고항목 변수
	
	            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                addr = data.roadAddress;
	            } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                addr = data.jibunAddress;
	            }
	
	            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	            if(data.userSelectedType === 'R'){
	                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                    extraAddr += data.bname;
	                }
	                // 건물명이 있고, 공동주택일 경우 추가한다.
	                if(data.buildingName !== '' && data.apartment === 'Y'){
	                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                }
	                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                if(extraAddr !== ''){
	                    extraAddr = '(' + extraAddr + ')';
	                }
	                // 조합된 참고항목을 해당 필드에 넣는다.
	                document.getElementById("addr2").value = extraAddr;
	            
	            } else {
	                document.getElementById("addr2").value = '';
	            }
	
	            // 우편번호와 주소 정보를 해당 필드에 넣는다.
	            document.getElementById('zip').value = data.zonecode;
	            document.getElementById("addr1").value = addr;
	            // 커서를 상세주소 필드로 이동한다.
	            document.getElementById("addr2").focus();
	            $('input[name=zip]').css('background', '#dfe3ee');
	            $('input[name=addr1]').css('background', '#dfe3ee');
	            $('input[name=addr2]').css('background', '#dfe3ee');
	            addr1Val = true;
	        }
	    }).open();
	})
}

// 가입하기
function memberJoin(){
	$('#memberJoinBtn').click(function(){
		let addr2Val = $('input[name=addr2]').val();
		
		if(pwVal && emailVal && pwEqual && addr1Val && addr2Val && emailCodeVal){
			$('#memberJoinForm').submit();
			alert('성공적으로 가입되었습니다. 로그인해주세요.')
			let data = {uid : $('input[name=uid]').val()};
			
			sessionStorage.setItem('data', JSON.stringify(data));
		}else{
			if(!pwVal) $('input[name=pass]').css('background', 'rgb(255 219 234)');
			if(!emailVal) $('input[name=email]').css('background', 'rgb(255 219 234)');
			if(!pwEqual) $('input[name=pass2]').css('background', 'rgb(255 219 234)');
			if(!addr1Val) $('input[name=addr1]').css('background', 'rgb(255 219 234)');
			if(!addr1Val) $('input[name=zip]').css('background', 'rgb(255 219 234)');
			if(!addr2Val) $('input[name=addr2]').css('background', 'rgb(255 219 234)');
			alert('입력하신 가입 정보를 확인해주세요');
		}
	});
}
