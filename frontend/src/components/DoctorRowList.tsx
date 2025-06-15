import { Box, Typography } from "@mui/material";
import DoctorCard from "./DoctorCard";
import { type Doctor } from "../types/doctor";
import { useMemo } from "react";
import { useDoctors } from "../hooks/useDoctor";

interface DoctorsRowList {
  title?: string;
  maxDoctors?: number;
  onSortBy?: () => number;
}

export default function DoctorsRowList({
  title = "Recommended Doctors",
  maxDoctors = 3,
  onSortBy = () => 0.5 - Math.random(),
}: DoctorsRowList) {
  // Fetch all doctors
  const { data: allDoctors, loading, error } = useDoctors();

  // Mock recommendation logic - in a real app, this would use actual recommendation algorithm
  const recommendedDoctors = useMemo(() => {
    if (!allDoctors) return [];

    // For now, just randomly select doctors as "recommended"
    return [...allDoctors].sort(onSortBy).slice(0, maxDoctors);
  }, [allDoctors, maxDoctors,onSortBy]);

  // Don't show anything if there's no data or an error
  if (loading || error || !recommendedDoctors.length) {
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
            sm: recommendedDoctors.length > 1 ? "repeat(2, 1fr)" : "1fr",
            md:
              recommendedDoctors.length > 2
                ? "repeat(3, 1fr)"
                : recommendedDoctors.length > 1
                ? "repeat(2, 1fr)"
                : "1fr",
          },
          gap: 3,
        }}
      >
        {recommendedDoctors.map((doctor) => (
          <Box key={doctor.id} sx={{ height: "100%" }}>
            <DoctorCard doctor={doctor} />
          </Box>
        ))}
      </Box>
    </Box>
  );
}
