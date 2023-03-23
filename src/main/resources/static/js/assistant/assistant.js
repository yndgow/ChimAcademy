/**
 * 날짜 : 2023/03/15
 * 이름 : 김지홍
 * 내용 : 조교 페이지 자바스크립트 함수 모음
 */
$(function(){
    $('#side > li > ol').hide();

    $('#side > li > a').click(function(){
        $(this).siblings().slideToggle(300);
    });
});

// 인원 추가 페이지 행 추가
function addRow(){
	$('#btnAddRow').click(function(){
		let lastRow = $('#insertMemTable tr:last');
		let lastCell = lastRow.children('td:first');
		let currentValue = parseInt(lastCell.text());
		let nextValue = currentValue+1; 
		if(nextValue > 15){
			alert('최대 15개까지만 추가가 가능합니다.');
			return false;
		} 
		let tag = `<tr>
					<td>`+nextValue+`</td>
					<td><input type="text" name="uid"></td>
					<td><input type="text" name="name"></td>
					<td><input type="text" name="birth"></td>
					<td><input type="text" name="hp"></td>
					<td><select name="level">
							<option value="1">학생</option>
							<option value="2">조교</option>
							<option value="3">교수</option>
					</select></td>
				</tr>`;
				
		$('#insertMemTable').append(tag);
				
	});
}

// 인원 추가 테이블 내용을 읽어서 json으로 전송 
function insertMembers(){
	$('#btnMemInsert').click(function(){
		let data = [];
		
		$('#insertMemTable tbody tr').each(function(){
			let row = {};
			row.uid = $(this).find('input[name="uid"]').val();
			if(!row.uid) return;
			row.name = $(this).find('input[name="name"]').val();
		    row.birth = $(this).find('input[name="birth"]').val();
    		row.hp = $(this).find('input[name="hp"]').val();
    		row.level = $(this).find('select[name="level"]').val();

    		let depCode = $('input[name=depCode]').val();
    		let departmentEntity = { depCode: depCode };
    		row.departmentEntity = departmentEntity;

    		data.push(row);
		});
		if(data.length == 0){
			alert('입력하신 정보가 없습니다.');
			return false;
		} 
		$.ajax({
			type: "POST",
			url: "/ChimAcademy/assistant/insert",
			data: JSON.stringify(data),
			contentType: "application/json",
			dataType: "json",
			success: function(data){
				let msg = '';
				data.forEach(function(ele){
					msg += '아이디 : '		+ ele.uid + ", ";
					msg += '이름 : '		+ ele.name + ", ";
					msg += '생년월일 : '	+ ele.birth + "\n";
					msg += '휴대폰번호 : '	+ ele.hp+ ", ";
					switch(ele.level){
						case 1 : msg += '등급 : ' + '학생'  + '\n'; break;
						case 2 : msg += '등급 : ' + '조교'  + '\n'; break;
						case 3 : msg += '등급 : ' + '교수'  + '\n'; break;
					}
				})
				alert(data.length+'건의 데이터가 추가(변경)되었습니다.\n'+msg);
				location.reload();
			},
			error: function(data){
				alert('아이디 또는 휴대폰 번호가 이미 존재합니다.');
			}
		})
	});
}
// 강의 찾기
function searchBtn(){
	$('#btnSearchLec').click(function(){
		let lecGubun = $('select[name=lecGubun]').val();
		let searchData = $('input[name=searchData]').val();
		let depCode = $('select[name=depCode]').val();
		let majorCode = $('select[name=majorCodeSel]').val();
		let lecClass = $('select[name=lecClass]').val();
		
		let jsonData = {};
		if(lecGubun) jsonData.lecGubun = lecGubun;
		if(majorCode) jsonData.majorCode = majorCode;
		if(lecClass) jsonData.lecClass = lecClass;
		if(searchData) jsonData.searchData = searchData;
		if(depCode) jsonData.depCode = depCode;
		
		$.ajax({
			type: "GET",
			url: "/ChimAcademy/assistant/lecture/search",
			data: jsonData,
			dataType: 'json',
			success: function(data){
				alert('정보를 검색했습니다.')
				$('#searchResult').text(data.length);
				$('#userListTable tbody').empty();
				let tag = '';
				let reqUrl = location.pathname;
				data.forEach(function(e, i){
					
					/* 전공 지정 */
					if(!e.majorCode) e.majorCode = "-";						
										
					/* 구분 지정 */
					switch(e.lecGubun){
						case 0: e.lecGubun = "-"; break;
						case 1: e.lecGubun = "전필"; break;
						case 2: e.lecGubun = "전선"; break;
						case 3: e.lecGubun = "교양"; break;
					}
					
					/* 교수 지정 */
					if(!e.name) e.name = "-";
					
					/* 시간 지정*/
					let time='';
					if(e.beginTime){
						for(let j = e.beginTime; j <= e.endTime; j++){
							time += j;
						}	
					}
					if(time == '1'){
						time = '';
					}
					/* 날짜 지정 */
					if(!e.lecDay) e.lecDay = "";
					
					/* 장소 지정 */
					if(!e.lecLoc) e.lecLoc = "";

				
					
					let btnTd = '';
					switch(reqUrl){
						case '/ChimAcademy/student/class/signup':
						btnTd = `<td>${e.lecRequest}</td>
								<td>
		                            <button class="btnSugangTb btnSyllabus">보기</button>
	                            </td>
	                            <td>
		                            <button class="btnSugangTb btnLecReg">신청</button>
		                        </td>`;
                        break;
                        
                        case '/ChimAcademy/assistant/lecuture': 
                  		btnTd = `<td>
	                            <button class="btnResultTable btnLecMod">수정</button>
	                            <button class="btnResultTable btnLecDel">삭제</button>
	                        	</td>`;
					}

					/* 태그 붙여넣기 */
                    tag += `<tr>
                        <td>${i+1}</td>
                        <td>${e.depCode}</td>
                        <td>${e.majorCode}</td>
                        <td>${e.lecClass}</td>
                        <td>${e.lecCode}</td>
                        <td>${e.lecName}</td>
                        <td>${e.lecGubun}</td>
                        <td>${e.credit}</td>
                        <td>${e.name}</td>
                        <td>${e.lecDay}${time}&nbsp;${e.lecLoc}</td>
                        <td>${e.lecLimit}</td>
      					${btnTd}
                    </tr>`
				})
				if(data.length == 0){
					let element = document.createElement('tr');
					if(reqUrl == '/ChimAcademy/student/class/signup'){
						element.innerHTML = '<td colspan="14">검색결과가 없습니다.</td>';
					}else{
						element.innerHTML = '<td colspan="12">검색결과가 없습니다.</td>';	
					}
					let tbody = document.getElementById('resultTbody');
					tbody.appendChild(element);
				}else{
					$('#userListTable tbody').append(tag);	
				}
				
			}
		});
		
	});
	

}

