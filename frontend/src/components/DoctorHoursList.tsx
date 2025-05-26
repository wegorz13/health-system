import Typography from "@mui/material/Typography";
import {Stack} from "@mui/material";
import type {Doctor} from "../types/doctor.ts";

export default function DoctorHoursList(props: {doctor:Doctor}) {
    const {doctor} = props;
    return (
    <Stack spacing={2}>
        {doctor.workingHours.map((whs) =>(
            <div>
                <Typography variant="subtitle1" >
                    {whs.hospital.name}
                </Typography>

                {doctor.workingHours.map((whs) => (
                    <ul>
                        {whs.hours.map((hours) =>(
                            <li>
                                {hours}
                            </li>))}
                    </ul>
                ))}
            </div>
        ))}
    </Stack>)
}