import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import DoctorPage from "./DoctorPage";
import MainPage from "./MainPage";
import BookAppointmentPage from "./BookAppointmentPage";
import { Container, Paper, Box, Typography } from "@mui/material";
import NavigationBar from "../components/NavigationBar";

export default function App() {
    return (
        <Router>
            <div className="flex flex-col min-h-screen">
                <NavigationBar />
                <Container maxWidth="lg" className="flex-grow py-4">                    <Routes>
                        <Route path="/" element={<MainPage />} />
                        <Route path="/doctor/:id" element={<DoctorPage />} />
                        <Route path="/history" element={
                            <Paper elevation={3} className="p-6">
                                <Typography variant="h4" className="mb-4">Historia wizyt</Typography>
                                <Typography>Ta funkcjonalność będzie dostępna wkrótce.</Typography>
                            </Paper>
                        } />
                        <Route path="/prescriptions" element={
                            <Paper elevation={3} className="p-6">
                                <Typography variant="h4" className="mb-4">Recepty</Typography>
                                <Typography>Ta funkcjonalność będzie dostępna wkrótce.</Typography>
                            </Paper>
                        } />
                        <Route path="/book-appointment/:doctorId" element={
                            <BookAppointmentPage />
                        } />
                    </Routes>
                </Container>
                <Box component="footer" className="bg-gray-100 py-3 px-4 text-center">
                    <Typography variant="body2" color="textSecondary">
                        &copy; 2025 HealthSystem. Wszystkie prawa zastrzeżone.
                    </Typography>
                </Box>
            </div>
        </Router>
    );
}
