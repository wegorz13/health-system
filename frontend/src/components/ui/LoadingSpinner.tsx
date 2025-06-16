import { Box, CircularProgress, Typography } from "@mui/material";

interface LoadingSpinnerProps {
  message?: string;
}

export function LoadingSpinner({ message = "Loading..." }: LoadingSpinnerProps) {
  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        minHeight: "200px",
      }}
    >
      <CircularProgress size={40} />
      {message && (
        <Typography variant="body1" sx={{ mt: 2 }}>
          {message}
        </Typography>
      )}
    </Box>
  );
}
