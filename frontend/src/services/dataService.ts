import type { Doctor } from "../types/doctor";
import type { Hospital } from "../types/hospital";
import type { Patient } from "../types/patient";
import type { Visit } from "../types/visit";
import { mockDoctors, mockHospitals, mockPatients, mockVisits } from "../mock/mockData";
import { APP_CONFIG } from "../config/appConfig";
import type { DataSource } from "../config/appConfig";

// Data source is fixed at build time from environment variables
const dataSource: DataSource = APP_CONFIG.DATA_SOURCE;

// Log the data source on initialization
console.log(`Data source: ${dataSource} mode`);

// Get the current data source (for components that need to know which source is active)
export function getDataSource(): DataSource {
  return dataSource;
}

// Generic fetch function
async function fetchFromApi<T>(endpoint: string): Promise<T> {
  const response = await fetch(`${APP_CONFIG.API_BASE_URL}/${endpoint}`);
  if (!response.ok) {
    throw new Error(`API error: ${response.status}`);
  }
  return await response.json();
}

// Doctor services
export async function getDoctors(): Promise<Doctor[]> {
  if (dataSource === 'mock') {
    return [...mockDoctors];
  }
  return fetchFromApi<Doctor[]>('doctors');
}

export async function getDoctor(id: number | string): Promise<Doctor | null> {
  if (dataSource === 'mock') {
    const doctor = mockDoctors.find(d => d.id === Number(id));
    return doctor ? { ...doctor } : null;
  }
  try {
    return await fetchFromApi<Doctor>(`doctors/${id}`);
  } catch (error) {
    console.error('Error fetching doctor:', error);
    return null;
  }
}

// Hospital services
export async function getHospitals(): Promise<Hospital[]> {
  if (dataSource === 'mock') {
    return [...mockHospitals];
  }
  return fetchFromApi<Hospital[]>('hospitals');
}

export async function getHospital(id: number | string): Promise<Hospital | null> {
  if (dataSource === 'mock') {
    const hospital = mockHospitals.find(h => h.id === Number(id));
    return hospital ? { ...hospital } : null;
  }
  try {
    return await fetchFromApi<Hospital>(`hospitals/${id}`);
  } catch (error) {
    console.error('Error fetching hospital:', error);
    return null;
  }
}

// Patient services
export async function getPatients(): Promise<Patient[]> {
  if (dataSource === 'mock') {
    return [...mockPatients];
  }
  return fetchFromApi<Patient[]>('patients');
}

export async function getPatient(id: number | string): Promise<Patient | null> {
  if (dataSource === 'mock') {
    const patient = mockPatients.find(p => p.id === Number(id));
    return patient ? { ...patient } : null;
  }
  try {
    return await fetchFromApi<Patient>(`patients/${id}`);
  } catch (error) {
    console.error('Error fetching patient:', error);
    return null;
  }
}

// Visit services
export async function getVisits(): Promise<Visit[]> {
  if (dataSource === 'mock') {
    return [...mockVisits];
  }
  return fetchFromApi<Visit[]>('visits');
}

export async function getVisit(id: number | string): Promise<Visit | null> {
  if (dataSource === 'mock') {
    const visit = mockVisits.find(v => v.id === Number(id));
    return visit ? { ...visit } : null;
  }
  try {
    return await fetchFromApi<Visit>(`visits/${id}`);
  } catch (error) {
    console.error('Error fetching visit:', error);
    return null;
  }
}
