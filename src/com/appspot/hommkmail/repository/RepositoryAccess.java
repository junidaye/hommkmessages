package com.appspot.hommkmail.repository;

public class RepositoryAccess {
	static String oneAndOnlySource = "";

	public void save(String htmlSource) {
		oneAndOnlySource = htmlSource;
	}

	public String loadAny() {
		return oneAndOnlySource;
	}

}
