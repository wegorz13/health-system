import type { Visit } from "./visit";

// Updated to match PatientDTO
export interface Patient {
  id: number;
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  dateOfBirth: string;
  gender: string;
  roles: string;
  visits?: Visit[]; // Made optional since it's not in PatientDTO
}
