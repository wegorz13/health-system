import type { Doctor } from "../types/doctor";
import type { Hospital } from "../types/hospital";
import type { Patient } from "../types/patient";
import type { Visit } from "../types/visit";

// Mock Hospitals
export const mockHospitals: Hospital[] = [
  {
    id: 1,
    name: "Central City Hospital",
    address: "123 Main St, Central City",
    phone: "+48 123 456 789",
    email: "contact@centralcityhospital.com",
    doctors: [] // Will be populated after defining doctors
  },
  {
    id: 2,
    name: "Riverside Medical Center",
    address: "456 River Road, Riverside",
    phone: "+48 987 654 321",
    email: "info@riversidemedical.com",
    doctors: [] // Will be populated after defining doctors
  },
  {
    id: 3,
    name: "St. Mary's Hospital",
    address: "789 Oak Avenue, Oakville",
    phone: "+48 555 123 456",
    email: "reception@stmarys.com",
    doctors: [] // Will be populated after defining doctors
  }
];

// Mock Doctors - renamed to baseDoctors to avoid naming conflict
const baseDoctors: Doctor[] = [
  {
    id: 1,
    fullName: "Dr. Jan Kowalski",
    specialty: "Cardiology",
    workingHours: [
      {
        hospital: mockHospitals[0],
        dailyHours: {
          monday: ["8:00-12:00", "13:00-16:00"],
          wednesday: ["9:00-13:00"],
          friday: ["8:00-12:00"]
        }
      },
      {
        hospital: mockHospitals[1],
        dailyHours: {
          tuesday: ["10:00-14:00"],
          thursday: ["11:00-15:00"]
        }
      }
    ]
  },
  {
    id: 2,
    fullName: "Dr. Anna Nowak",
    specialty: "Pediatrics",
    workingHours: [
      {
        hospital: mockHospitals[0],
        hours: ["9:00-17:00"]
      }
    ]
  },
  {
    id: 3,
    fullName: "Dr. Piotr Wiśniewski",
    specialty: "Orthopedics",
    workingHours: [
      {
        hospital: mockHospitals[1],
        hours: ["8:00-16:00"]
      },
      {
        hospital: mockHospitals[2],
        hours: ["10:00-14:00", "15:00-18:00"]
      }
    ]
  },
  {
    id: 4,
    fullName: "Dr. Maria Dąbrowska",
    specialty: "Neurology",
    workingHours: [
      {
        hospital: mockHospitals[2],
        hours: ["7:00-15:00"]
      }
    ]
  },
  {
    id: 5,
    fullName: "Dr. Tomasz Lewandowski",
    specialty: "Dermatology",
    workingHours: [
      {
        hospital: mockHospitals[0],
        hours: ["12:00-20:00"]
      },
      {
        hospital: mockHospitals[1],
        hours: ["8:00-12:00"]
      }
    ]
  }
];

// Update hospital doctors references - now referencing mockDoctors which we'll define later
mockHospitals[0].doctors = [];  // These will be populated after mockDoctors is defined
mockHospitals[1].doctors = [];
mockHospitals[2].doctors = [];

// Define specialty descriptions for improved doctor information
export const specialtyDescriptions = {
  "Cardiology": "Specializes in diagnosing and treating diseases or conditions of the heart and blood vessels.",
  "Pediatrics": "Focuses on the health and medical care of infants, children, and adolescents.",
  "Orthopedics": "Concentrates on the diagnosis, correction, prevention, and treatment of patients with skeletal deformities.",
  "Neurology": "Deals with the diagnosis and treatment of all categories of conditions and disease involving the brain and nervous system.",
  "Dermatology": "Specializes in the diagnosis, treatment, and prevention of skin, hair, nail, and mucous membrane disorders."
};

// Add bios to doctors for more detailed information
export const mockDoctors: Doctor[] = baseDoctors.map(doctor => ({
  ...doctor,
  bio: `Dr. ${doctor.fullName.split(' ')[1]} is a highly qualified ${doctor.specialty.toLowerCase()} specialist with over ${Math.floor(Math.random() * 15) + 5} years of experience. ${Math.random() > 0.5 ? 'They graduated with honors from the Medical University of Warsaw.' : 'They completed their medical training at Jagiellonian University Medical College.'} They are known for their ${['compassionate', 'thorough', 'patient-centered', 'innovative', 'detail-oriented'][Math.floor(Math.random() * 5)]} approach to healthcare.`
}));

// Now populate the hospital doctors using the enhanced mockDoctors
mockHospitals[0].doctors = [mockDoctors[0], mockDoctors[1], mockDoctors[4]];
mockHospitals[1].doctors = [mockDoctors[0], mockDoctors[2], mockDoctors[4]];
mockHospitals[2].doctors = [mockDoctors[2], mockDoctors[3]];

