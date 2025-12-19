package persistence;

import model.Prescription;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PrescriptionWriter {

    // Appends one prescription as a new CSV row
    public static void appendPrescription(String filePath, Prescription p) {
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
            System.out.println("Error writing prescription to file: " + filePath);
            e.printStackTrace();
        }
    }

    // Converts the prescription into the same column order used by FileLoader.loadPrescriptions(...)
    private static String toCsvRow(Prescription p) {
        // Keep this order EXACTLY the same as your FileLoader parts[0]..parts[14]
        return safe(p.getPrescriptionId()) + "," +
                safe(p.getPatientId()) + "," +
                safe(p.getClinicianId()) + "," +
                safe(p.getAppointmentId()) + "," +
                safe(p.getPrescriptionDate()) + "," +
                safe(p.getMedicationName()) + "," +
                safe(p.getDosage()) + "," +
                safe(p.getFrequency()) + "," +
                safe(p.getDurationDays()) + "," +
                safe(p.getQuantity()) + "," +
                safe(p.getInstructions()) + "," +
                safe(p.getPharmacyName()) + "," +
                safe(p.getStatus()) + "," +
                safe(p.getIssueDate()) + "," +
                safe(p.getCollectionDate());
    }

    // Basic safety for commas/newlines (simple approach)
    private static String safe(String value) {
        if (value == null) return "";
        String v = value.replace("\n", " ").replace("\r", " ");
        // If it contains commas, wrap in quotes
        if (v.contains(",")) {
            v = "\"" + v.replace("\"", "\"\"") + "\"";
        }
        return v;
    }
}
