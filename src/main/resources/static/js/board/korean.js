$(document).ready(function() { 
    //첫 번째 버튼 이벤트
     $("#delbtn").click(function() {
    			
		let no = $('input[name=no]').val();
		$.ajax({
     		type: "POST",
     		url: "/ChimAcademy/board/A101/delete?no="+no, 
      		success: function (data) {
      			if(data.result > 0){
      				alert('삭제 완료');
      				location.href = "/ChimAcademy/board/A101/list";
      			}else {
      				alert('삭제 실패');
      			}
     		}
    }); 
 });
});