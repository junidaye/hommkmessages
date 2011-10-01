/*-------------------------------------------------------------------------------
# This file is part of hommkmessage.
# 
# hommkmessage is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
# 
# hommkmessage is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
# 
# You should have received a copy of the GNU Affero General Public License
# along with hommkmessage.  If not, see <http://www.gnu.org/licenses/>.
-------------------------------------------------------------------------------*/
function submitMessage(key, serviceUrl){
	var css = '<head><link rel="stylesheet" type="text/css" href="http://static5.cdn.ubi.com/u/HOMMK/mightandmagicheroeskingdoms.ubi.com/1.5.0.90-2-MTR/css/prod.css" /></head>';
	var messageBoxHeaderElement = getMessageElementByClass('messageBoxFrameMessageHeader');
	var messageBoxContentElement = getMessageElementByClass('messageBoxFrameMessageContent');
	var subjectText = getMessageElementByClass('messageHeaderSubject').textContent;
	var dateText = getMessageElementByClass('messageHeaderSubject').parentNode.textContent.replace(subjectText, '');
	var receiverText = getMessageElementByClass('messageHeaderReceivers').textContent;
	
	var elementsWithAddedTitle = addSpyReportTooltips(messageBoxContentElement);
	var messageCode = outerHTML(messageBoxHeaderElement);
	messageCode += outerHTML(messageBoxContentElement);
	messageCode = removeHiddenChildrenOfNodeInHtml(messageBoxContentElement, messageCode);
	var code = css + messageCode;
	code = code.replace(/&quot;/g, '&amp;quot;');
	code = code.replace(/"/g, '&quot;');
	var headerText = messageBoxHeaderElement.textContent;
	var contentText = removeHiddenChildrenOfNodeInContentText(messageBoxContentElement);
	contentText = contentText.replace(/"/g, '&quot;');
	
	var newWindow = window.open("", "_blank"); 
	if (newWindow == null) {
		alert("Please disable popup blockers for this website and try again.");
	} else {
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
	cleanUpTitles(elementsWithAddedTitle);
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
function addSpyReportTooltips(messageElement) {
	var heroDivs = messageElement.getElementsByClassName("hero");
	var elementsWithAddedTitle = new Array();
	for (var i in heroDivs) {
	  var heroDiv = heroDivs[i];
	  if (heroDiv.parentElement && heroDiv.parentElement.parentElement && heroDiv.parentElement.parentElement.className.contains("hidden") || typeof heroDiv.tagName === 'undefined' || heroDiv.tagName.toLowerCase() !== "div") {
	    continue;
	  }
	  var heroId = heroDiv.getElementsByTagName("div")[0].getAttribute("heroid");
	  var heroContent = HOMMK.elementPool.obj.ScoutingResultHero.get(heroId).content;
	  var attack = typeof heroContent.attack === 'undefined' ? "?" : heroContent.attack;
	  var defense = typeof heroContent.defense === 'undefined' ? "?" : heroContent.defense;
	  var magic = typeof heroContent.magic === 'undefined' ? "?" : heroContent.magic;
	  var level = typeof heroContent._level === 'undefined' ? "?" : heroContent._level;
	  var title = HOMMK.HEROTRAINING_CARAC_LEVEL_TEXT + ": " + level;
	  title += "   \n";
	  title += HOMMK.HEROTRAINING_CARAC_ATTACK_TEXT + ": " + attack + "   \n" + HOMMK.HEROTRAINING_CARAC_DEFENSE_TEXT + ": " + defense + "   \n" + HOMMK.HEROTRAINING_CARAC_MAGIC_TEXT + ": " + magic;
	  heroDiv.getElementsByTagName("div")[0].title = title;
	  elementsWithAddedTitle.push(heroDiv.getElementsByTagName("div")[0]);
	}
	return elementsWithAddedTitle;
}
function cleanUpTitles(elementsWithAddedTitle) {
	for (var i in elementsWithAddedTitle) {
		elementsWithAddedTitle[i].title = '';
	}
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
