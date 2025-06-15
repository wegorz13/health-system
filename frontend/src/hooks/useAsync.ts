import { useState, useEffect } from 'react';

/**
 * Simple hook for handling async data fetching with loading and error states
 * @param asyncFn The async function to execute
 * @param deps Dependencies array that will trigger the function to re-run
 * @returns Object with data, loading state, and error
 */
export function useAsync<T>(asyncFn: () => Promise<T>, deps: any[] = []) {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    let isMounted = true;
    setLoading(true);
    
    asyncFn()
      .then(result => {
        if (isMounted) {
          setData(result);
          setError(null);
        }
      })
      .catch(err => {
        if (isMounted) {
          setError(err instanceof Error ? err : new Error(String(err)));
          setData(null);
        }
      })
      .finally(() => {
        if (isMounted) {
          setLoading(false);
        }
      });

    return () => {
      isMounted = false;
    };
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, deps);

  return { data, loading, error };
}
