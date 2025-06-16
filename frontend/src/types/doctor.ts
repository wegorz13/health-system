import type { Hospital } from "./hospital.ts";

// Update to match DoctorDTO from Java backend
export interface Doctor {
  id: number;
  fullName: string;
  specialty: string;
  workingHours: {
    hospital: Hospital;
    dailyHours: {
      monday?: string[];
      tuesday?: string[];
      wednesday?: string[];
      thursday?: string[];
      friday?: string[];
      saturday?: string[];
      sunday?: string[];
    };
  }[];
}

// Add WorkDaySchedule interface to match WorkDayScheduleDTO
export interface WorkDaySchedule {
  startTime: string;
  endTime: string;
  hospitalName: string;
}