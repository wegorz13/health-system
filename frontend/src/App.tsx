import { Box, Typography } from "@mui/material";
import DoctorHoursList from "../components/DoctorHoursList";
import { useParams } from "react-router-dom";
import { useDoctor } from "../hooks";

/**
 * Doctor details page component
 * Using the useDoctor hook to automatically handle loading, error states, and data fetching
 */
export default function DoctorPage() {
    const { id } = useParams<{ id: string }>();
    const { data: doctor, loading, error } = useDoctor(id);    return (
        <>
            {loading ? (
                <Typography>Loading...</Typography>
            ) : error ? (
                <Typography color="error">Error loading doctor: {error.message}</Typography>
            ) : doctor ? (
                <Box sx={{ flexGrow: 1 }}>
                    <Box sx={{ display: 'grid', gridTemplateColumns: { xs: '1fr', md: '1fr 1fr' }, gap: 2, alignItems: 'center' }}>
                        <Box>
                            <Typography variant="h5" fontWeight="bold">
                                {doctor.fullName}
                            </Typography>
                        </Box>
                        <Box>
                            <DoctorHoursList doctor={doctor} />
                        </Box>
                        <Box>
                            <Typography variant="subtitle1">
                                Specialty: {doctor.specialty}
                            </Typography>
                        </Box>
                    </Box>
                </Box>
            ) : (
                <Typography>No doctor found with this ID</Typography>
            )}
        </>
    );
}