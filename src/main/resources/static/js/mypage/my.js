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

$(function(){
	
		$('#savebtn').click(function(){
		if (!confirm("정말로 수정하시겠습니까?")) {
            return false;
        } else {
            return true;
        }
	})
	
})

