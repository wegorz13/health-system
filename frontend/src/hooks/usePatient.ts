import { getPatient, getPatients } from '../services/dataService';
import { useAsync } from './useAsync';
import type { Patient } from '../types/patient';

/**
 * Hook to fetch a single patient by ID
 * @param id The patient ID to fetch
 * @returns Object with patient data, loading state, and error
 */
export function usePatient(id: string | number | null | undefined) {
  const fetchPatient = async () => {
    if (!id) return null;
    return await getPatient(id);
  };

  return useAsync<Patient | null>(fetchPatient, [id]);
}

/**
 * Hook to fetch all patients
 * @returns Object with patients data, loading state, and error
 */
export function usePatients() {
  return useAsync<Patient[]>(getPatients, []);
}
