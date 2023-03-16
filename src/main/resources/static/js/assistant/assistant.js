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
    		row.depCode = $('input[name=depCode]').val();
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
			}
		})
	});
}

function searchBtn(){
	$('#btnSearchLec').click(function(){
		let lecGubun = $('select[name=lecGubun]').val();
		let searchData = $('input[name=searchData]').val();
		let depCode = $('select[name=depCode]').val();
		let majorCode = $('select[name=majorCode]').val();
		let lecClass = $('select[name=lecClass]').val();
		
		/*if(!lecGubun) lecGubun = null;
		if(!majorCode) majorCode = null;
		if(!lecClass) lecClass = null;*/
		
		
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
				console.log(data);
			}
		});
		
	});
}