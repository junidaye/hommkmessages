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
#-------------------------------------------------------------------------------*/
javascript:(function(){
	var s=document.createElement('script');
	s.type='text/javascript';
	s.src='http://hommkmessages.appspot.com/postMessage.js';
	s.onload = function(){ submitMessage('demo', 'http://hommkmessages.appspot.com/hommk_message/post')};
	document.head.appendChild(s);
})();
