import { getHospital, getHospitals } from '../services/dataService';
import { useAsync } from './useAsync';
import type { Hospital } from '../types/hospital';

/**
 * Hook to fetch a single hospital by ID
 * @param id The hospital ID to fetch
 * @returns Object with hospital data, loading state, and error
 */
export function useHospital(id: string | number | null | undefined) {
  const fetchHospital = async () => {
    if (!id) return null;
    return await getHospital(id);
  };

  return useAsync<Hospital | null>(fetchHospital, [id]);
}

/**
 * Hook to fetch all hospitals
 * @returns Object with hospitals data, loading state, and error
 */
export function useHospitals() {
  return useAsync<Hospital[]>(getHospitals, []);
}
