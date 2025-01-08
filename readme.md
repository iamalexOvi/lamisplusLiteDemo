LamisPlusLite
A lightweight HIV/AIDS Patient Management System implemented in Java and Python.

Overview
LamisPlusLite is a command-line application that helps healthcare providers manage patient records and HIV testing data. The system supports:
- Patient registration and management
- HIV test recording and tracking
- Clinical diagnosis tracking
- Patient record viewing

Prerequisites
- Java JDK 17 or higher
- Python 3.8 or higher (for Python version)
- An IDE like Visual Studio Code or IntelliJ IDEA

Setup
Java Version
Clone the repository:
```bash
git clone https://github.com/yourusername/LamisPlusLite.git
cd LamisPlusLite
```
Compile the Java program:
```bash
javac LamisPlusLite.java
```
Run the application:
```bash
java LamisPlusLite
```

Python Version
Install required dependencies:
```bash
pip install -r requirements.txt
```
Run the Python version:
```bash
python lamis_plus_lite.py
```

Usage
The system presents a menu with the following options:

1. Add Patient - Register a new patient
2. Add HIV Test - Record a new HIV test for a patient
3. Add Clinical Diagnosis - Add diagnosis information
4. View Patient Record - Display complete patient information
5. View All Patients - List all registered patients
6. Exit - Close the application

Data Storage
Java version stores log data in LamisPlusLite.log
Python version stores data in data.json and logs in lamis_plus_lite.log

Testing
To run the Java tests:
```bash
javac -cp .:junit-platform-console-standalone-1.8.2.jar LamisPlusLiteTest.java
java -cp .:junit-platform-console-standalone-1.8.2.jar org.junit.platform.console.ConsoleLauncher --class-path . --scan-class-path
```

File Structure
.
├── LamisPlusLite.java        # Main Java implementation
├── LamisPlusLiteTest.java    # Java unit tests
├── lamis_plus_lite.py        # Python implementation
├── data.json                 # Data storage for Python version
├── LamisPlusLite.log        # Java application logs
└── lamis_plus_lite.log      # Python application logs


Authors
Ovey Alexander - Initial work