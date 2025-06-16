export interface CreateDoctorRequest {
  firstName: string;
  lastName: string;
  specialty: string;
}

export interface CreateWorkDayScheduleRequest {
  hospitalId: number;
  dayOfWeek: string; // DayOfWeek enum value: "MONDAY", "TUESDAY", etc.
  startTime: string; // LocalTime format: "HH:MM:SS"
  endTime: string;   // LocalTime format: "HH:MM:SS"
}

export interface CreateHospitalRequest {
  name: string;
  address: string;
  email: string;
  phone: string;
}

export interface CreatePatientRequest {
  firstName: string;
  lastName: string;
  dateOfBirth: string; // Format: YYYY-MM-DD
  username: string;
  password: string;
  roles: string;
  email: string;
  gender: string;
}

export interface CreateVisitRequest {
  date: string;       // LocalDateTime format
  cost: number;
  prescriptions: string[];
  patientsCondition: string;
  patientId: number;
  doctorId: number;
}
