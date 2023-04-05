/**
 * 날짜 : 2023/03/28
 * 이름 : 김지홍
 * 내용 : 교수 자바스크립트 함수
 */
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

// 강의계획서 폼 유효성검사 및 출력
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
		
		$('#syllabusForm').submit();
		
		setTimeout(()=>{
			window.close();
		}, 100)
		
		
	});
}

// 세션스토리지 데이터 저장
function setData(data){
	sessionStorage.setItem('data', JSON.stringify(data));
}

// 과목코드에 따라 수강인원(성적) 불러오기
function getScores(){
	$(document).on('click', '.btnStdList', function(){
		let count = $(this).data('lecreq');
		if(!count){
			alert('수강인원이 없습니다.');
			return false;
		}
		let lecCode = $(this).data('leccode');
		
		$.ajax({
			type : 'get',
			url : '/ChimAcademy/professor/credit/'+lecCode,
			dataType : 'json',
			success : function(data){
				console.log(data);
				let tag = '';
				data.forEach(function(ele, index){
					tag += ` <tr>
                           <td>${index+1}</td>
                           <td>${ele.lectureEntity.lecClass}</td>
                           <td>${ele.lectureEntity.depCode}</td>
                           <td>${ele.memberEntity.uid}</td>
                           <td>${ele.memberEntity.name}</td>
                           <td>${ele.midExam}</td>
                           <td>${ele.finalExam}</td>
                           <td>${ele.etc1}</td>
                           <td>${ele.etc2}</td>
                           <td>${ele.etc3}</td>
                           <td>${ele.etc4}</td>
                           <td>${ele.totalScore}/100</td>
                           <td>${ele.grade}</td>
                           <td><button class="btnInputCredit" data-no="${ele.no}">입력</button></td>
                        </tr>`;
					
					
					$('#scoreTbody').empty();
					$('#scoreTbody').append(tag);
				});
			}
		})
		
		
	});
}

function editTest() {
	$(document).on('click', '.btnInputCredit', function() {
		let no = $(this).data('no');

		let url = "/ChimAcademy/professor/score/" + no;
		// 팝업 제목
		let name = "성적입력";
		// 화면 중앙에 팝업 시키기 위한 값 구하기
		let width = 800;
		let height = 600;
		let left = (screen.width / 2) - (width / 2);
		let top = (screen.height / 2) - (height / 2);
		// 팝업 옵션(크기, 위치)
		let option = `width = ${width}, height = ${height}, top = ${top}, left = ${left}`;

		window.open(url, name, option);

	})
}

// 성적 입력 팝업 설정 함수
function inputNumControl() {
	const inputFields = document.querySelectorAll('.inputSc');
	let tot = document.getElementById('totalScore');
	const grade = document.getElementById('sGrade');
	
	// 입력 범위 설정 0 ~ 100
	inputFields.forEach(field => {
		field.setAttribute('min', '0');
		field.setAttribute('max', '100');

		field.addEventListener('input', () => {
			const value = field.value;
			if (value < 0 || value == "") {
				field.value = '0';
			} else if (value > 100) {
				field.value = '100';
			} 
		});

		// 숫자 입력시 총합점수, 등급 계산
		field.addEventListener('input', () => {
			const sum = Array.from(inputFields).reduce((acc, field) => acc + Number(field.value), 0);
			tot.value = sum;
			// Calculate the grade based on the total score and set the value of the sGrade input field
			if (tot.value >= 95) {
			  grade.value = "A+";
			} else if (tot.value >= 90 && tot.value < 95) {
			  grade.value = "A";
			} else if (tot.value >= 85 && tot.value < 90) {
			  grade.value = "B+";
			} else if (tot.value >= 80 && tot.value < 85) {
			  grade.value = "B";
			} else if (tot.value >= 75 && tot.value < 80) {
			  grade.value = "C+";
			} else if (tot.value >= 70 && tot.value < 75) {
			  grade.value = "C";
			} else {
			  grade.value = "F";
			}
			
			const errDiv = document.getElementById("errDiv");
			
			// 100 초과시 오류문구 출력
			if(tot.value > 100){
				errDiv.style.display = "block";
				submitScore(tot.value);
			}else{
				errDiv.style.display = "none";
				submitScore(tot.value);
			}
		});
		

		
	});
	
	$('#btnRegScore').click(function(e) {
		e.preventDefault();
		if (tot.value > 100) {
			alert('총합 100점 이상입니다. 수정해주세요.');
			return false;
		} else {
			$('#scoreForm').submit();
			setTimeout(() => {
				window.close();
			}, 100)
		}
	})
}

// 성적 입력 폼 관리