// Mock Visits - update to use the mockDoctors (with bios) rather than the base doctors
export const mockVisits: Visit[] = [
  {
    id: 1,
    date: "2025-06-10T10:30:00",
    cost: 150.0,
    prescriptions: ["Amlodipine 5mg", "Aspirin 100mg"],
    description: "Regular blood pressure check. Patient shows improvement.",
    doctor: mockDoctors[0]
  },
  {
    id: 2,
    date: "2025-06-12T14:00:00",
    cost: 200.0,
    prescriptions: ["Amoxicillin 500mg"],
    description: "Ear infection. Prescribed antibiotics for 7 days.",
    doctor: mockDoctors[1]
  },
  {
    id: 3,
    date: "2025-06-07T09:15:00",
    cost: 300.0,
    prescriptions: ["Ibuprofen 400mg", "Physical therapy"],
    description: "Knee pain assessment. MRI scheduled for next week.",
    doctor: mockDoctors[2]
  },
  {
    id: 4,
    date: "2025-06-15T11:00:00",
    cost: 250.0,
    prescriptions: ["Sumatriptan 50mg"],
    description: "Recurring migraines. Prescribed new medication.",
    doctor: mockDoctors[3]
  },
  {
    id: 5,
    date: "2025-06-08T16:30:00",
    cost: 180.0,
    prescriptions: ["Hydrocortisone cream 1%", "Cetirizine 10mg"],
    description: "Skin rash assessment. Likely allergic reaction.",
    doctor: mockDoctors[4]
  }
];

// Mock Patients
export const mockPatients: Patient[] = [
  {
    id: 1,
    firstName: "Adam",
    lastName: "Malinowski",
    username: "amalinowski",
    email: "adam.malinowski@example.com",
    dateOfBirth: "1980-03-15",
    gender: "Male",
    roles: "PATIENT",
    visits: [mockVisits[0], mockVisits[4]]
  },
  {
    id: 2,
    firstName: "Ewa",
    lastName: "Zielińska",
    username: "ezielinska",
    email: "ewa.zielinska@example.com",
    dateOfBirth: "1992-07-22",
    gender: "Female",
    roles: "PATIENT",
    visits: [mockVisits[1]]
  },
  {
    id: 3,
    firstName: "Michał",
    lastName: "Kwiatkowski",
    username: "mkwiatkowski",
    email: "michal.kwiatkowski@example.com",
    dateOfBirth: "1975-11-08",
    gender: "Male",
    roles: "PATIENT",
    visits: [mockVisits[2]]
  },
  {
    id: 4,
    firstName: "Karolina",
    lastName: "Szymańska",
    username: "kszymanska",
    email: "karolina.szymanska@example.com",
    dateOfBirth: "1988-05-30",
    gender: "Female",
    roles: "PATIENT",
    visits: [mockVisits[3]]
  },
  {
    id: 5,
    firstName: "Paweł",
    lastName: "Jankowski",
    username: "pjankowski",
    email: "pawel.jankowski@example.com",
    dateOfBirth: "1965-09-12",
    gender: "Male",
    roles: "PATIENT",
    visits: []
  },
  {
    id: 6,
    firstName: "Magdalena",
    lastName: "Kowalczyk",
    username: "mkowalczyk",
    email: "magdalena.kowalczyk@example.com",
    dateOfBirth: "1982-04-18",
    gender: "Female",
    roles: "PATIENT",
    visits: []
  },
  {
    id: 7,
    firstName: "Krzysztof",
    lastName: "Wojciechowski",
    username: "kwojciechowski",
    email: "krzysztof.wojciechowski@example.com",
    dateOfBirth: "1979-12-03",
    gender: "Male",
    roles: "PATIENT",
    visits: []
  }
];

// Define family relationships for recommendations
export const mockFamilyRelationships = [
  { patientId: 1, relativeIds: [2, 6] },  // Adam is related to Ewa and Magdalena
  { patientId: 3, relativeIds: [4, 7] },  // Michał is related to Karolina and Krzysztof
  { patientId: 5, relativeIds: [6] }      // Paweł is related to Magdalena
];

// Mock ratings for doctors (for recommendations)
export const mockDoctorRatings = [
  { doctorId: 1, averageRating: 4.8, numberOfRatings: 124 },
  { doctorId: 2, averageRating: 4.6, numberOfRatings: 89 },
  { doctorId: 3, averageRating: 4.9, numberOfRatings: 156 },
  { doctorId: 4, averageRating: 4.7, numberOfRatings: 102 },
  { doctorId: 5, averageRating: 4.5, numberOfRatings: 78 }
];
