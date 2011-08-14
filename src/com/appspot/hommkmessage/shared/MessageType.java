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
