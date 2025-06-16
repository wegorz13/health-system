import type { Doctor } from './doctor';

// Updated to match HospitalDTO
export interface Hospital {
    id: number;
    name: string;
    address: string;
    doctors: Doctor[];
    phone: string;
    email: string;
}