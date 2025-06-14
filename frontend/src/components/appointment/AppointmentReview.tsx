import { useEffect } from "react";
import {
  Box,
  Typography,
  Paper,
  List,
  ListItem,
  ListItemText,
} from "@mui/material";
import { format, parseISO } from 'date-fns';
import type { Doctor } from "../../types/doctor";

interface AppointmentReviewProps {
  doctor: Doctor;
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
  hospitals: Array<{ id: string; name: string; address: string }>;
  onValidationChange?: (isValid: boolean) => void;
}

export default function AppointmentReview({
  doctor,
  appointmentData,
  hospitals,
  onValidationChange
}: AppointmentReviewProps) {
  const selectedHospital = hospitals?.find(h => h.id === appointmentData.hospitalId);
  
  // Always validate as true for review step
  useEffect(() => {
    onValidationChange?.(true);
  }, [onValidationChange]);

  return (
    <Box>
      <Typography variant="h6" gutterBottom>
        Review Appointment Details
      </Typography>

      <Paper variant="outlined" sx={{ p: 2, mb: 3 }}>
        <List disablePadding>
          <ListItem sx={{ py: 1, px: 0 }}>
            <ListItemText 
              primary="Doctor" 
              secondary={doctor.fullName} 
            />
          </ListItem>
          
          <ListItem sx={{ py: 1, px: 0 }}>
            <ListItemText 
              primary="Specialty" 
              secondary={doctor.specialty} 
            />
          </ListItem>
          
          <ListItem sx={{ py: 1, px: 0 }}>
            <ListItemText 
              primary="Hospital" 
              secondary={selectedHospital?.name} 
            />
          </ListItem>
          
          <ListItem sx={{ py: 1, px: 0 }}>
            <ListItemText 
              primary="Address" 
              secondary={selectedHospital?.address} 
            />
          </ListItem>
          
          <ListItem sx={{ py: 1, px: 0 }}>
            <ListItemText 
              primary="Date & Time" 
              secondary={`${format(parseISO(appointmentData.date), 'MMMM d, yyyy')} at ${appointmentData.startTime} - ${appointmentData.endTime}`} 
            />
          </ListItem>
        </List>
      </Paper>

      <Typography variant="h6" gutterBottom>
        Patient Information
      </Typography>

      <Paper variant="outlined" sx={{ p: 2 }}>
        <List disablePadding>
          <ListItem sx={{ py: 1, px: 0 }}>
            <ListItemText 
              primary="Name" 
              secondary={appointmentData.patientName || "Not provided"} 
            />
          </ListItem>
          
          <ListItem sx={{ py: 1, px: 0 }}>
            <ListItemText 
              primary="Email" 
              secondary={appointmentData.patientEmail || "Not provided"} 
            />
          </ListItem>
          
          <ListItem sx={{ py: 1, px: 0 }}>
            <ListItemText 
              primary="Phone" 
              secondary={appointmentData.patientPhone || "Not provided"} 
            />
          </ListItem>
          
          <ListItem sx={{ py: 1, px: 0 }}>
            <ListItemText 
              primary="Reason for Visit" 
              secondary={appointmentData.reason || "Not provided"} 
            />
          </ListItem>
        </List>
      </Paper>
    </Box>
  );
}
