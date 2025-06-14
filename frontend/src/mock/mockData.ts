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

// Mock Doctors
export const mockDoctors: Doctor[] = [  {
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

// Update hospital doctors references
mockHospitals[0].doctors = [mockDoctors[0], mockDoctors[1], mockDoctors[4]];
mockHospitals[1].doctors = [mockDoctors[0], mockDoctors[2], mockDoctors[4]];
mockHospitals[2].doctors = [mockDoctors[2], mockDoctors[3]];

// Mock Visits
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
  }
];
