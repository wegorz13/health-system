import { 
  Box, 
  TextField, 
  Autocomplete, 
  Paper,
  InputAdornment,
} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import LocationOnIcon from '@mui/icons-material/LocationOn';
import MedicalServicesIcon from '@mui/icons-material/MedicalServices';

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
        
        {/* City filter */}
        <Box sx={{ flex: '1 1 250px' }}>
          <Autocomplete
            options={cities}
            value={filters.selectedCity}
            onChange={(_, newValue) => onFilterChange({ selectedCity: newValue })}
            renderInput={(params) => (
              <TextField
                {...params}
                label="Filter by city"
                placeholder="Select a city"
                InputProps={{
                  ...params.InputProps,
                  startAdornment: (
                    <InputAdornment position="start">
                      <LocationOnIcon color="action" />
                    </InputAdornment>
                  ),
                }}
              />
            )}
          />
        </Box>

        {/* Specialty filter */}
        <Box sx={{ flex: '1 1 250px' }}>
          <Autocomplete
            options={specialties}
            value={filters.selectedSpecialty}
            onChange={(_, newValue) => onFilterChange({ selectedSpecialty: newValue })}
            renderInput={(params) => (
              <TextField
                {...params}
                label="Filter by specialty"
                placeholder="Select a specialty"
                InputProps={{
                  ...params.InputProps,
                  startAdornment: (
                    <InputAdornment position="start">
                      <MedicalServicesIcon color="action" />
                    </InputAdornment>
                  ),
                }}
              />
            )}
          />
        </Box>

      </Box>
    </Paper>
  );
}
