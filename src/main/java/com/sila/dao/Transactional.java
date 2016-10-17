package com.sila.dao;

import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.query.Query;
import com.sila.dbo.Person;
import com.sila.utils.IOResult;
import org.openrdf.query.QueryResult;
import org.openrdf.query.TupleQueryResult;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Transactional {

    public final static void doSelectInTransaction(Consumer<Connection> query) {

        try {
            Connection connection = ConnectionSupplier.returnConnection();
            connection.begin();
            query.accept(connection);
            connection.commit();
//            connection.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
