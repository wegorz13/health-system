import Typography from "@mui/material/Typography";
import {Stack} from "@mui/material";
import type {Doctor} from "../types/doctor.ts";

export default function DoctorHoursList(props: {doctor:Doctor}) {
    const {doctor} = props;
    return (
    <Stack spacing={2}>
        {doctor.workingHours.map((whs, whIndex) =>(
            <div key={`hospital-${whIndex}`}>
                <Typography variant="subtitle1" >
                    {whs.hospital.name}
                </Typography>
                
                <ul>
                    {whs.dailyHours.monday && (
                        <li>
                            <Typography variant="body2" component="span" fontWeight="bold">Monday: </Typography>
                            {whs.dailyHours.monday.join(", ")}
                        </li>
                    )}
                    {whs.dailyHours.tuesday && (
                        <li>
                            <Typography variant="body2" component="span" fontWeight="bold">Tuesday: </Typography>
                            {whs.dailyHours.tuesday.join(", ")}
                        </li>
                    )}
                    {whs.dailyHours.wednesday && (
                        <li>
                            <Typography variant="body2" component="span" fontWeight="bold">Wednesday: </Typography>
                            {whs.dailyHours.wednesday.join(", ")}
                        </li>
                    )}
                    {whs.dailyHours.thursday && (
                        <li>
                            <Typography variant="body2" component="span" fontWeight="bold">Thursday: </Typography>
                            {whs.dailyHours.thursday.join(", ")}
                        </li>
                    )}
                    {whs.dailyHours.friday && (
                        <li>
                            <Typography variant="body2" component="span" fontWeight="bold">Friday: </Typography>
                            {whs.dailyHours.friday.join(", ")}
                        </li>
                    )}
                    {whs.dailyHours.saturday && (
                        <li>
                            <Typography variant="body2" component="span" fontWeight="bold">Saturday: </Typography>
                            {whs.dailyHours.saturday.join(", ")}
                        </li>
                    )}
                    {whs.dailyHours.sunday && (
                        <li>
                            <Typography variant="body2" component="span" fontWeight="bold">Sunday: </Typography>
                            {whs.dailyHours.sunday.join(", ")}
                        </li>
                    )}
                </ul>
            </div>
        ))}
    </Stack>)
}