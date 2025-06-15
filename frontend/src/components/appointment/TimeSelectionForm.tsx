import { useState, useEffect } from "react";
import {
  Box,
  TextField,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Typography,
  Grid,
  FormHelperText,
  RadioGroup,
  FormControlLabel,
  Radio,
  FormLabel,
} from "@mui/material";
import { format, addDays, parseISO } from 'date-fns';
import type { Doctor } from "../../types/doctor";

interface TimeSelectionFormProps {
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
  onChange: (data: Partial<TimeSelectionFormProps['appointmentData']>) => void;
  onValidationChange?: (isValid: boolean) => void;
  hospitals: Array<{ id: string; name: string; address: string }>;
}

export default function TimeSelectionForm({
  doctor,
  appointmentData,
  onChange,
  onValidationChange,
  hospitals
}: TimeSelectionFormProps) {
  const [availableTimes, setAvailableTimes] = useState<{ start: string; end: string }[]>([]);
  const [errors, setErrors] = useState({
    hospitalId: false,
    date: false,
    startTime: false
  });

  // Filter hospitals where the doctor works
  const doctorHospitals = hospitals?.filter(hospital =>
    doctor.workingHours.some(wh => wh.hospital.id === hospital.id)
  ) || [];

  // Validate form fields and notify parent component
  useEffect(() => {
    const hospitalError = !appointmentData.hospitalId;
    const dateError = !appointmentData.date;
    const timeError = !appointmentData.startTime;
    
    setErrors({
      hospitalId: hospitalError,
      date: dateError,
      startTime: timeError
    });
    
    const isValid = !hospitalError && !dateError && !timeError;
    onValidationChange?.(isValid);
  }, [appointmentData.hospitalId, appointmentData.date, appointmentData.startTime, onValidationChange]);

  // Generate time slots based on doctor's working hours
  useEffect(() => {
    if (!appointmentData.hospitalId || !appointmentData.date) {
      setAvailableTimes([]);
      return;
    }

    // Get the working hours for the selected hospital
    const selectedDate = parseISO(appointmentData.date);
    const dayOfWeek = format(selectedDate, 'EEEE').toLowerCase();
    
    // Find the working hours for the selected hospital
    const workingHoursForHospital = doctor.workingHours.find(wh => 
      wh.hospital.id === appointmentData.hospitalId
    );

    // Add this check to prevent TypeError
    if (!workingHoursForHospital || !workingHoursForHospital.dailyHours) {
      setAvailableTimes([]);
      return;
    }

    // Generate 30-minute time slots for the day
    const slots: { start: string; end: string }[] = [];
    const availableHours = workingHoursForHospital.dailyHours[dayOfWeek as keyof typeof workingHoursForHospital.dailyHours];
    
    if (availableHours) {
      availableHours.forEach(timeRange => {
        const [startTime, endTime] = timeRange.split('-');
        const startHour = parseInt(startTime.split(':')[0]);
        const endHour = parseInt(endTime.split(':')[0]);
        
        for (let hour = startHour; hour < endHour; hour++) {
          slots.push({
            start: `${hour.toString().padStart(2, '0')}:00`,
            end: `${hour.toString().padStart(2, '0')}:30`
          });
          slots.push({
            start: `${hour.toString().padStart(2, '0')}:30`,
            end: `${(hour + 1).toString().padStart(2, '0')}:00`
          });
        }
      });
    }
    
    setAvailableTimes(slots);
  }, [appointmentData.hospitalId, appointmentData.date, doctor.workingHours]);

  // Add this function to check if a date has working hours
  const isDateAvailable = (date: Date) => {
    if (!appointmentData.hospitalId) return false;
    
    const workingHoursForHospital = doctor.workingHours.find(
      (wh) => wh.hospital.id === appointmentData.hospitalId
    );
    
    if (!workingHoursForHospital || !workingHoursForHospital.dailyHours) return false;
    
    const dayOfWeek = date.getDay();
    return workingHoursForHospital.dailyHours[dayOfWeek]?.length > 0;
  };

  return (
    <Box>
      <Typography variant="h6" gutterBottom>
        Select Appointment Details
      </Typography>

      <Grid container spacing={3}>
        <Grid item xs={12}>
          <FormControl 
            fullWidth 
            required 
            error={errors.hospitalId}
          >
            <InputLabel>Hospital</InputLabel>
            <Select
              value={appointmentData.hospitalId}
              label="Hospital"
              onChange={(e) => onChange({ hospitalId: e.target.value })}
            >
              {doctorHospitals.map((hospital) => (
                <MenuItem key={hospital.id} value={hospital.id}>
                  {hospital.name} - {hospital.address}
                </MenuItem>
              ))}
            </Select>
            <FormHelperText>
              {errors.hospitalId ? "Hospital selection is required" : "Select where you want to meet the doctor"}
            </FormHelperText>
          </FormControl>
        </Grid>

        <Grid item xs={12}>
          <TextField
            label="Appointment Date"
            type="date"
            fullWidth
            required
            error={errors.date}
            value={appointmentData.date}
            onChange={(e) => {
              onChange({ 
                date: e.target.value,
                startTime: '',
                endTime: '' 
              });
            }}
            InputLabelProps={{
              shrink: true,
            }}
            inputProps={{
              min: format(new Date(), 'yyyy-MM-dd'),
              max: format(addDays(new Date(), 30), 'yyyy-MM-dd')
            }}
            helperText={errors.date ? "Date selection is required" : "Choose a date within the next 30 days"}
          />
        </Grid>

        {appointmentData.date && appointmentData.hospitalId && (
          <Grid item xs={12}>
            <FormControl 
              component="fieldset" 
              required
              error={errors.startTime}
            >
              <FormLabel component="legend">Available Time Slots</FormLabel>
              
              {availableTimes.length === 0 ? (
                <Typography color="error" sx={{ mt: 2 }}>
                  No available time slots for this date and location.
                </Typography>
              ) : (
                <RadioGroup
                  value={appointmentData.startTime}
                  onChange={(e) => {
                    const selectedSlot = availableTimes.find(
                      slot => slot.start === e.target.value
                    );
                    if (selectedSlot) {
                      onChange({ 
                        startTime: selectedSlot.start, 
                        endTime: selectedSlot.end 
                      });
                    }
                  }}
                >
                  <Grid container spacing={1} sx={{ mt: 1 }}>
                    {availableTimes.map((slot, index) => (
                      <Grid item xs={6} sm={4} md={3} key={index}>
                        <FormControlLabel
                          value={slot.start}
                          control={<Radio />}
                          label={`${slot.start} - ${slot.end}`}
                        />
                      </Grid>
                    ))}
                  </Grid>
                </RadioGroup>
              )}
              {errors.startTime && (
                <FormHelperText error>Time selection is required</FormHelperText>
              )}
            </FormControl>
          </Grid>
        )}
      </Grid>
    </Box>
  );
}
