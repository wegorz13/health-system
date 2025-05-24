import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import type {Doctor} from "../types/doctor.ts";

export default function doctorListElem(props:{doctor: Doctor}) {
    const {doctor} = props;

    return (
        <Box sx={{ flexGrow: 1 }}>
            <Grid container spacing={2}>
                <Grid size={12}>
                <p>{doctor.fullName}</p>
                </Grid>
                <Grid size={4}>
                <p>{doctor.specialty}</p>
                </Grid>
            </Grid>
        </Box>
    );
}