package ru.vasyunin.geo.dep;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws DepException {
        Dep dep = new Dep("D:\\GDrive\\Geo\\Shrt_002.dep", "D:\\GDrive\\Geo\\Shrt_002.lst", Dep.class.getResource("/parsys/").getFile());
        dep.open();

        MongoCollection<Document> mc = MongoConnector.getConnector().getTable();


        Iterator<DepRowStruct> i = dep.iterator();
        ArrayList<Document> alDoc = new ArrayList<>();

        while (i.hasNext()){
            Map<String, Object> map = i.next().getParamsMap();
            System.out.println(map.get("numrec") + " " + map.get("uniquewell"));
            alDoc.add(new Document(map));
        }

        System.out.println(alDoc.size());

        mc.insertMany(alDoc);
        System.out.println(mc.countDocuments());

        dep.close();
    }
}
