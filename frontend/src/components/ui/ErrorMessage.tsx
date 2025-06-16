import { Alert, Box } from "@mui/material";

interface ErrorMessageProps {
  message: string;
  severity?: "error" | "warning" | "info" | "success";
}

export function ErrorMessage({ message, severity = "error" }: ErrorMessageProps) {
  return (
    <Box sx={{ my: 3, mx: 'auto', maxWidth: '600px' }}>
      <Alert severity={severity}>
        {message}
      </Alert>
    </Box>
  );
}
