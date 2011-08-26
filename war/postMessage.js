function submitMessage(key, serviceUrl){
	var css = '<head><link rel="stylesheet" type="text/css" href="http://static5.cdn.ubi.com/u/HOMMK/mightandmagicheroeskingdoms.ubi.com/1.5.0.90-2-MTR/css/prod.css" /></head>';
	var messageBoxHeaderElement = getMessageElementByClass('messageBoxFrameMessageHeader');
	var messageBoxContentElement = getMessageElementByClass('messageBoxFrameMessageContent');
	var subjectText = getMessageElementByClass('messageHeaderSubject').textContent;
	var dateText = getMessageElementByClass('messageHeaderSubject').parentNode.textContent.replace(subjectText, '');
	var receiverText = getMessageElementByClass('messageHeaderReceivers').textContent;
	
	var messageCode = outerHTML(messageBoxHeaderElement);
	messageCode += outerHTML(messageBoxContentElement);
	messageCode = removeHiddenChildrenOfNodeInHtml(messageBoxContentElement, messageCode);
	var code = css + messageCode;
	code = code.replace(/&quot;/g, '&amp;quot;');
	code = code.replace(/"/g, '&quot;');
	var headerText = messageBoxHeaderElement.textContent;
	var contentText = removeHiddenChildrenOfNodeInContentText(messageBoxContentElement);
	
	var newWindow = window.open("", "_blank"); 
	var newSource = '<form id="tmpForm" action="'+serviceUrl+'" method="post">';
	newSource += createHiddenField("source", code); 
	newSource += createHiddenField("key", key); 
	newSource += createHiddenField("subjectText", subjectText); 
	newSource += createHiddenField("dateText", dateText); 
	newSource += createHiddenField("receiverText", receiverText); 
	newSource += createHiddenField("contentText", contentText);
	newSource += createHiddenField("userId", getUserId());
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
		if (children[i].nodeType == 1) {
			html = html.replace(outerHTML(children[i]), '');
		}
	}
	return html;
}

function removeHiddenChildrenOfNodeInContentText(parent) {
	var text = '';
	var children = parent.childNodes;
	for (var i in children) {
		if (children[i].nodeType == 1 && !children[i].getAttribute("class").contains("hidden")) {
			text += children[i].textContent;
		}
	}
	return text;
}
function outerHTML(node){
	// IE and Chrome have their own method
	return node.outerHTML || (
		function(n){
			var div = document.createElement('div'), h;
			div.appendChild( n.cloneNode(true) );
			h = div.innerHTML;
			div = null;
			return h;
		}
	)(node);
}

function getUserId() {
	if (!localStorage.userId) {
		localStorage.userId = guid();
	}
	return localStorage.userId;
}
function S4() {
	   return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}
function guid() {
   return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
}
