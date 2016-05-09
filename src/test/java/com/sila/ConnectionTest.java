package com.sila;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;

public class ConnectionTest {

	public static void main(final String[] args) {
		final String server = "snarl://localhost:5820/";
		try {
			final Connection aConn = ConnectionConfiguration.to("Persons")
					.server(server).credentials("admin", "admin").connect();

			aConn.begin();

			// aConn.add().io().format(RDFFormat.TURTLE)
			// .stream(new FileInputStream("data/sp2b_10k.n3"));
			//
			// final Model aGraph = Models2.newModel(Values.statement(
			// Values.iri("urn:subj"), Values.iri("urn:pred"),
			// Values.iri("urn:obj")));
			//
			// final Resource aContext = Values.iri("urn:test:context");
			//
			// aConn.add().graph(aGraph, aContext);

			aConn.commit();

		} catch (final StardogException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
