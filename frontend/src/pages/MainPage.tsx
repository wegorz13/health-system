import { Box, Container, Typography } from "@mui/material";
import DoctorSearchAndList from "../components/find_doctor/DoctorSearchAndList";
import DoctorsRowList from "../components/DoctorRowList";
function MainPage() {
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
          <DoctorsRowList title="Best doctors" />
          <DoctorsRowList title="Doctors you need to know" />
        </Box>
      </Container>
    </>
  );
}

export default MainPage;
