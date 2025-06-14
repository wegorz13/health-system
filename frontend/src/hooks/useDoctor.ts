import { getDoctor, getDoctors } from '../services/dataService';
import { useAsync } from './useAsync';
import type { Doctor } from '../types/doctor';

/**
 * Hook to fetch a single doctor by ID
 * @param id The doctor ID to fetch
 * @returns Object with doctor data, loading state, and error
 */
export function useDoctor(id: string | number | null | undefined) {
  const fetchDoctor = async () => {
    if (!id) return null;
    return await getDoctor(id);
  };

  return useAsync<Doctor | null>(fetchDoctor, [id]);
}

/**
 * Hook to fetch all doctors
 * @returns Object with doctors data, loading state, and error
 */
export function useDoctors() {
  return useAsync<Doctor[]>(getDoctors, []);
}
