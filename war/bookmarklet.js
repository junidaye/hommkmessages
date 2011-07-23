javascript:(function(){
	var s=document.createElement('script');
	s.type='text/javascript';
	s.src='http://127.0.0.1:8888/postMail.js';
	s.onload = function(){
			submitMail('some_password');
		};
	document.head.appendChild(s);
})();