import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import type {Hospital} from "../types/hospital.ts";
import {Paper} from "@mui/material";
import Typography from "@mui/material/Typography";

export default function HospitalListElem(props:{hospital: Hospital}) {
    const {hospital} = props;

    return (
        <Box sx={{ flexGrow: 1, p: 2 }}>
            <Paper elevation={3} sx={{ p: 3, borderRadius: 2 }}>
                <Grid container spacing={2}>
                    <Grid size={{xs:12}}>
                        <Typography variant="h5" fontWeight="bold">
                            {hospital.name}
                        </Typography>
                    </Grid>
                    <Grid size={{xs:12, sm:6, md:4}}>
                        <Typography variant="subtitle1" color="text.secondary">
                            Address: {hospital.address}
                        </Typography>
                    </Grid>
                </Grid>
            </Paper>
        </Box>
    );
}