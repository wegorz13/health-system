import { useEffect } from "react";
import {
  Box,
  TextField,
  Typography,
  Grid,
} from "@mui/material";

interface PatientInformationFormProps {
  appointmentData: {
    doctorId: string;
    hospitalId: string;
    date: string;
    startTime: string;
    endTime: string;
    patientName: string;
    patientEmail: string;
    patientPhone: string;
    reason: string;
  };
  onChange: (data: Partial<PatientInformationFormProps['appointmentData']>) => void;
  onValidationChange?: (isValid: boolean) => void;
}

export default function PatientInformationForm({
  appointmentData,
  onChange,
  onValidationChange
}: PatientInformationFormProps) {
  // Validate patient information
  useEffect(() => {
    const isValid = Boolean(
      appointmentData.patientName &&
      appointmentData.patientEmail &&
      appointmentData.patientPhone
    );
    
    onValidationChange?.(isValid);
  }, [
    appointmentData.patientName,
    appointmentData.patientEmail,
    appointmentData.patientPhone,
    onValidationChange
  ]);

  return (
    <Box>
      <Typography variant="h6" gutterBottom>
        Your Information
      </Typography>

      <Grid container spacing={3}>
        <Grid item xs={12}>
          <TextField
            required
            fullWidth
            label="Full Name"
            value={appointmentData.patientName}
            onChange={(e) => onChange({ patientName: e.target.value })}
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            required
            fullWidth
            label="Email"
            type="email"
            value={appointmentData.patientEmail}
            onChange={(e) => onChange({ patientEmail: e.target.value })}
          />
        </Grid>

        <Grid item xs={12} sm={6}>
          <TextField
            required
            fullWidth
            label="Phone Number"
            value={appointmentData.patientPhone}
            onChange={(e) => onChange({ patientPhone: e.target.value })}
          />
        </Grid>

        <Grid item xs={12}>
          <TextField
            fullWidth
            label="Reason for Visit"
            multiline
            rows={4}
            value={appointmentData.reason}
            onChange={(e) => onChange({ reason: e.target.value })}
            helperText="Please briefly describe your symptoms or reason for the appointment"
          />
        </Grid>
      </Grid>
    </Box>
  );
}
