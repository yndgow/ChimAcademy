/**
 * 날짜 : 2023/03/13
 * 이름 : 김지홍
 * 내용 : 공통 자바스크립트
 */

/* aside 슬라이드*/
$(function(){
    $('#side > li > ol').hide();

    $('#side > li > a').click(function(){
        $(this).siblings().slideToggle(300);
    });
});