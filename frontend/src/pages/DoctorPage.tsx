import { Box, Typography, Container, Paper, Grid } from "@mui/material";
import DoctorHoursList from "../components/DoctorHoursList";
import { useParams } from "react-router-dom";
import { useDoctor } from "../hooks";
import { LoadingSpinner, ErrorMessage } from "../components/ui"; // Use the UI components
import DoctorInfo from "../components/doctor/DoctorInfo";

/**
 * Doctor details page component
 * Using the useDoctor hook to automatically handle loading, error states, and data fetching
 */
export default function DoctorPage() {
    const { id } = useParams<{ id: string }>();
    const { data: doctor, loading, error } = useDoctor(id);
    
    if (loading) {
        return <LoadingSpinner />;
    }
    
    if (error) {
        return <ErrorMessage message={`Error loading doctor: ${error.message}`} />;
    }
    
    if (!doctor) {
        return <ErrorMessage message="No doctor found with this ID" severity="warning" />;
    }
    
    return (
        <Container maxWidth="lg" sx={{ mt: 4, mb: 6 }}>
            <Paper elevation={2} sx={{ p: 3 }}>
                <Grid container spacing={3}>
                    <Grid item xs={12} md={6}>
                        <DoctorInfo doctor={doctor} />
                    </Grid>
                    
                    <Grid item xs={12} md={6}>
                        <Typography variant="h6" gutterBottom>
                            Working Hours
                        </Typography>
                        <DoctorHoursList doctor={doctor} />
                    </Grid>
                </Grid>
            </Paper>
        </Container>
    );
}