import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import React from "react";

export default function BasicTextField({ setSearch }) {
    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearch(event.target.value);
    };

    return (
        <Box
            component="form"
            sx={{ '& > :not(style)': { m: 1, width: 300 } }}
            noValidate
            autoComplete="off"
        >
            <TextField
                id="outlined-basic"
                label="Search"
                variant="outlined"
                onChange={handleChange}
            />
        </Box>
    );
}
