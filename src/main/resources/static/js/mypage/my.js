/*메뉴바*/
$(function() {
	$('#side > li > ol').hide();

	$('#side > li > a').click(function() {
		$(this).siblings().slideToggle(300);
	});
});

/*휴대폰번호 */
function inputPhoneNumber(obj) {
	var number = obj.value.replace(/[^0-9]/g, "");
	var phone = "";

	if (number.length < 4) {
		return number;
	} else if (number.length < 7) {
		phone += number.substr(0, 3);
		phone += "-";
		phone += number.substr(3);
	} else if (number.length < 11) {
		phone += number.substr(0, 3);
		phone += "-";
		phone += number.substr(3, 3);
		phone += "-";
		phone += number.substr(6);
	} else {
		phone += number.substr(0, 3);
		phone += "-";
		phone += number.substr(3, 4);
		phone += "-";
		phone += number.substr(7);
	}
	obj.value = phone;
}


/*세이브 버튼 클릭시 넘어가도록함 */

$(function() {

	$('#savebtn').click(function() {
		if (!confirm("정말로 수정하시겠습니까?")) {
			return false;
		} else {
			return true;
		}
	})

})

/* 우편번호 api */
function execDaumPostcode() {
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
			if (data.userSelectedType === 'R') {
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					document.getElementById("detailAddress").value = '';
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}

			} else {
				document.getElementById("extraAddress").value = '';
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('postcode').value = data.zonecode;
			document.getElementById("address").value = addr;
			// 커서를 상세주소 필드로 이동한다.
			document.getElementById("detailAddress").focus();
		}
	}).open();
}

/* ::::::::::프로필 사진:::::::::: */
function readURL(input) {
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			document.getElementById('preview').src = e.target.result;
		};
		reader.readAsDataURL(input.files[0]);
	} else {
		document.getElementById('preview').src = "";
	}
}

// 휴대폰번호 유효성 검사 함수
function inputPhoneNumber(obj) {
    var number = obj.value.replace(/[^0-9]/g, "");
    obj.value = number;
    addHyphen(obj);
}

// 하이픈(-)을 자동으로 삽입해주는 함수
function addHyphen(obj) {
    var number = obj.value.replace(/-/g, "");
    var tel = "";

    if (number.length < 4) {
        return number;
    } else if (number.length < 7) {
        tel += number.substr(0, 3);
        tel += "-";
        tel += number.substr(3);
    } else if (number.length < 11) {
        tel += number.substr(0, 3);
        tel += "-";
        tel += number.substr(3, 3);
        tel += "-";
        tel += number.substr(6);
    } else {
        tel += number.substr(0, 3);
        tel += "-";
        tel += number.substr(3, 4);
        tel += "-";
        tel += number.substr(7);
    }

    obj.value = tel;
}

// 이메일 유효성 검사 함수
function isValidEmail(email) {
    var emailRegex = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
    return emailRegex.test(email);
}

// 유효성 검사를 위한 jQuery 코드
$(document).ready(function() {
    // 휴대폰번호 유효성 검사
     $("#box1").blur(function() {
        var inputPhone = $(this).val().replace(/-/gi, "");
        if (inputPhone.length < 10 || inputPhone.length > 11 || inputPhone.charAt(0) !== "0") {
            alert("유효하지 않은 휴대폰번호입니다.");
            document.location.reload(true);
        }else if(!!inputPhone.length < 10 || inputPhone.length > 11 || inputPhone.charAt(0) !== "0"){
			$(this).css('background', '#f5f6f7');
		}
    });

    // 이메일 유효성 검사
      $("#box2").blur(function() {
        var inputEmail = $(this).val();
        if (!isValidEmail(inputEmail)) {
            alert("유효하지 않은 이메일입니다.");
			$(this).css('background', '#ffffff');
            $(this).val("");
        }else if(!!isValidEmail(inputEmail)) {
			$(this).css('background', '#f5f6f7');
		}
    });
});


//모달창 열기
$(document).ready(function(){
  // 모달 열기 버튼 클릭 시
  $(".openModalBtn").click(function(){
    $("#myModal").fadeIn();
  });
  
  // 모달 닫기 버튼 클릭 시
  $(".close").click(function(){
    $("#myModal").fadeOut();
  });
  
  // 모달 외부 클릭 시
  $(window).click(function(event) {
    if (event.target == $("#myModal")[0]) {
      $("#myModal").fadeOut();
    }
  });
});


//강의평가 값 가져오기

$(document).ready(function() {
  $('.openModalBtn').click(function() {
	var tr = $(this).closest("tr");
  	var slecCode = tr.find("input[name=slecCode]").val();
    var pid = tr.find("input[name=spid]").val();
    var lecName = $(this).closest('tr').find('td:nth-child(3)').text();
 	$("#myModal input[name=lecCode]").val(slecCode);
    $("#myModal input[name=pid]").val(pid);
    $('#myModal input[name="lecName"]').val(lecName);
  });
});

