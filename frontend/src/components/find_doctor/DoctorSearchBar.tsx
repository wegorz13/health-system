import { 
  Box, 
  TextField, 
  Paper,
  InputAdornment,
} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import MedicalServicesIcon from '@mui/icons-material/MedicalServices';
import { ComboBox } from "../ui"; // Import ComboBox from UI

export interface DoctorSearchFilters {
  searchTerm: string;
  selectedCity: string | null;
  selectedSpecialty: string | null;
}

interface DoctorSearchBarProps {
  filters: DoctorSearchFilters;
  onFilterChange: (newFilters: Partial<DoctorSearchFilters>) => void;
  cities: string[];
  specialties: string[];
}

export default function DoctorSearchBar({ 
  filters, 
  onFilterChange, 
  cities, 
  specialties 
}: DoctorSearchBarProps) {
  return (
    <Paper sx={{ p: 3, mb: 4 }}>
      <Box sx={{ 
        display: 'flex', 
        flexDirection: { xs: 'column', sm: 'row' },
        flexWrap: 'wrap',
        gap: 2
      }}>
        {/* Name search */}
        <Box sx={{ flex: '1 1 250px' }}>
          <TextField
            fullWidth
            label="Search by name"
            placeholder="Enter doctor name"
            value={filters.searchTerm}
            onChange={(e) => onFilterChange({ searchTerm: e.target.value })}
            size="medium"
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <SearchIcon color="action" />
                </InputAdornment>
              ),
            }}
          />
        </Box>
        
        {/* City filter - Use ComboBox */}
        <Box sx={{ flex: '1 1 250px' }}>
          <ComboBox 
            options={cities}
            value={filters.selectedCity}
            onChange={(newValue) => onFilterChange({ selectedCity: newValue })}
            label="Filter by city"
            placeholder="Select a city"
            startIcon={<LocationOnIcon color="action" />}
          />
        </Box>

        {/* Specialty filter - Use ComboBox */}
        <Box sx={{ flex: '1 1 250px' }}>
          <ComboBox 
            options={specialties}
            value={filters.selectedSpecialty}
            onChange={(newValue) => onFilterChange({ selectedSpecialty: newValue })}
            label="Filter by specialty"
            placeholder="Select a specialty"
            startIcon={<MedicalServicesIcon color="action" />}
          />
        </Box>

      </Box>
    </Paper>
  );
}
