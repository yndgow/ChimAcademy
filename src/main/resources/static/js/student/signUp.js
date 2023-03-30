/**
 * 
 */
$(function(){
    
});

// 학과가 변할 때 전공 찾기
function selectMajors(){
	$('select[name=depCode]').change(function (e) { 
		e.preventDefault();
		// 학과 코드
		let depCode = $(this).val();

		let url = '/ChimAcademy/lecture/'+depCode;

		$.ajax({
			type: "get",
			url: url,
			dataType: "json",
			success: function (data) {
				//console.log(data);

				let major = $('select[name=majorCodeSel]');
				major.empty();

				let tag = `<option value="">-</option>`;
				data.forEach(e => {	
					tag += `<option value="${e.majorCode}">${e.majorName}</option>`;
				});
				major.append(tag);

			}
		});
		
	});
}
// 검색결과에서 강의계획서 보기를 눌렀을때
function viewSyllabus(){
	$(document).on('click', '.btnSyllabus', function(){
		let tr = $(this).closest('tr');
		let lecCode = tr.children('td:eq(4)').text();
		
		// 팝업 오픈
		// 팝업주소
		let url = "/ChimAcademy/student/class/view?lecCode="+lecCode;
		// 팝업 제목
		let name = "강의계획서";
		// 화면 중앙에 팝업 시키기 위한 값 구하기
		let width = 1220;
		let height = 760;
		let left = (screen.width/2)-(width/2);
		let top  = (screen.height/2)-(height/2);
		// 팝업 옵션(크기, 위치)
		let option = `width = ${width}, height = ${height}, top = ${top}, left = ${left}`;
		
		let lecture = {};
		lecture.lecName = $(this).closest('tr').find('td:nth-child(6)').text();
		setData(lecture);
		
		window.open(url, name, option);
	})
}




// 검색결과에서 수강신청을 눌렀을때 수강신청하기
function regLecSuang(){
	$(document).on('click', '.btnLecReg', function(){
		let tr = $(this).closest('tr');
		let lecCode = tr.children('td:eq(4)').text();
		let uid = $('#uid').text();
		// 버튼을 눌렀을때 수강 테이블에 uid, lecCode 추가하기
		// 추가로 과목 테이블에 신청인원 1증가 시키기
		let url = '/ChimAcademy/student/class/signup';
		let jsonData = {
			lectureEntity:lecCode, memberEntity:uid
		};
		$.ajax({
			type: "post",
			url: url,
			data: jsonData,
			dataType: "json",
			success: function (data) {
				//console.log(data);
				if(data.result == 0){
					alert('이미 신청하였거나 수강인원이 꽉 찼습니다.');
				}else if(data.result == 1){
					alert('수강신청에 성공하였습니다.');
					location.reload();
				}else if(data.result == 2){
					alert('신청할 수 있는 학점은 최대 20학점입니다.');
				}else if(data.result == 3){
					alert('이미 신청하였거나 신청한 다른 강의와 시간이 겹칩니다.');
				}else if(data.result ==4){
					alert('날짜가 정해지지 않은 강의는 신청하실 수 없습니다.');
				}
			}
		});
		
		 
	})
}

// 수강 신청 내역 삭제
function delSugang(){
	$('.btnDelSugang').click(function(){
		let tr = $(this).closest('tr');
		let lecCode = tr.children('td:eq(1)').text();
		
		$.ajax({
			type : 'delete',
			url : '/ChimAcademy/student/class/signup/'+lecCode,
			dataType : 'json',
			success : function(data){
				if(data.result > 0){
					alert('해당 과목의 수강을 취소하셨습니다.');
					location.reload();
				}else{
					alert('삭제에 실패했습니다.');
				}
				
			}
		})
	});
}


function setData(data){
	
	sessionStorage.setItem('data', JSON.stringify(data));
}
