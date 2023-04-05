// 라디오 변경 불가 스크립트
$(function() {
	let ipt = document.querySelectorAll('input.readonly');
	for (let i = 0; i < ipt.length; i++) {
		ipt[i].setAttribute('onclick', 'return false');
	}
});