javascript:(function(){
	var s=document.createElement('script');
	s.type='text/javascript';
	s.src='http://hommkmessages.appspot.com/postMessage.js';
	s.onload = function(){ submitMessage('test_key', 'http://hommkmessages.appspot.com/hommk_message/post')};
	document.head.appendChild(s);
})();
