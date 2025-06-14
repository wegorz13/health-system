import { Box, CircularProgress } from "@mui/material";

interface LoadingSpinnerProps {
  size?: number;
}

export function LoadingSpinner({ size = 40 }: LoadingSpinnerProps) {
  return (
    <Box display="flex" justifyContent="center" my={4}>
      <CircularProgress size={size} />
    </Box>
  );
}
