import type { Doctor } from "../../types/doctor";
import TimeSelectionForm from "./TimeSelectionForm";
import PatientInformationForm from "./PatientInformationForm";
import AppointmentReview from "./AppointmentReview";
import { useHospitals } from "../../hooks/useHospital";

interface AppointmentFormProps {
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
  onChange: (data: Partial<AppointmentFormProps['appointmentData']>) => void;
  step: "time" | "patient" | "review";
  onValidationChange?: (isValid: boolean) => void;
}

export default function AppointmentForm({
  doctor,
  appointmentData,
  onChange,
  step,
  onValidationChange
}: AppointmentFormProps) {
  const { data: hospitals } = useHospitals();

  // Show the appropriate form based on the step
  if (step === "time") {
    return (
      <TimeSelectionForm
        doctor={doctor}
        appointmentData={appointmentData}
        onChange={onChange}
        onValidationChange={onValidationChange}
        hospitals={hospitals || []}
      />
    );
  }

  if (step === "patient") {
    return (
      <PatientInformationForm
        appointmentData={appointmentData}
        onChange={onChange}
        onValidationChange={onValidationChange}
      />
    );
  }

  if (step === "review") {
    return (
      <AppointmentReview
        doctor={doctor}
        appointmentData={appointmentData}
        hospitals={hospitals || []}
      />
    );
  }

  return null;
}
