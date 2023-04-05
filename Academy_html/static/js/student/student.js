/**
 * 
 */
$(function(){
    $('#side > li > ol').hide();

    $('#side > li > a').click(function(){
        $(this).siblings().slideToggle(300);
    });
});