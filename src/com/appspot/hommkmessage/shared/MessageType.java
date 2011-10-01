/*******************************************************************************
 * This file is part of hommkmessage.
 * 
 * hommkmessage is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * hommkmessage is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with hommkmessage.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.appspot.hommkmessage.shared;

public enum MessageType {

	TYPE_BATTLE {
		@Override
		String getIconUrl() {
			return "images/zeimusu_Crossed_swords_streched.svg";
		}
	},
	TYPE_SPYING {
		@Override
		String getIconUrl() {
			return "images/secretlondon_Brown_eye.svg";
		}
	},
	TYPE_UNKNOWN {
		@Override
		String getIconUrl() {
			return null;
		}
	};

	public static MessageType readTypeOfMessageHtml(String html) {
		if (html.contains("BattleResultDetail")) {
			return TYPE_BATTLE;
		}
		if (html.contains("ScoutingResultDetail")) {
			return TYPE_SPYING;
		}
		return TYPE_UNKNOWN;
	}

	abstract String getIconUrl();

	public String getIconHtml() {
		String iconUrl = getIconUrl();
		if (iconUrl == null) {
			return "";
		}
		String iconHtml = "<img src=\"" + iconUrl
				+ "\" class=\"messageListEntryTypeIcon\"/>";
		return iconHtml;
	}

}
