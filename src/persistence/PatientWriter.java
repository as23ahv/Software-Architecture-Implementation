package persistence;

import model.Patient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PatientWriter {

    public static void appendPatient(String filePath, Patient p) {
        if (p == null) return;

        try {
            File file = new File(filePath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(toCsvRow(p));
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error writing patient to file: " + filePath);
            e.printStackTrace();
        }
    }

    private static String toCsvRow(Patient p) {
        // Must match your FileLoader.loadPatients order (parts[0]..parts[7])
        return safe(p.getPatientId()) + "," +
                safe(p.getFirstName()) + "," +
                safe(p.getLastName()) + "," +
                safe(p.getNhsNumber()) + "," +
                safe(p.getDateOfBirth()) + "," +
                safe(p.getPhoneNumber()) + "," +
                safe(p.getEmail()) + "," +
                safe(p.getGpSurgeryId());
    }

    private static String safe(String value) {
        if (value == null) return "";
        String v = value.replace("\n", " ").replace("\r", " ");
        if (v.contains(",")) {
            v = "\"" + v.replace("\"", "\"\"") + "\"";
        }
        return v;
    }
}
