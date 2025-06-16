import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import { ReactNode } from 'react';
import { InputAdornment } from '@mui/material';


interface ComboBoxProps {
  options: string[];
  value: string | null;
  onChange: (value: string | null) => void;
  label?: string;
  placeholder?: string;
  startIcon?: ReactNode;
  sx?: any;
}

export function ComboBox({ 
  options, 
  value, 
  onChange, 
  label = "Select", 
  placeholder = "Select an option",
  startIcon,
  sx = {}
}: ComboBoxProps) {
  return (
    <Autocomplete
      options={options}
      value={value}
      onChange={(_, newValue) => onChange(newValue)}
      sx={{ width: '100%', ...sx }}
      renderInput={(params) => (
        <TextField 
          {...params} 
          label={label} 
          placeholder={placeholder}
          InputProps={{
            ...params.InputProps,
            startAdornment: startIcon ? (
              <InputAdornment position="start">
                {startIcon}
                {params.InputProps.startAdornment}
              </InputAdornment>
            ) : params.InputProps.startAdornment,
          }}
        />
      )}
    />
  );
}
