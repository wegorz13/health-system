import { useState, useMemo } from "react";
import { 
  Box, 
  Typography, 
  CircularProgress
} from "@mui/material";
import DoctorCardList from "../DoctorCardList";
import DoctorSearchBar from "./DoctorSearchBar";
import type { DoctorSearchFilters } from "./DoctorSearchBar";
import { useDoctors } from "../../hooks/useDoctor";
import { useHospitals } from "../../hooks/useHospital";

interface DoctorSearchProps {
  title?: string;
}

export default function DoctorSearchAndList({ 
  title = "Our Doctors"
}: DoctorSearchProps) {
  // Get doctors and hospitals data
  const { data: doctors, loading, error } = useDoctors();
  const { data: hospitals } = useHospitals();
  
  // State for filtering
  const [filters, setFilters] = useState<DoctorSearchFilters>({
    searchTerm: "",
    selectedCity: null,
    selectedSpecialty: null,
  });
  
  // Extract unique cities and specialties
  const cities = useMemo(() => {
    if (!hospitals) return [];
    return [...new Set(hospitals.map(hospital => {
      const addressParts = hospital.address.split(',');
      return addressParts.length > 1 
        ? addressParts[addressParts.length - 1].trim() 
        : hospital.address.trim();
    }))];
  }, [hospitals]);
  
  const specialties = useMemo(() => {
    if (!doctors) return [];
    return [...new Set(doctors.map(doctor => doctor.specialty))];
  }, [doctors]);
  
  // Filter doctors based on criteria
  const filteredDoctors = useMemo(() => {
    if (!doctors) return [];
    
    return doctors.filter(doctor => {
      // Name filter
      if (filters.searchTerm && 
          !doctor.fullName.toLowerCase().includes(filters.searchTerm.toLowerCase())) {
        return false;
      }
      
      // Specialty filter
      if (filters.selectedSpecialty && doctor.specialty !== filters.selectedSpecialty) {
        return false;
      }
      
      // City filter
      if (filters.selectedCity && hospitals) {
        const worksInCity = doctor.workingHours.some(wh => {
          const hospital = hospitals.find(h => h.id === wh.hospital.id);
          if (!hospital) return false;
          
          const addressParts = hospital.address.split(',');
          const hospitalCity = addressParts.length > 1 
            ? addressParts[addressParts.length - 1].trim() 
            : hospital.address.trim();
            
          return hospitalCity === filters.selectedCity;
        });
        
        if (!worksInCity) return false;
      }
      
      return true;
    });
  }, [doctors, hospitals, filters]);
  
  // Create appropriate empty message
  const emptyMessage = filters.selectedCity 
    ? `No doctors found in ${filters.selectedCity}` 
    : filters.searchTerm 
      ? `No doctors found matching "${filters.searchTerm}"`
      : filters.selectedSpecialty
        ? `No doctors found with specialty "${filters.selectedSpecialty}"`
        : "No doctors found";
  
  if (loading) {
    return (
      <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", minHeight: "200px" }}>
        <CircularProgress />
      </Box>
    );
  }
  
  if (error) {
    return (
      <Box sx={{ p: 3, bgcolor: '#FFF4F4', borderRadius: 1, color: '#D32F2F' }}>
        <Typography>Error loading doctors: {error.message}</Typography>
      </Box>
    );
  }
  
  return (
    <Box sx={{ py: 4 }}>
      <Typography variant="h4" sx={{ mb: 3 }}>{title}</Typography>
      
      <DoctorSearchBar
        filters={filters}
        onFilterChange={setFilters}
        cities={cities}
        specialties={specialties}
      />
      
      <DoctorCardList 
        doctors={filteredDoctors}
        title={filters.searchTerm || filters.selectedCity || filters.selectedSpecialty ? "Search results" : undefined}
        emptyMessage={emptyMessage}
      />
    </Box>
  );
}
