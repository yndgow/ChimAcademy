/**
 * 날짜 : 2023/03/28
 * 이름 : 김지홍
 * 내용 : 교수 자바스크립트 함수
 */
$(function(){
    $('#side > li > ol').hide();

    $('#side > li > a').click(function(){
        $(this).siblings().slideToggle(300);
    });
});

function popupOpen(){
	$('.btn_plan').click(function(){
		let url = "/ChimAcademy/professor/class/write";
		let name = "강의계획서";
		let option = "width = 1400, height = 800, top = 100, left = 300";
		window.open(url, name, option);
	});
}

