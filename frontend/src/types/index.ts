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

// Re-export types from other files
export * from './requests';
