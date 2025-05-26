import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import BasicTextField from "./BasicTextField.tsx";
import ComboBox from "./ComboBox.tsx";
import {useEffect, useState} from "react";
import {Stack} from "@mui/material";
import {Link} from "react-router-dom"
import DoctorListElem from "./DoctorListElem";
import HospitalListElem from "./HospitalListElem.tsx";
import type {Doctor} from "../types/doctor.ts";
import DoctorHospitalSwitch from "./AntSwitch.tsx";
import type {Hospital} from "../types/hospital.ts";

export default function Searcher(){
    const [search, setSearch] = useState("");
    const [doctors, setDoctors] = useState<Doctor[]>([]);
    const [hospitals, setHospitals] = useState<Hospital[]>([]);
    const [showDoctors, setShowDoctors] = useState(true);

    useEffect(() => {
        fetch("http://localhost:8080/api/v1/doctors/")
            .then((response)=>response.json())
            .then((data)=>setDoctors(data))
        fetch("http://localhost:8080/api/v1/hospitals/")
            .then((response)=>response.json())
            .then((data)=>setHospitals(data))
    }, [])

    return (
        <>
            <Box sx={{ flexGrow: 1 }}>
                <Grid container spacing={2} alignItems={"center"}>
                    <Grid size={2}>
                        <DoctorHospitalSwitch onChange={(val) => setShowDoctors(val)}></DoctorHospitalSwitch>
                    </Grid>
                    <Grid size={6}>
                        <BasicTextField setSearch={setSearch} />
                    </Grid>
                    <Grid size={4}>
                        <ComboBox />
                    </Grid>
                </Grid>
            </Box>

            <Stack spacing={2} sx={{ mt: 2 }}>
                {showDoctors ?
                    doctors
                    .filter((doctor) =>
                        doctor.fullName.toLowerCase().includes(search.toLowerCase())
                    ).map((doctor) => (
                        <Link to={`/doctor/${doctor.id}`} style={{ textDecoration: "none" }}>
                        <DoctorListElem key={doctor.id} doctor={doctor} />
                        </Link>))
                    :
                    hospitals.filter((hospital) =>
                        hospital.name.toLowerCase().includes(search.toLowerCase())
                    ).map((hospital) => (
                        <HospitalListElem key={hospital.id} hospital={hospital}></HospitalListElem>
                    ))
                }
            </Stack>
        </>
    )
}