package com.sila;


import com.complexible.common.base.Options;
import com.complexible.stardog.api.SelectQuery;
import com.sila.dao.Transactional;
import com.sila.utils.IOResult;
import org.apache.jena.rdf.model.Model;
import org.openrdf.query.*;
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

        IOResult<Exception, TupleQueryResult> aResult= null;

        Transactional.doSelectInTransaction(conn -> {
            IOResult.success((TupleQueryResult) conn.select("select * where { ?s <http://www.sila.com/family#hasChild> ?o }"));
        });

        if (aResult.isSuccess()) {
            while (aResult.getResult().hasNext()) {
                System.out.print("=======" + aResult.getResult().next().getBinding("o").getValue());
            }
        }
    }
}
