import { Box, Typography } from "@mui/material";
import DoctorCard from "./DoctorCard";
import { type Doctor } from "../types/doctor";

interface DoctorCardListProps {
  title?: string;
  doctors: Doctor[];
  emptyMessage?: string;
}

/**
 * A simplified DoctorCardList component that only handles presentation
 * All data fetching and filtering logic is now in the DoctorSearchAndList component
 */
export default function DoctorCardList({
  title,
  doctors,
  emptyMessage = "No doctors found"
}: DoctorCardListProps) {
  // Simple empty state
  if (!doctors?.length) {
    return (
      <Box sx={{ textAlign: "center", py: 3 }}>
        <Typography variant="h6" color="text.secondary">
          {emptyMessage}
        </Typography>
      </Box>
    );
  }

  return (
    <Box sx={{ py: 2 }}>
      {title && (
        <Typography variant="h5" sx={{ mb: 3 }}>
          {title}
        </Typography>
      )}
      
      <Box sx={{ 
        display: 'flex', 
        flexWrap: 'wrap',
        gap: 3,
      }}>
        {doctors.map((doctor) => (
          <Box 
            key={doctor.id} 
            sx={{ 
              width: { 
                xs: '100%', 
                sm: 'calc(50% - 12px)', 
                md: 'calc(33.33% - 16px)' 
              } 
            }}
          >
            <DoctorCard doctor={doctor} />
          </Box>
        ))}
      </Box>
    </Box>
  );
}
