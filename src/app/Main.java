package app;

import model.store.DataStore;
import persistence.FileLoader;

public class Main {

    public static void main(String[] args) {

        DataStore store = new DataStore();

        FileLoader.loadPatients("data/patients.csv", store);

        System.out.println("Patients loaded: " + store.getPatients().size());
    }
}
