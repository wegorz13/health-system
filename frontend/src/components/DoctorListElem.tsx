import { Box, Paper, Typography, Stack } from "@mui/material";
import type { Doctor } from "../types/doctor";

export default function DoctorListElem({ doctor }: { doctor: Doctor }) {
    return (
        <Box sx={{ flexGrow: 1, p: 2 }}>
            <Paper elevation={3} sx={{ p: 3, borderRadius: 2 }}>
                <Stack spacing={1}>
                    <Typography variant="h5" fontWeight="bold">
                        {doctor.fullName}
                    </Typography>
                    <Typography variant="subtitle1" color="text.secondary">
                        Specialty: {doctor.specialty}
                    </Typography>
                </Stack>
            </Paper>
        </Box>
    );
}