package ru.vasyunin.geo.dep;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.FileInputStream;
import java.util.Properties;

public class MongoConnector {
    private MongoCollection<Document> table;

    private static MongoConnector connector = new MongoConnector();

    static MongoConnector getConnector() {
        return connector;
    }

    public MongoCollection<Document> getTable() {
        return table;
    }

    private MongoConnector() {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(getClass().getClassLoader().getResource("config.properties").getFile()));
            MongoClient mongoClient = MongoClients.create(prop.getProperty("db.mongo.connectionString"));
            MongoDatabase db = mongoClient.getDatabase(prop.getProperty("db.mongo.dbname"));
            table = db.getCollection(prop.getProperty("db.mongo.collection"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
