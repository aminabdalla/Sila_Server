package com.sila.dao;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

import java.io.InputStream;

/**
 * Created by amin on 16/10/2016.
 */
public class ModelSupplier {

    public static Model supplyModel(){
        return readModel();
    }

    private static Model readModel() {
        String inputFileName = "myFamilyOntology.owl";
        Model model = ModelFactory.createDefaultModel();

        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + inputFileName + " not found");
        }

        model.read(in, null);
        return model;
    }

}
