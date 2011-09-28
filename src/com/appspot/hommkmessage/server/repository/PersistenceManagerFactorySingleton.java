package com.appspot.hommkmessage.server.repository;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public enum PersistenceManagerFactorySingleton {
	INSTANCE;

	private final PersistenceManagerFactory factory = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public PersistenceManagerFactory getFactory() {
		return factory;
	}

}
