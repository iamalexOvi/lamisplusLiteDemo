from datetime import datetime
import json
import logging
import os
from typing import List, Optional

class Patient:
    def __init__(self, id: int, patient_id: str, first_name: str, last_name: str,
                 dob: datetime, gender: str, education: str, marital_status: str,
                 address: str, contact: str, registration_date: datetime):
        self.id = id
        self.patient_id = patient_id
        self.first_name = first_name
        self.last_name = last_name
        self.dob = dob
        self.gender = gender
        self.education = education
        self.marital_status = marital_status
        self.address = address
        self.contact = contact
        self.registration_date = registration_date
        self.uuid = f"PAT-{id}"

    def to_dict(self):
        return {
            'id': self.id,
            'patient_id': self.patient_id,
            'first_name': self.first_name,
            'last_name': self.last_name,
            'dob': self.dob.isoformat(),
            'gender': self.gender,
            'education': self.education,
            'marital_status': self.marital_status,
            'address': self.address,
            'contact': self.contact,
            'registration_date': self.registration_date.isoformat(),
            'uuid': self.uuid
        }

class HivTesting:
    def __init__(self, id: int, person_uuid: str, test_date: datetime,
                 setting: str, result: str, prep_given: str, cd4_result: str,
                 sti_screening: str):
        self.id = id
        self.person_uuid = person_uuid
        self.test_date = test_date
        self.setting = setting
        self.result = result
        self.prep_given = 'Not Eligible' if result.lower() == 'positive' else prep_given
        self.cd4_result = cd4_result
        self.sti_screening = sti_screening

    def to_dict(self):
        return {
            'id': self.id,
            'person_uuid': self.person_uuid,
            'test_date': self.test_date.isoformat(),
            'setting': self.setting,
            'result': self.result,
            'prep_given': self.prep_given,
            'cd4_result': self.cd4_result,
            'sti_screening': self.sti_screening
        }

