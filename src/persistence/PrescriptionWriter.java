package persistence;

import model.Prescription;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PrescriptionWriter {

    public static void appendPrescription(String filePath, Prescription p) {
        if (p == null) return;

        try {
            File file = new File(filePath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(toCsvRow(p));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing prescription to file: " + filePath);
            e.printStackTrace();
        }
    }

    private static String toCsvRow(Prescription p) {
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

    private static String safe(String value) {
        if (value == null) return "";
        String v = value.replace("\n", " ").replace("\r", " ");
        if (v.contains(",")) {
            v = "\"" + v.replace("\"", "\"\"") + "\"";
        }
        return v;
    }
}
