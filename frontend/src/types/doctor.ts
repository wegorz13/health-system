import type {Hospital} from "./hospital.ts";

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