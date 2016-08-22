package com.usbus.dal;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import com.usbus.dal.model.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by JuanPablo on 4/27/2016.
 */
public class MongoDB {

//    public static final String DB_HOST = "localhost";
//    public static final int DB_PORT = 27017;
//    public static final String DB_NAME = "usbus";
    private static final MongoDB INSTANCE = new MongoDB();
    private final Datastore datastore;

    private MongoDB() {
        Properties propiedades = new Properties();
        try {
            propiedades.load(this.getClass().getResourceAsStream("/mongo.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String DB_HOST = propiedades.getProperty("DB_HOST");
        int DB_PORT = Integer.parseInt(propiedades.getProperty("DB_PORT"));
        String DB_NAME = propiedades.getProperty("DB_NAME");

        Morphia morphia = new Morphia();
        ServerAddress addr = new ServerAddress(DB_HOST, DB_PORT);
        MongoClient client = new MongoClient(addr);
        morphia.map(Tenant.class);
        morphia.map(Branch.class);
        morphia.map(BusStop.class);
        morphia.map(HumanResource.class);
        morphia.map(Journey.class);
        morphia.map(Maintenance.class);
        morphia.map(CashRegister.class);
        morphia.map(Parcel.class);
        morphia.map(Reservation.class);
        morphia.map(Route.class);
        morphia.map(Service.class);
        morphia.map(Ticket.class);
        morphia.map(User.class);
        datastore = morphia.createDatastore(client, DB_NAME);
        datastore.ensureIndexes();

    }

    public static MongoDB instance() {
        return INSTANCE;
    }

    public Datastore getDatabase() {
        return datastore;
    }
}
