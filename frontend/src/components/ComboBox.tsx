import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';


export default function ComboBox() {
    const mockCities = ["Kraków", "Rzeszów", "Warszawa"]
    return (
        <Autocomplete
            disablePortal
            options={mockCities}
            sx={{ width: 150, m:1 }}
            renderInput={(params) => <TextField {...params} label="Movie" />}
        />
    );
}
