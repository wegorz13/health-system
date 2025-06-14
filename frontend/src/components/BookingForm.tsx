import { useState } from "react";
import { 
  Box, 
  Stepper,
  Step,
  StepLabel,
  Button,
  CircularProgress,
  Alert
} from "@mui/material";
import AppointmentForm from "./appointment/AppointmentForm";
import type { Doctor } from "../types/doctor";
const steps = ['Select Time', 'Patient Information', 'Review & Confirm'];

interface BookingFormProps {
  doctor: Doctor;
  doctorId: string;
  onBookingStatusChange: (status: {
    loading: boolean;
    success: boolean;
    error: string | null;
  }) => void;
  bookingStatus: {
    loading: boolean;
    success: boolean;
    error: string | null;
  };
  onBookingComplete: () => void;
}

export function BookingForm({ 
  doctor, 
  doctorId, 
  onBookingStatusChange,
  bookingStatus,
  onBookingComplete
}: BookingFormProps) {
  const [activeStep, setActiveStep] = useState(0);
  const [appointmentData, setAppointmentData] = useState({
    doctorId: doctorId,
    hospitalId: '',
    date: '',
    startTime: '',
    endTime: '',
    patientName: '',
    patientEmail: '',
    patientPhone: '',
    reason: ''
  });
  const [isStepValid, setIsStepValid] = useState(false);

  const handleNext = () => {
    setActiveStep((prevStep) => prevStep + 1);
  };

  const handleBack = () => {
    setActiveStep((prevStep) => prevStep - 1);
  };

  const handleFormChange = (data: Partial<typeof appointmentData>) => {
    setAppointmentData(prev => ({ ...prev, ...data }));
  };

  const handleValidationChange = (isValid: boolean) => {
    setIsStepValid(isValid);
  };

  const handleSubmit = async () => {
    onBookingStatusChange({ loading: true, success: false, error: null });

    try {
      // In a real app, you would call an API here to save the appointment
      await new Promise(resolve => setTimeout(resolve, 1500));
      
      onBookingStatusChange({ loading: false, success: true, error: null });
      setTimeout(onBookingComplete, 2000);
    } catch (err) {
      onBookingStatusChange({ 
        loading: false, 
        success: false, 
        error: err instanceof Error ? err.message : 'An error occurred during booking'
      });
    }
  };

  // Show content based on step
  const getStepContent = (step: number) => {
    switch (step) {
      case 0:
        return (
          <AppointmentForm
            doctor={doctor}
            appointmentData={appointmentData}
            onChange={handleFormChange}
            step="time"
            onValidationChange={handleValidationChange}
          />
        );
      case 1:
        return (
          <AppointmentForm
            doctor={doctor}
            appointmentData={appointmentData}
            onChange={handleFormChange}
            step="patient"
            onValidationChange={handleValidationChange}
          />
        );
      case 2:
        return (
          <AppointmentForm
            doctor={doctor}
            appointmentData={appointmentData}
            onChange={handleFormChange}
            step="review"
            onValidationChange={handleValidationChange}
          />
        );
      default:
        return null;
    }
  };

  return (
    <>
      <Stepper activeStep={activeStep} sx={{ pt: 3, pb: 5 }}>
        {steps.map((label) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>

      {getStepContent(activeStep)}

      {bookingStatus.error && (
        <Alert severity="error" sx={{ mt: 2 }}>
          {bookingStatus.error}
        </Alert>
      )}

      <Box sx={{ display: 'flex', justifyContent: 'space-between', mt: 3 }}>
        <Button
          disabled={activeStep === 0 || bookingStatus.loading}
          onClick={handleBack}
        >
          Back
        </Button>
        
        <Box>
          <Button 
            variant="contained"
            onClick={activeStep === steps.length - 1 ? handleSubmit : handleNext}
            disabled={
              bookingStatus.loading || 
              !isStepValid
            }
            sx={{ ml: 1 }}
          >
            {bookingStatus.loading ? (
              <CircularProgress size={24} />
            ) : activeStep === steps.length - 1 ? (
              'Book Appointment'
            ) : (
              'Next'
            )}
          </Button>
        </Box>
      </Box>
    </>
  );
}