function lecRegFormShow(){
	$('#btnRegFormShow').click(function(){
		$('.lecRegister').toggle();
	});
}


// 강의 등록 체크
function lecRegFormSubmit(){
	$('.btnLecRegister').click(function(){
		let con = confirm('등록하시겠습니까?')
		if(!con){
			return false;
		}
		$('.lecRegForm').submit();
	})
}

// 강의 수정
function lecModFormShow(){
	$(document).on('click','.btnLecMod',function(){
		$('.lectureMod').hide();

		let tr = $(this).closest('tr');
		let lecCode = tr.children('td:eq(4)').text();

		let url = '/ChimAcademy/assistant/lecture/'+lecCode;
		$.getJSON(url, 
			function (data) {
			   	let lectureEntity = data.lectureEntity ? data.lectureEntity : data;
			
			    $('.lecModForm input[name=lecCode]').val(lectureEntity.lecCode);
			    $('.lecModForm input[name=lecName]').val(lectureEntity.lecName);
			    $('.lecModForm select[name=lecClass]').val(lectureEntity.lecClass);
			    $('.lecModForm select[name=lecGubun]').val(lectureEntity.lecGubun);
			    $('.lecModForm input[name=credit]').val(lectureEntity.credit);
			    
			    if (data.memberEntity) {
			        $('.lecModForm select[name=memberEntity]').val(data.memberEntity.uid);
			    }else{
					$('.lecModForm select[name=memberEntity]').val('');
				}
			    
			    $('.lecModForm input[name=lecLoc]').val(lectureEntity.lecLoc);
			    $('.lecModForm select[name=lecDay]').val(lectureEntity.lecDay || "월");
			    $('.lecModForm select[name=beginTime]').val(lectureEntity.beginTime);
			    $('.lecModForm select[name=endTime]').val(lectureEntity.endTime);
			    $('.lecModForm input[name=lecLimit]').val(lectureEntity.lecLimit);
			    $('.lecModForm input[name=depCode]').val(lectureEntity.depCode);
			    $('.lecModForm input[name=no]').val(data.no);
			    alert('정보를 불러왔습니다.');
			    $('.lectureMod').show();
			}
		);
		
	})
}

// 수정 확인
function lecModComp(){
	$(document).on('click', '.btnLecModify', function(e){
		e.preventDefault();
		if(!confirm('수정하시겠습니까?')){
			return false;
		}else{
			$('.lecModForm').submit();
		}
	});
}

// 삭제
function delLecutre(){
	$(document).on('click', '.btnLecDel', function(){
		let tr = $(this).closest('tr');
		let lecCode = tr.children('td:eq(4)').text();
		let url = '/ChimAcademy/assistant/lecture/'+lecCode;

		$.ajax({
			type: "delete",
			url: url,
			dataType: "json",
			success: function (data) {
				if(data > 0){
					alert('삭제에 성공하였습니다.');
					location.reload();
				}
			}
		});		
	})	
}