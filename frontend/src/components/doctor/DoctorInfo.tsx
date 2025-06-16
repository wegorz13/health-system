import { Box, Typography, Chip, Divider, Button } from "@mui/material";
import { Link } from "react-router-dom";
import type { Doctor } from "../../types/doctor";

interface DoctorInfoProps {
  doctor: Doctor;
}

export default function DoctorInfo({ doctor }: DoctorInfoProps) {
  return (
    <Box>
      <Typography variant="h4" component="h1" gutterBottom>
        {doctor.fullName}
      </Typography>
      
      <Chip 
        label={doctor.specialty} 
        color="primary" 
        variant="outlined" 
        sx={{ mb: 2 }}
      />
      
      {doctor.bio && (
        <Typography variant="body1" paragraph>
          {doctor.bio}
        </Typography>
      )}

      <Divider sx={{ my: 2 }} />
      
      <Box sx={{ mt: 3 }}>
        <Button
          variant="contained"
          color="primary"
          component={Link}
          to={`/book-appointment/${doctor.id}`}
          fullWidth
        >
          Book Appointment
        </Button>
      </Box>
    </Box>
  );
}
