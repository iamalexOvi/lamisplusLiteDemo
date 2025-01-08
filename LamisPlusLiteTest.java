import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LamisPlusLite Test Suite")
class LamisPlusLiteTest {
    private LamisPlusLite.Patient testPatient;
    private LamisPlusLite.HivTesting testHivTest;
    private static final LocalDate TEST_DATE = LocalDate.of(2024, 1, 1);
    private ArrayList<LamisPlusLite.Patient> patients;
    private ArrayList<LamisPlusLite.HivTesting> hivTests;

    @BeforeEach
    void setUp() {
        patients = new ArrayList<>();
        hivTests = new ArrayList<>();
        
        // Create test patient
        testPatient = new LamisPlusLite.Patient(
            1, 
            "PAT-001",
            LocalDate.now(),
            "Male",
            "Graduate",
            "Single",
            "123 Test St",
            TEST_DATE,
            "1234567890",
            LocalDate.now(),
            "John",
            "Doe",
            "",
            "HOSP-001",
            "test-uuid",
            "NIN-001"
        );
        patients.add(testPatient);

        // Create test HIV test
        testHivTest = new LamisPlusLite.HivTesting(
            1,
            "test-uuid",
            "HIV-001",
            LocalDate.now(),
            "Self",
            "Hospital",
            "Negative",
            "Yes",
            true,
            0,
            "IND-001",
            "500",
            "Negative"
        );
        hivTests.add(testHivTest);
    }

    @Test
    @DisplayName("Should create patient successfully")
    void testPatientCreation() {
        Assertions.assertNotNull(testPatient, "Patient should not be null");
        Assertions.assertEquals("PAT-001", testPatient.patientId);
        Assertions.assertEquals("John", testPatient.firstName);
        Assertions.assertEquals("Doe", testPatient.lastName);
    }

    @Test
    @DisplayName("Should create HIV test successfully")
    void testHivTestCreation() {
        Assertions.assertNotNull(testHivTest, "HIV test should not be null");
        Assertions.assertEquals("Negative", testHivTest.hivTestResult);
        Assertions.assertEquals("Yes", testHivTest.prepGiven);
    }

    @Test
    @DisplayName("Should validate patient data")
    void testPatientValidation() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testPatient.patientId = "";
            LamisPlusLite.validatePatientData(testPatient);
        });
        Assertions.assertTrue(exception.getMessage().contains("Patient ID cannot be empty"));
    }

    @Test
    void testMenuInputValidation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            LamisPlusLite.validateMenuInput("7");
        });
        assertTrue(exception.getMessage().contains("Invalid menu option"));
    }
}