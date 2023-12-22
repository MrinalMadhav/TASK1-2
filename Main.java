import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Complaint {
    private String complaintId;
    private String serviceNumber;
    private String address;
    private String contactNumber;
    private boolean resolved;

    public Complaint(String complaintId, String serviceNumber, String address, String contactNumber) {
        this.complaintId = complaintId;
        this.serviceNumber = serviceNumber;
        this.address = address;
        this.contactNumber = contactNumber;
        this.resolved = false;  // Initially, complaint is unresolved
    }

    public String getComplaintId() {
        return complaintId;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void markResolved() {
        this.resolved = true;
    }
}

class ComplaintManager {
    private List<Complaint> complaints;

    public ComplaintManager() {
        complaints = new ArrayList<>();
    }

    public void addComplaint(String complaintId, String serviceNumber, String address, String contactNumber) {
        if (complaints.size() > 5) {
            sendMessageToSuperintendent();
        }

        // Check if the complaintId is unique
        boolean uniqueComplaintId = complaints.stream().noneMatch(c -> c.getComplaintId().equals(complaintId));

        if (uniqueComplaintId) {
            Complaint newComplaint = new Complaint(complaintId, serviceNumber, address, contactNumber);
            complaints.add(newComplaint);
        } else {
            System.out.println("Complaint ID already exists.");
        }
    }

    public Complaint searchComplaint(String complaintId) {
        return complaints.stream()
                .filter(c -> c.getComplaintId().equals(complaintId))
                .findFirst()
                .orElse(null);
    }

    public void displayAllComplaints() {
        System.out.println("All Complaints:");
        for (Complaint complaint : complaints) {
            System.out.println(complaint.getComplaintId() + " - " +
                    "Service Number: " + complaint.getServiceNumber() +
                    ", Address: " + complaint.getAddress() +
                    ", Contact Number: " + complaint.getContactNumber());
        }
    }

    public void removeResolvedComplaint(String complaintId) {
        Iterator<Complaint> iterator = complaints.iterator();
        while (iterator.hasNext()) {
            Complaint complaint = iterator.next();
            if (complaint.getComplaintId().equals(complaintId) && complaint.isResolved()) {
                iterator.remove();
                System.out.println("Complaint ID: " + complaintId + " has been removed.");
            }
        }
    }

    private void sendMessageToSuperintendent() {
        System.out.println("Sending message to the superintendent: More than 5 complaints");
    }
}

public class Main {
    public static void main(String[] args) {
        ComplaintManager complaintManager = new ComplaintManager();
        Scanner sc = new Scanner(System.in);

        for (int i = 1; i <= 6; i++) {
            System.out.println("Enter details for complaint " + i + ":");
            String id = "C" + i;

            System.out.print("Service Number: ");
            String serviceNumber = sc.nextLine();

            System.out.print("Address: ");
            String address = sc.nextLine();

            System.out.print("Contact Number: ");
            String contactNumber = sc.nextLine();

            complaintManager.addComplaint(id, serviceNumber, address, contactNumber);
        }

        complaintManager.displayAllComplaints();

        System.out.println("Enter the search complaint ID:");
        String searchID = sc.nextLine();
        Complaint searchResult = complaintManager.searchComplaint(searchID);
        if (searchResult != null) {
            System.out.println("Found Complaint: " + searchResult.getComplaintId());
        } else {
            System.out.println("Complaint not found!");
        }

        System.out.println("Marking a complaint as resolved and removing it");
        complaintManager.searchComplaint(searchID).markResolved();
        complaintManager.removeResolvedComplaint(searchID);

        complaintManager.displayAllComplaints();

        sc.close();
    }
}
