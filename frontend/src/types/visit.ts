import type { Doctor } from "./doctor";

export interface Visit {
  id: number,
  date: string,
  cost: number,
  prescriptions: string[],
  description: string,
  doctor:Doctor,
}