import type {Hospital} from "./hospital.ts";

export interface Doctor{
    id: number;
    fullName: string;
    specialty: string;
    workingHours: {
        hospital: Hospital;
        hours: string[];
    }[];
}