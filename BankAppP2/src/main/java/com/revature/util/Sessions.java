package com.revature.util;

import org.eclipse.jetty.server.session.DatabaseAdaptor;
import org.eclipse.jetty.server.session.JDBCSessionDataStoreFactory;
import org.eclipse.jetty.server.session.NullSessionCache;
import org.eclipse.jetty.server.session.SessionCache;
import org.eclipse.jetty.server.session.SessionHandler;

public class Sessions {

	public SessionHandler sqlSessionHandler() {
	    SessionHandler sessionHandler = new SessionHandler();
	    SessionCache sessionCache = new NullSessionCache(sessionHandler);
	    sessionCache.setSessionDataStore(
	        jdbcDataStoreFactory().getSessionDataStore(sessionHandler)
	    );
	    sessionHandler.setSessionCache(sessionCache);
	    sessionHandler.setHttpOnly(true);
	    // make additional changes to your SessionHandler here
	    return sessionHandler;
	}

	public JDBCSessionDataStoreFactory jdbcDataStoreFactory() {
		
	    DatabaseAdaptor databaseAdaptor = new DatabaseAdaptor();
	    databaseAdaptor.setDriverInfo("org.postgresql.Driver", (System.getenv("db_url") + "?user=" 
	    		+ System.getenv("db_username") + "&password=" + System.getenv("db_password")));
	    // databaseAdaptor.setDatasource(myDataSource); // you can set data source here (for connection pooling, etc)
	    JDBCSessionDataStoreFactory jdbcSessionDataStoreFactory = new JDBCSessionDataStoreFactory();
	    jdbcSessionDataStoreFactory.setDatabaseAdaptor(databaseAdaptor);
	    return jdbcSessionDataStoreFactory;
	}
	
}
