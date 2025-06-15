import { getVisit, getVisits } from '../services/dataService';
import { useAsync } from './useAsync';
import type { Visit } from '../types/visit';

/**
 * Hook to fetch a single visit by ID
 * @param id The visit ID to fetch
 * @returns Object with visit data, loading state, and error
 */
export function useVisit(id: string | number | null | undefined) {
  const fetchVisit = async () => {
    if (!id) return null;
    return await getVisit(id);
  };

  return useAsync<Visit | null>(fetchVisit, [id]);
}

/**
 * Hook to fetch all visits
 * @returns Object with visits data, loading state, and error
 */
export function useVisits() {
  return useAsync<Visit[]>(getVisits, []);
}
