// Export all common types from here

export interface Doctor {
  id: string;
  fullName: string;
  // Add other doctor properties here
}

export interface AppointmentData {
  doctorId: string;
  hospitalId: string;
  date: string;
  startTime: string;
  endTime: string;
  patientName: string;
  patientEmail: string;
  patientPhone: string;
  reason: string;
}

export interface BookingStatus {
  loading: boolean;
  success: boolean;
  error: string | null;
}
