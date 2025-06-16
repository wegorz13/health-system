import { Box, Typography, CircularProgress } from "@mui/material";
import DoctorCard from "./DoctorCard";
import { type Doctor } from "../types/doctor";
import { useState, useEffect } from "react";
import { getRecommendedDoctors, getRelativeRecommendedDoctors } from "../services/dataService";

interface DoctorsRowListProps {
  title?: string;
  maxDoctors?: number;
  type: "best" | "relative";
  patientId?: number | string;
}

export default function DoctorsRowList({
  title,
  maxDoctors = 3,
  type,
  patientId
}: DoctorsRowListProps) {
  const [doctors, setDoctors] = useState<Doctor[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    async function fetchDoctors() {
      try {
        setLoading(true);
        let fetchedDoctors: Doctor[];
        
        if (type === "best") {
          // Fetch best recommended doctors
          fetchedDoctors = await getRecommendedDoctors();
        } else if (type === "relative" && patientId) {
          // Fetch doctors recommended based on relatives
          fetchedDoctors = await getRelativeRecommendedDoctors(patientId);
        } else {
          // Default case - return empty array
          fetchedDoctors = [];
        }
        
        setDoctors(fetchedDoctors.slice(0, maxDoctors));
      } catch (err) {
        setError(err instanceof Error ? err : new Error('Failed to fetch doctors'));
      } finally {
        setLoading(false);
      }
    }
    
    fetchDoctors();
  }, [type, patientId, maxDoctors]);

  // Show loading indicator
  if (loading) {
    return (
      <Box sx={{ py: 4, textAlign: 'center' }}>
        <CircularProgress size={40} />
      </Box>
    );
  }

  // Show error message
  if (error) {
    console.error('Error loading doctors:', error);
    return null; // Hide component on error
  }

  // Don't show anything if there's no data
  if (!doctors.length) {
    return null;
  }

  return (
    <Box sx={{ py: 4 }}>
      <Typography variant="h4" sx={{ mb: 3 }}>
        {title}
      </Typography>

      <Box
        sx={{
          display: "grid",
          gridTemplateColumns: {
            xs: "1fr",
            sm: doctors.length > 1 ? "repeat(2, 1fr)" : "1fr",
            md:
              doctors.length > 2
                ? "repeat(3, 1fr)"
                : doctors.length > 1
                ? "repeat(2, 1fr)"
                : "1fr",
          },
          gap: 3,
        }}
      >
        {doctors.map((doctor) => (
          <Box key={doctor.id} sx={{ height: "100%" }}>
            <DoctorCard doctor={doctor} />
          </Box>
        ))}
      </Box>
    </Box>
  );
}
