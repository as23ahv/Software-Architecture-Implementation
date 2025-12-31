package model.store;

import model.Referral;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

public class ReferralManager {

    private static ReferralManager instance;

    private final Queue<Referral> referralQueue;
    private final DateTimeFormatter formatter;

    private ReferralManager() {
        referralQueue = new LinkedList<>();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public static ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }

    public void enqueueReferral(Referral referral) {
        if (referral == null) return;
        referralQueue.add(referral);
    }

    public int getQueueSize() {
        return referralQueue.size();
    }

    public Referral peekNext() {
        return referralQueue.peek();
    }

    public Referral processNextReferral(DataStore store) {
        Referral referral = referralQueue.poll();
        if (referral == null) return null;

        String now = LocalDateTime.now().format(formatter);

        // Update status + last updated
        referral.setStatus("PROCESSED");
        referral.setLastUpdated(now);

        // Save updated referral to datastore
        if (store != null) {
            store.addReferral(referral);
        }

        // Generate "email" content and persist it
        String emailText = generateReferralEmailText(referral);
        appendToFile("output/referrals_emails.txt", emailText);

        // Write an audit trail entry
        String auditText = now + " | Referral processed | referralId=" + referral.getReferralId()
                + " | patientId=" + referral.getPatientId()
                + " | urgency=" + referral.getUrgencyLevel();
        appendToFile("output/ehr_audit_log.txt", auditText + System.lineSeparator());

        return referral;
    }

    public void processAllReferrals(DataStore store) {
        while (!referralQueue.isEmpty()) {
            processNextReferral(store);
        }
    }

    public String generateReferralEmailText(Referral r) {
        String now = LocalDateTime.now().format(formatter);

        StringBuilder sb = new StringBuilder();
        sb.append("========================================").append(System.lineSeparator());
        sb.append("REFERRAL EMAIL (SIMULATED)").append(System.lineSeparator());
        sb.append("Generated: ").append(now).append(System.lineSeparator());
        sb.append("========================================").append(System.lineSeparator());

        sb.append("Referral ID: ").append(r.getReferralId()).append(System.lineSeparator());
        sb.append("Referral Date: ").append(r.getReferralDate()).append(System.lineSeparator());
        sb.append("Urgency: ").append(r.getUrgencyLevel()).append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("Patient ID: ").append(r.getPatientId()).append(System.lineSeparator());
        sb.append("Referring Clinician ID: ").append(r.getReferringClinicianId()).append(System.lineSeparator());
        sb.append("Referred To Clinician ID: ").append(r.getReferredToClinicianId()).append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("Referring Facility ID: ").append(r.getReferringFacilityId()).append(System.lineSeparator());
        sb.append("Referred To Facility ID: ").append(r.getReferredToFacilityId()).append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("Referral Reason: ").append(r.getReferralReason()).append(System.lineSeparator());
        sb.append("Clinical Summary: ").append(r.getClinicalSummary()).append(System.lineSeparator());
        sb.append("Requested Investigations: ").append(r.getRequestedInvestigations()).append(System.lineSeparator());
        sb.append(System.lineSeparator());

        sb.append("Appointment ID (if any): ").append(r.getAppointmentId()).append(System.lineSeparator());
        sb.append("Status: ").append(r.getStatus()).append(System.lineSeparator());
        sb.append("Notes: ").append(r.getNotes()).append(System.lineSeparator());
        sb.append("Last Updated: ").append(r.getLastUpdated()).append(System.lineSeparator());
        sb.append(System.lineSeparator());

        return sb.toString();
    }

    private void appendToFile(String filePath, String content) {
        try {
            File file = new File(filePath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(content);
                if (!content.endsWith(System.lineSeparator())) {
                    writer.write(System.lineSeparator());
                }
            }

        } catch (IOException e) {
            System.out.println("Error writing to file: " + filePath);
            e.printStackTrace();
        }
    }
}
