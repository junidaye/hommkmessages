javascript:(function(){
	var s=document.createElement('script');
	s.type='text/javascript';
	s.src='http://127.0.0.1:8888/postMessage.js';
	s.onload = function(){ submitMessage('test_key')};
	document.head.appendChild(s);
})();
