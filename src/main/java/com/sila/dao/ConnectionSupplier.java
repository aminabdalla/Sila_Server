package com.sila.dao;

import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;

/**
 * Created by amin on 16/10/2016.
 */
public class ConnectionSupplier {

    public static Connection returnConnection() {

        final String server = "http://localhost:5820/";

        return ConnectionConfiguration.to("Persons")
                .credentials("admin", "admin")
                .server(server)
                .reasoning(true)
                .connect();
    }

}
