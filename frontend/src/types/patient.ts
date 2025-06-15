import type { Visit } from "./visit";

export interface Patient {
  id: number;
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  dateOfBirth: string;
  gender: string;
  roles: string;
  visits: Visit[];
}
