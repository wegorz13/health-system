import {
  Card,
  CardContent,
  CardActions,
  Typography,
  Button,
  Avatar,
  Box,
} from "@mui/material";
import { type Doctor } from "../types/doctor";
import { useNavigate } from "react-router-dom";

interface DoctorCardProps {
  doctor: Doctor;
}

export default function DoctorCard({
  doctor,
}: DoctorCardProps) {
  const navigate = useNavigate();
  const handleViewProfile = () => {
    navigate(`/doctor/${doctor.id}`);
  };

  const handleBookAppointment = () => {
    navigate(`/book-appointment/${doctor.id}`);
  };
  // Get all unique hospital locations
  const locations = [...new Set(doctor.workingHours?.map(wh => wh.hospital?.name) || [])];

  return (
    <Card className="transition-all duration-300 hover:shadow-lg border border-gray-200">
      <CardContent>
        <Box className="flex items-center mb-3">
          <Avatar
            className="bg-primary text-white mr-3 h-16 w-16"
            alt={doctor.fullName}
          >
            {doctor.fullName
              .split(" ")
              .map((n) => n[0])
              .join("")
              .substring(1)}
          </Avatar>
          <Box>
            <Typography variant="h6" component="h3" className="font-bold">
              {doctor.fullName}
            </Typography>
            <Typography variant="body2" color="text.secondary">
              {doctor.specialty}
            </Typography>
          </Box>
        </Box>

        <Box className="mt-3 m">
          <Typography variant="body2" className="text-gray-700 font-medium mb-1">
            Available at:
          </Typography>
          {locations.length > 0 ? (
            locations.map((location, index) => (
              <Typography key={index} variant="body2" className="text-gray-600 pl-2">
                • {location || "Unknown location"}
              </Typography>
            ))
          ) : (
            <Typography variant="body2" className="text-gray-600 pl-2">
              • No locations available
            </Typography>
          )}
        </Box>
      </CardContent>

      <CardActions className="bg-gray-50 flex justify-between">
        <Button
          size="small"
          onClick={handleViewProfile}
          className="text-secondary-dark hover:text-secondary"
        >
          View Profile
        </Button>
        <Button
          size="small"
          variant="contained"
          color="primary"
          onClick={handleBookAppointment}
          className="bg-primary hover:bg-primary-dark"
        >
          Book Appointment
        </Button>
      </CardActions>
    </Card>
  );
}
