import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import BasicTextField from "./BasicTextField.tsx";
import ComboBox from "./ComboBox.tsx";
import {useEffect, useState} from "react";
import {Stack} from "@mui/material";
import DoctorListElem from "./DoctorListElem";
import type {Doctor} from "../types/doctor.ts";

export default function Searcher(){
    const [search, setSearch] = useState("");
    const [doctors, setDoctors] = useState<Doctor[]>([]);

    useEffect(() => {
        fetch("http://localhost:8080/api/v1/doctors/")
            .then((response)=>response.json())
            .then((data)=>setDoctors(data))
    })

    return (
        <>
            <Box sx={{ flexGrow: 1 }}>
                <Grid container spacing={2}>
                    <Grid size={8}>
                        <BasicTextField setSearch={setSearch} />
                    </Grid>
                    <Grid size={4}>
                        <ComboBox />
                    </Grid>
                </Grid>
            </Box>

            <Stack spacing={2} sx={{ mt: 2 }}>
                {doctors
                    .filter((doctor) =>
                        doctor.fullName.toLowerCase().includes(search.toLowerCase())
                    )
                    .map((doctor) => (
                        <DoctorListElem key={doctor.id} doctor={doctor} />
                    ))}
            </Stack>
        </>
    )
}