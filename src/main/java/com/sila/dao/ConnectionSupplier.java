package com.sila.dao;

import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;

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
