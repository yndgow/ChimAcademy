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
				console.log(data);

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
				if(data.result > 0){
					alert('수강신청에 성공하였습니다.')
					location.reload();
				}else{
					alert('이미 신청하였거나 수강인원이 꽉 찼습니다.')
				}
			}
		});
		
		 
	})
}