class LamisPlusLite:
    def __init__(self):
        self.patients: List[Patient] = []
        self.hiv_tests: List[HivTesting] = []
        self.logger = self._setup_logger()
        self.load_data()

    def _setup_logger(self):
        logger = logging.getLogger('LamisPlusLite')
        logger.setLevel(logging.INFO)
        handler = logging.FileHandler('lamis_plus_lite.log')
        handler.setFormatter(logging.Formatter('%(asctime)s - %(levelname)s - %(message)s'))
        logger.addHandler(handler)
        return logger

    def add_patient(self):
        try:
            print("\nEnter Patient Details:")
            id = len(self.patients) + 1
            patient_id = f"PAT{id:04d}"
            first_name = input("First Name: ")
            last_name = input("Last Name: ")
            dob = datetime.strptime(input("Date of Birth (YYYY-MM-DD): "), "%Y-%m-%d")
            gender = input("Gender (M/F): ").upper()
            if gender not in ['M', 'F']:
                raise ValueError("Invalid gender")
            education = input("Education Level: ")
            marital_status = input("Marital Status: ")
            address = input("Address: ")
            contact = input("Contact Number: ")
            registration_date = datetime.now()

            patient = Patient(id, patient_id, first_name, last_name, dob, gender,
                            education, marital_status, address, contact, registration_date)
            self.patients.append(patient)
            self.save_data()
            print(f"\nPatient added successfully! ID: {patient_id}")
            
        except ValueError as e:
            print(f"Error: {str(e)}")
            self.logger.error(f"Error adding patient: {str(e)}")

    def add_hiv_test(self):
        try:
            print("\nEnter HIV Test Details:")
            patient_id = input("Patient ID: ")
            patient = next((p for p in self.patients if p.patient_id == patient_id), None)
            if not patient:
                raise ValueError("Patient not found")

            test_id = len(self.hiv_tests) + 1
            setting = input("Test Setting: ")
            result = input("Test Result (Positive/Negative): ")
            if result.lower() not in ['positive', 'negative']:
                raise ValueError("Invalid test result")
            prep_given = input("PrEP Given (Yes/No): ")
            cd4_result = input("CD4 Result: ")
            sti_screening = input("STI Screening Result: ")

            test = HivTesting(test_id, patient.uuid, datetime.now(), setting, 
                            result, prep_given, cd4_result, sti_screening)
            self.hiv_tests.append(test)
            self.save_data()
            print("\nHIV test recorded successfully!")

        except ValueError as e:
            print(f"Error: {str(e)}")
            self.logger.error(f"Error adding HIV test: {str(e)}")

    def view_patient_record(self):
        try:
            patient_id = input("\nEnter Patient ID: ")
            patient = next((p for p in self.patients if p.patient_id == patient_id), None)
            if not patient:
                print("Patient not found")
                return

            print("\n=== Patient Record ===")
            print(f"ID: {patient.patient_id}")
            print(f"Name: {patient.first_name} {patient.last_name}")
            print(f"DOB: {patient.dob.strftime('%Y-%m-%d')}")
            print(f"Gender: {patient.gender}")
            print(f"Education: {patient.education}")
            print(f"Marital Status: {patient.marital_status}")
            print(f"Address: {patient.address}")
            print(f"Contact: {patient.contact}")

            print("\n=== HIV Test History ===")
            tests = [t for t in self.hiv_tests if t.person_uuid == patient.uuid]
            if not tests:
                print("No HIV tests recorded")
                return

            for test in tests:
                print(f"\nTest Date: {test.test_date.strftime('%Y-%m-%d')}")
                print(f"Setting: {test.setting}")
                print(f"Result: {test.result}")
                print(f"PrEP Given: {test.prep_given}")
                print(f"CD4 Result: {test.cd4_result}")
                print(f"STI Screening: {test.sti_screening}")

        except Exception as e:
            print(f"Error: {str(e)}")
            self.logger.error(f"Error viewing patient record: {str(e)}")

    def save_data(self):
        data = {
            'patients': [p.to_dict() for p in self.patients],
            'hiv_tests': [t.to_dict() for t in self.hiv_tests]
        }
        with open('data.json', 'w') as f:
            json.dump(data, f)

    def load_data(self):
        try:
            if os.path.exists('data.json'):
                with open('data.json', 'r') as f:
                    data = json.load(f)
                self.patients = [self._dict_to_patient(p) for p in data['patients']]
                self.hiv_tests = [self._dict_to_test(t) for t in data['hiv_tests']]
        except Exception as e:
            self.logger.error(f"Error loading data: {str(e)}")

    def _dict_to_patient(self, data):
        return Patient(
            id=data['id'],
            patient_id=data['patient_id'],
            first_name=data['first_name'],
            last_name=data['last_name'],
            dob=datetime.fromisoformat(data['dob']),
            gender=data['gender'],
            education=data['education'],
            marital_status=data['marital_status'],
            address=data['address'],
            contact=data['contact'],
            registration_date=datetime.fromisoformat(data['registration_date'])
        )

    def _dict_to_test(self, data):
        return HivTesting(
            id=data['id'],
            person_uuid=data['person_uuid'],
            test_date=datetime.fromisoformat(data['test_date']),
            setting=data['setting'],
            result=data['result'],
            prep_given=data['prep_given'],
            cd4_result=data['cd4_result'],
            sti_screening=data['sti_screening']
        )

def main():
    app = LamisPlusLite()
    while True:
        print("\nLAMISPLUS_LITE")
        print("1. Add Patient")
        print("2. Add HIV Test")
        print("3. View Patient Record")
        print("4. Exit")
        
        try:
            choice = input("\nEnter choice (1-4): ")
            if choice == '1':
                app.add_patient()
            elif choice == '2':
                app.add_hiv_test()
            elif choice == '3':
                app.view_patient_record()
            elif choice == '4':
                print("Exiting...")
                break
            else:
                print("Invalid choice")
        except Exception as e:
            print(f"Error: {str(e)}")

if __name__ == "__main__":
    main()