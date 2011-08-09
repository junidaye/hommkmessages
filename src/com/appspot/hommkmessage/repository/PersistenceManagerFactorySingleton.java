package com.appspot.hommkmessage.repository;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public enum PersistenceManagerFactorySingleton {
	INSTANCE;

	private final PersistenceManagerFactory persistenceManagerFactory = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public PersistenceManagerFactory get() {
		return persistenceManagerFactory;
	}
}
