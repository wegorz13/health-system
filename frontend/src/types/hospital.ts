import type {Doctor} from './doctor';

export interface Hospital {
    id: number;
    name: string;
    address: string;
    doctors: Doctor[];
    phone: string;
    email: string;
}