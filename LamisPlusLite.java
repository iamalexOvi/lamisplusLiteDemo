import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LamisPlusLite {
    private static final Logger LOGGER = Logger.getLogger(LamisPlusLite.class.getName());
    private static FileHandler fh;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Patient> patients = new ArrayList<>();
    private static final String MENU_BORDER = "=".repeat(50);
    private static final Map<Integer, String> MENU_OPTIONS = Map.of(
        1, "Add Patient",
        2, "Add HIV Test", 
        3, "Add Clinical Diagnosis",
        4, "View Patient Record",
        5, "View All Patients",
        6, "Exit"
    );
    
    static {
        try {
            fh = new FileHandler("LamisPlusLite.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
            LOGGER.setLevel(Level.ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Patient {
        int id;
        String patientId;
        LocalDate dateCreated;
        String gender;
        String education;
        String maritalStatus;
        String address;
        LocalDate dob;
        String contact;
        LocalDate dateOfRegistration;
        String firstName;
        String lastName;
        String otherName;
        String hospitalNumber;
        String uuid;
        String ninNumber;
        ArrayList<Diagnosis> diagnoses = new ArrayList<>();
        ArrayList<Test> tests = new ArrayList<>();
        ArrayList<Treatment> treatments = new ArrayList<>();

        public Patient(int id, String patientId, LocalDate dateCreated, String gender, String education,
                       String maritalStatus, String address, LocalDate dob, String contact, LocalDate dateOfRegistration,
                       String firstName, String lastName, String otherName, String hospitalNumber, String uuid,
                       String ninNumber) {
            this.id = id;
            this.patientId = patientId;
            this.dateCreated = dateCreated;
            this.gender = gender;
            this.education = education;
            this.maritalStatus = maritalStatus;
            this.address = address;
            this.dob = dob;
            this.contact = contact;
            this.dateOfRegistration = dateOfRegistration;
            this.firstName = firstName;
            this.lastName = lastName;
            this.otherName = otherName;
            this.hospitalNumber = hospitalNumber;
            this.uuid = uuid;
            this.ninNumber = ninNumber;
        }
        
        @Override
        public String toString() {
            return String.format("Patient[id=%d, patientId=%s, dateCreated=%s, gender=%s]",
                id, patientId, dateCreated, gender);
        }
    }

    static class Diagnosis {
        String diagnosis;
        LocalDate date;

        public Diagnosis(String diagnosis, LocalDate date) {
            this.diagnosis = diagnosis;
            this.date = date;
        }
    }

    static class Test {
        String testName;
        String result;
        LocalDate date;

        public Test(String testName, String result, LocalDate date) {
            this.testName = testName;
            this.result = result;
            this.date = date;
        }
    }

    static class Treatment {
        String treatment;
        LocalDate startDate;
        LocalDate endDate;

        public Treatment(String treatment, LocalDate startDate, LocalDate endDate) {
            this.treatment = treatment;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    static class HivTesting {
        int id;
        String person_uuid;
        String uuid;
        LocalDate dateVisit;
        String referredFrom;
        String testingSetting;
        String hivTestResult;
        String prepOffered;
        String prepGiven;
        String prepAccepted;
        boolean firstTimeVisit;
        int numberOfChildren;
        String indexClient;
        String cd4Result;
        String stiScreening;

        //create constructor HivTesting
        public HivTesting(int id, String person_uuid, String uuid, LocalDate dateVisit, String referredFrom, String testingSetting, String hivTestResult, String prepOffered, String prepGiven, String prepAccepted, boolean firstTimeVisit, int numberOfChildren, String indexClient, String cd4Result, String stiScreening) {
            this.id = id;
            this.person_uuid= person_uuid;
            this.uuid = uuid;
            this.dateVisit = dateVisit;
            this.referredFrom = referredFrom;
            this.testingSetting = testingSetting;
            this.hivTestResult = hivTestResult;
            this.prepOffered = prepOffered;
            this.prepAccepted = prepAccepted;
            this.prepGiven = prepGiven;
            this.firstTimeVisit = firstTimeVisit;
            this.numberOfChildren = numberOfChildren;
            this.indexClient = indexClient;
            this.cd4Result = cd4Result;
            this.stiScreening = stiScreening;
        }
    }

    static ArrayList<HivTesting> hivTests = new ArrayList<>();

    private static Patient findPatientById(int id) {
        for (Patient patient : patients) {
            if (patient.id == id) {
                return patient;
            }
        }
        return null;
    }

    private static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return null;
        }
    }

    public static void addPatient() {
        try {
            int id = patients.size() + 1;
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter other name: ");
            String otherName = scanner.nextLine();
            System.out.print("Enter date of birth (YYYY-MM-DD): ");
            String dob = scanner.nextLine();
            LocalDate parsedDob = parseDate(dob);
            if (parsedDob == null) {
                System.out.println("Please enter a valid date in the format YYYY-MM-DD.");
                return;
            }

            System.out.print("Enter gender (Male/Female): ");
            String gender = scanner.nextLine();
            System.out.print("Enter education level: ");
            String education = scanner.nextLine();
            System.out.print("Enter marital status: ");
            String maritalStatus = scanner.nextLine();
            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            System.out.print("Enter contact: ");
            String contact = scanner.nextLine();
            LocalDate dateOfRegistration = LocalDate.now();

String hospitalNumber = "HOSP-" + id;
String uuid = java.util.UUID.randomUUID().toString();
String ninNumber = "NIN-" + id;

            Patient patient = new Patient(id, "PAT-" + id, LocalDate.now(), gender, education, maritalStatus, address,
                    parsedDob, contact, dateOfRegistration, firstName, lastName, otherName, hospitalNumber, uuid, ninNumber);
            patients.add(patient);
            System.out.println("Patient added successfully with ID " + id + "!\n");
        } catch (Exception e) {
            System.out.println("An error occurred while adding the patient. Please try again.");
        }
    }

    public static void addDiagnosis() {
        try {
            System.out.print("Enter patient ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Patient patient = findPatientById(id);
            if (patient == null) {
                System.out.println("Patient not found. Please ensure you have entered the correct ID.\n");
                return;
            }

            System.out.print("Enter diagnosis: ");
            String diagnosis = scanner.nextLine();
            LocalDate date = LocalDate.now();

            patient.diagnoses.add(new Diagnosis(diagnosis, date));
            System.out.println("Diagnosis added for patient " + patient.firstName + " " + patient.lastName + "!\n");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid patient ID as a number.");
            scanner.nextLine(); // Consume invalid input
        } catch (Exception e) {
            System.out.println("An error occurred while adding the diagnosis. Please try again.");
        }
    }

    public static void addHivTest() {
        try {
            System.out.print("Enter patient UUID: ");
            String person_uuid = scanner.nextLine();
            System.out.print("Enter referred from: ");
            String referredFrom = scanner.nextLine();
            System.out.print("Enter testing setting: ");
            String testingSetting = scanner.nextLine();
            System.out.print("Enter HIV test result (Positive/Negative): ");
            String hivTestResult = scanner.nextLine();

            String prepGiven = "";
            if (hivTestResult.equalsIgnoreCase("Negative")) {
                System.out.print("Enter prep given (Yes/No): ");
                prepGiven = scanner.nextLine();
            }

            System.out.print("Is this a first-time visit (true/false): ");
            boolean firstTimeVisit = scanner.nextBoolean();
            scanner.nextLine(); // accept user data
            System.out.print("Enter numberOfChildren: ");
            int numberOfChildren = scanner.nextInt();
            scanner.nextLine(); // accept user data
            System.out.print("Enter CD4 result: ");
            String cd4Result = scanner.nextLine();
            System.out.print("Enter STI screening result: ");
            String stiScreening = scanner.nextLine();

            String uuid = "HIVTEST" + (hivTests.size() + 1);
            String indexClient = "INDEX" + (hivTests.size() + 1);
            LocalDate dateVisit = LocalDate.now();
                    HivTesting hivTest = new HivTesting(hivTests.size() + 1, person_uuid, uuid, dateVisit, referredFrom, testingSetting, hivTestResult, "", prepGiven, "", firstTimeVisit, numberOfChildren, indexClient, cd4Result, stiScreening);
            hivTests.add(hivTest);
            System.out.println("HIV test added successfully with ID " + hivTest.id + "!\n");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please ensure all entries are in the correct format.");
            scanner.nextLine(); // Take invalid input
        } catch (Exception e) {
            System.out.println("An error occurred while adding the HIV test. Please try again.");
        }
    }

    public static void viewPatientRecord() {
        try {
            // Get Patient ID
            System.out.print("Enter patient ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Patient patient = findPatientById(id);
            if (patient == null) {
                System.out.println("Patient not found with ID: " + id);
                return;
            }

            // Section 1: Patient Demographics
            System.out.println("\n" + "=".repeat(50));
            System.out.println("PATIENT DEMOGRAPHICS");
            System.out.println("=".repeat(50));
            System.out.printf("ID: %d%n", patient.id);
            System.out.printf("Name: %s %s %s%n", patient.firstName, patient.lastName, patient.otherName);
            System.out.printf("Hospital Number: %s%n", patient.hospitalNumber);
            System.out.printf("Gender: %s%n", patient.gender);
            System.out.printf("DOB: %s%n", patient.dob.format(dateFormatter));
            System.out.printf("Contact: %s%n", patient.contact);

            // Section 2: HIV Test History
            System.out.println("\n" + "=".repeat(50));
            System.out.println("HIV TEST HISTORY");
            System.out.println("=".repeat(50));
            boolean hasTests = false;
            for (HivTesting test : hivTests) {
                if (test.id == patient.id) {
                    hasTests = true;
                    displayHivTestResults(test);
                }
            }
            if (!hasTests) System.out.println("No HIV tests recorded");

            // Section 3: Clinical Diagnoses
            System.out.println("\n" + "=".repeat(50));
            System.out.println("CLINICAL DIAGNOSES");
            System.out.println("=".repeat(50));
            if (patient.diagnoses.isEmpty()) {
                System.out.println("No clinical diagnoses recorded");
            } else {
                for (Diagnosis diag : patient.diagnoses) {
                    System.out.printf("\nDate: %s%n", diag.date.format(dateFormatter));
                    System.out.printf("Diagnosis: %s%n", diag.diagnosis);
                    System.out.println("-".repeat(30));
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid patient ID.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error viewing patient record: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Error in viewPatientRecord", e);
        }
    }

    private static void viewAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients registered in the system.");
            return;
        }

        System.out.println("\nAll Patients (Sorted by ID):");
        System.out.println("----------------------------------------");
        patients.stream()
                .sorted(Comparator.comparingInt(p -> p.id))
                .forEach(p -> System.out.printf("ID: %d | Name: %s %s | DOB: %s | Gender: %s%n",
                        p.id, p.firstName, p.lastName,
                        p.dob.format(dateFormatter),
                        p.gender));
        System.out.println("----------------------------------------");
    }

    private static void debugPrintState() {
        LOGGER.info("Current system state:");
        LOGGER.info("Number of patients: " + patients.size());
        for (Patient p : patients) {
            LOGGER.info(p.toString());
        }
    }

    private static void validatePatientData(Patient p) throws IllegalArgumentException {
        if (p.patientId == null || p.patientId.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient ID cannot be empty");
        }
        if (p.gender == null || (!p.gender.equalsIgnoreCase("M") && !p.gender.equalsIgnoreCase("F"))) {
            throw new IllegalArgumentException("Gender must be 'M' or 'F'");
        }
        if (p.dateCreated == null) {
            throw new IllegalArgumentException("Date created cannot be null");
        }
    }

    private static void displayMenu() {
        System.out.println("\n" + MENU_BORDER);
        System.out.println("LAMISPLUS LITE - HIV/AIDS Management System");
        System.out.println(MENU_BORDER);
        MENU_OPTIONS.forEach((key, value) -> 
            System.out.printf("%d. %s%n", key, value));
        System.out.println(MENU_BORDER);
        System.out.print("Enter your choice (1-6): ");
    }

    public static void main(String[] args) {
        while (true) {
            try {
                displayMenu();
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    System.out.println("Please make a selection");
                    continue;
                }

                int choice = validateMenuInput(input);
                
                switch (choice) {
                    case 1 -> addPatient();
                    case 2 -> addHivTest();
                    case 3 -> addDiagnosis();
                    case 4 -> viewPatientRecord();
                    case 5 -> viewAllPatients();
                    case 6 -> {
                        System.out.println("Exiting system. Goodbye!");
                        System.exit(0);
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                LOGGER.log(Level.WARNING, "Invalid menu input", e);
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
                LOGGER.log(Level.SEVERE, "Error in main menu", e);
            }
        }
    }

    private static int validateMenuInput(String input) {
        try {
            if (input == null || input.trim().isEmpty()) {
                throw new IllegalArgumentException("Input cannot be empty");
            }

            int choice = Integer.parseInt(input.trim());
            if (choice < 1 || choice > MENU_OPTIONS.size()) {
                throw new IllegalArgumentException(
                    String.format("Please enter a number between 1 and %d", MENU_OPTIONS.size())
                );
            }
            return choice;

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
            LOGGER.log(Level.WARNING, "Invalid number format entered: " + input);
            throw new IllegalArgumentException("Invalid input");
        }
    }

    private static void displayHivTestResults(HivTesting test) {
        System.out.printf("Test Date: %s%n", test.dateVisit.format(dateFormatter));
        System.out.printf("Test Setting: %s%n", test.testingSetting);
        System.out.printf("Result: %s%n", test.hivTestResult);

        if ("Negative".equalsIgnoreCase(test.hivTestResult)) {
            System.out.printf("PrEP Given: %s%n", test.prepGiven);
        } else if ("Positive".equalsIgnoreCase(test.hivTestResult)) {
            System.out.printf("CD4 Count: %s%n", test.cd4Result);
        }

        System.out.printf("STI Screening: %s%n", test.stiScreening);
        System.out.println("-".repeat(30));
    }
}
