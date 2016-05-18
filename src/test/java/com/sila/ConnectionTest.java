package com.sila;



import org.apache.jena.rdf.model.Model;
import org.openrdf.query.GraphQueryResult;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.QueryResults;
import org.openrdf.query.TupleQuery;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.quartz.jobs.ee.jms.SendDestinationMessageJob;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.GraphQuery;
import com.complexible.stardog.sesame.StardogRepository;



public class ConnectionTest {

	public static void main(final String[] args) {
		final String server = "snarl://localhost:5820/";
		try {
//			final Connection aConn = ConnectionConfiguration.to("myFamily")
//					.credentials("admin", "admin")
//					.server(server)
//					.connect();
			
			//aConn.begin();
			
//			GraphQuery gq = aConn.graph("select * where {?s a <http://www.sila.com/family#Child>}");
//			GraphQueryResult gqr = gq.execute();
			Repository repo = new StardogRepository(ConnectionConfiguration.to("myFamily")
					.credentials("admin", "admin")
					.server(server));
			repo.initialize();

			// now you can use it like a normal Sesame Repository
			RepositoryConnection aRepoConn = repo.getConnection();

			// always best to turn off auto commit
			aRepoConn.setAutoCommit(false);
			
			org.openrdf.query.GraphQuery gq = aRepoConn.prepareGraphQuery(QueryLanguage.SPARQL, "select * where {?s a <http://www.sila.com/family#Child>}");
			GraphQueryResult gqr = gq.evaluate();
			QueryResults.stream(gqr).forEach(s -> System.out.println(s.getSubject().stringValue()));
			
			//aConn.commit();

		} catch (final StardogException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
