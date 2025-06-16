import { Box, Container, Typography } from "@mui/material";
import DoctorSearchAndList from "../components/find_doctor/DoctorSearchAndList";
import DoctorsRowList from "../components/DoctorRowList";
import { useState, useEffect } from "react";
import { getCurrentUser } from "../services/dataService";

function MainPage() {
  const [currentPatientId, setCurrentPatientId] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  // Fetch current user ID on component mount
  useEffect(() => {
    async function fetchCurrentUser() {
      try {
        // In a real app, this would fetch the logged-in user's ID
        // For now, we'll use a mock ID or get it from the API based on your dataSource
        const username = await getCurrentUser();
        // This is a simplified approach - in a real app, you would
        // likely have a proper user context/authentication system
        setCurrentPatientId("1"); // Using a default ID for demo
      } catch (error) {
        console.error("Error fetching current user:", error);
      } finally {
        setLoading(false);
      }
    }

    fetchCurrentUser();
  }, []);

  return (
    <>
      <Container maxWidth="lg">
        <Box sx={{ mt: 8, pb: 4 }}>
          <Typography
            variant="h3"
            component="h1"
            gutterBottom
            sx={{ mb: 4, textAlign: "center" }}
          >
            Welcome to Health System
          </Typography>

          <DoctorSearchAndList title="Find Your Doctor" />
          <DoctorsRowList title="Best doctors" type="best" />

          {/* Only show personalized recommendations if we have a patient ID */}
          {!loading && currentPatientId && (
            <DoctorsRowList
              title="Doctors you need to know"
              type="relative"
              patientId={currentPatientId}
            />
          )}
        </Box>
      </Container>
    </>
  );
}

export default MainPage;
