import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { 
  Box, 
  Typography, 
  Container,
  Paper,
  Alert
} from "@mui/material";
import { useDoctor } from "../hooks";
import { BookingForm } from "../components/BookingForm";
import { LoadingSpinner } from "../components/ui/LoadingSpinner";

export default function BookAppointmentPage() {
  const { doctorId } = useParams<{ doctorId: string }>();
  const navigate = useNavigate();
  const { data: doctor, loading, error } = useDoctor(doctorId);
  
  const [bookingStatus, setBookingStatus] = useState<{
    loading: boolean;
    success: boolean;
    error: string | null;
  }>({ loading: false, success: false, error: null });

  const handleBookingComplete = async () => {
    navigate(`/my-appointments`);
  };

  if (loading) {
    return (
      <Container maxWidth="md" sx={{ mt: 4 }}>
        <LoadingSpinner />
      </Container>
    );
  }

  if (error || !doctor) {
    return (
      <Container maxWidth="md" sx={{ mt: 4 }}>
        <Alert severity="error">
          {error ? error.message : "Doctor not found"}
        </Alert>
      </Container>
    );
  }
  
  if (bookingStatus.success) {
    return (
      <Container maxWidth="md" sx={{ mt: 4 }}>
        <Alert severity="success">
          Your appointment with Dr. {doctor.fullName} has been successfully booked! Redirecting...
        </Alert>
      </Container>
    );
  }

  return (
    <Container maxWidth="md" sx={{ mt: 4, mb: 6 }}>
      <Paper sx={{ p: { xs: 2, md: 4 } }}>
        <Typography component="h1" variant="h4" align="center" gutterBottom>
          Book an Appointment
        </Typography>
        <Typography variant="h6" align="center" color="text.secondary" paragraph>
          with Dr. {doctor.fullName}
        </Typography>

        <BookingForm 
          doctor={doctor}
          doctorId={doctorId || ''}
          onBookingStatusChange={setBookingStatus}
          bookingStatus={bookingStatus}
          onBookingComplete={handleBookingComplete}
        />
      </Paper>
    </Container>
  );
}
