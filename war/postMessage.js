function submitMessage(password){
	var css = '<head><link rel="stylesheet" type="text/css" href="http://static5.cdn.ubi.com/u/HOMMK/mightandmagicheroeskingdoms.ubi.com/1.5.0.90-2-MTR/css/prod.css" /></head>';
	var messageBoxHeaderElement = document.getElementsByClassName('messageBoxFrameMessageHeader');
	var messageBoxContentElement = document.getElementsByClassName('messageBoxFrameMessageContent');
	var messageCode = messageBoxHeaderElement[messageBoxHeaderElement.length-1].outerHTML;
	messageCode += messageBoxContentElement[messageBoxContentElement.length-1].outerHTML;
	var code = css + messageCode;
	code = code.replace(/"/g, '&quot;');
	
	var serviceCallUrl = 'http://127.0.0.1:8888/hommk_message/post';
	var newWindow = window.open("", "_blank"); 
	var newSource = '<form id="tmpForm" action="'+serviceCallUrl+'" method="post"><input type="hidden" name="source" value="' + code + '"></form>';
	newWindow.document.charset=document.charset;
	newWindow.document.body.innerHTML += newSource;
	newWindow.document.getElementById("tmpForm").submit();
}