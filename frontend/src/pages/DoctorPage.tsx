import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import type {Doctor} from "../types/doctor.ts";
import DoctorHoursList from "../components/DoctorHoursList.tsx";
import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";

export default function DoctorPage() {
    const { id } = useParams();
    const [doctor, setDoctor] = useState<Doctor | null>(null);

    useEffect(() => {
        fetch(`http://localhost:8080/api/v1/doctors/${id}`)
            .then((res) => res.json())
            .then((data) => setDoctor(data));
    }, [id]);

    return (
        <>
            {doctor != null ? (
                <Box sx={{ flexGrow: 1 }}>
                    <Grid container spacing={2} alignItems={"center"}>
                        <Grid size={12}>
                            <Typography variant="h5" fontWeight="bold">
                                {doctor.fullName}
                            </Typography>
                        </Grid>
                        <Grid size={6}>
                            <DoctorHoursList doctor={doctor} />
                        </Grid>
                        <Grid size={4}>
                        </Grid>
                    </Grid>
                </Box>
            ) : (
                <p>Loading...</p>
            )}
        </>
    );
}