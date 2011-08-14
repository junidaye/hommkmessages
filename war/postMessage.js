function submitMessage(key, serviceUrl){
	var css = '<head><link rel="stylesheet" type="text/css" href="http://static5.cdn.ubi.com/u/HOMMK/mightandmagicheroeskingdoms.ubi.com/1.5.0.90-2-MTR/css/prod.css" /></head>';
	var messageBoxHeaderElement = getMessageElementByClass('messageBoxFrameMessageHeader');
	var messageBoxContentElement = getMessageElementByClass('messageBoxFrameMessageContent');
	var subjectText = getMessageElementByClass('messageHeaderSubject').textContent;
	var dateText = getMessageElementByClass('messageHeaderSubject').parentNode.textContent.replace(subjectText, '');
	var receiverText = getMessageElementByClass('messageHeaderReceivers').textContent;
	
	var messageCode = messageBoxHeaderElement.outerHTML;
	messageCode += messageBoxContentElement.outerHTML;
	messageCode = removeHiddenChildrenOfNodeInHtml(messageBoxContentElement, messageCode);
	var code = css + messageCode;
	code = code.replace(/"/g, '&quot;');
	var headerText = messageBoxHeaderElement.textContent;
	var contentText = messageBoxContentElement.textContent;
	contentText = removeHiddenChildrenOfNodeInContentText(messageBoxContentElement, contentText);
	
	var newWindow = window.open("", "_blank"); 
	var newSource = '<form id="tmpForm" action="'+serviceUrl+'" method="post">';
	newSource += createHiddenField("source", code); 
	newSource += createHiddenField("key", key); 
	newSource += createHiddenField("subjectText", subjectText); 
	newSource += createHiddenField("dateText", dateText); 
	newSource += createHiddenField("receiverText", receiverText); 
	newSource += createHiddenField("contentText", contentText);
	newSource += '</form>';
	newWindow.document.charset=document.charset;
	newWindow.document.body.innerHTML += newSource;
	newWindow.document.getElementById("tmpForm").submit();
}

function createHiddenField(name, value) {
	return '<input type="hidden" name="' + name + '" value="' + value + '" />';
}

function getMessageElementByClass(className) {
	var allElementsOfClass = document.getElementsByClassName(className);
	return allElementsOfClass[allElementsOfClass.length-1];
}

function removeHiddenChildrenOfNodeInHtml(parent, html) {
	var children = parent.getElementsByClassName("hidden");
	for (var i in children) {
		html = html.replace(children[i].outerHTML, '');
	}
	return html;
}

function removeHiddenChildrenOfNodeInContentText(parent, text) {
	var children = parent.getElementsByClassName("hidden");
	for (var i in children) {
		html = html.replace(children[i].contentText, '');
	}
	return html;
}