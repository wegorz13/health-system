import type { Doctor } from "../types/doctor";
import type { Hospital } from "../types/hospital";
import type { Patient } from "../types/patient";
import type { Visit } from "../types/visit";
import { mockDoctors, mockHospitals, mockPatients, mockVisits } from "../mock/mockData";
import { APP_CONFIG } from "../config/appConfig";
import type { DataSource } from "../config/appConfig";
import type { 
  CreateDoctorRequest,
  CreateWorkDayScheduleRequest,
  CreateHospitalRequest,
  CreatePatientRequest,
  CreateVisitRequest
} from "../types/requests";

// Data source is fixed at build time from environment variables
const dataSource: DataSource = APP_CONFIG.DATA_SOURCE;

// Log the data source on initialization
console.log(`Data source: ${dataSource} mode`);

// Get the current data source (for components that need to know which source is active)
export function getDataSource(): DataSource {
  return dataSource;
}

// Generic fetch function with mapping capability
async function fetchFromApi<T>(endpoint: string, mapper?: (data: any) => T): Promise<T> {
  const response = await fetch(`${APP_CONFIG.API_BASE_URL}/${endpoint}`);
  if (!response.ok) {
    throw new Error(`API error: ${response.status}`);
  }
  const data = await response.json();
  return mapper ? mapper(data) : data;
}

// Generic post function
async function postToApi<T>(endpoint: string, data: any): Promise<T | void> {
  const response = await fetch(`${APP_CONFIG.API_BASE_URL}/${endpoint}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  });
  
  if (!response.ok) {
    throw new Error(`API error: ${response.status}`);
  }
  
  if (response.status === 204) {
    return; // No content
  }
  
  return await response.json();
}

// Helper function to map DoctorDTO from backend to frontend Doctor type
function mapDoctorFromBackend(doctorDto: any): Doctor {
  // The backend sends a Map<DayOfWeek, WorkDayScheduleDTO>
  // We need to convert this to our workingHours structure
  
  // Group schedules by hospital
  const hospitalSchedules: Record<string, any> = {};
  
  if (doctorDto.workDaySchedule) {
    // Iterate through the workDaySchedule map
    Object.entries(doctorDto.workDaySchedule).forEach(([day, schedule]: [string, any]) => {
      const hospitalName = schedule.hospitalName;
      
      // Initialize this hospital if we haven't seen it yet
      if (!hospitalSchedules[hospitalName]) {
        hospitalSchedules[hospitalName] = {
          hospital: {
            id: 0, // We don't know the hospital ID here
            name: hospitalName,
            address: '', // Not provided in this context
            doctors: [],
            phone: '',
            email: ''
          },
          dailyHours: {
            monday: [],
            tuesday: [],
            wednesday: [],
            thursday: [],
            friday: [],
            saturday: [],
            sunday: []
          }
        };
      }
      
      // Format time range as "HH:MM-HH:MM"
      const startTime = schedule.startTime;
      const endTime = schedule.endTime;
      const timeRange = `${startTime}-${endTime}`;
      
      // Convert Java DayOfWeek enum (MONDAY, TUESDAY, etc.) to lowercase
      // to match our frontend data structure (monday, tuesday, etc.)
      const dayKey = day.toLowerCase() as keyof typeof hospitalSchedules[string]['dailyHours'];
      
      // Add this time range to the appropriate day
      hospitalSchedules[hospitalName].dailyHours[dayKey].push(timeRange);
    });
  }
  
  // Convert the grouped schedules into our workingHours array format
  const workingHours = Object.values(hospitalSchedules);
  
  return {
    id: doctorDto.id,
    fullName: doctorDto.fullName,
    specialty: doctorDto.specialty,
    workingHours: workingHours
  };
}

// Helper function to map an array of doctor DTOs
function mapDoctorsFromBackend(doctorDtos: any[]): Doctor[] {
  return doctorDtos.map(mapDoctorFromBackend);
}

// Helper function to map HospitalDTO from backend to frontend Hospital type
function mapHospitalFromBackend(hospitalDto: any): Hospital {
  return {
    id: hospitalDto.id,
    name: hospitalDto.name,
    address: hospitalDto.address,
    phone: hospitalDto.phone,
    email: hospitalDto.email,
    doctors: hospitalDto.doctors ? mapDoctorsFromBackend(hospitalDto.doctors) : []
  };
}

// Helper function to map an array of hospital DTOs
function mapHospitalsFromBackend(hospitalDtos: any[]): Hospital[] {
  return hospitalDtos.map(mapHospitalFromBackend);
}

// Helper function to map PatientDTO from backend to frontend Patient type
function mapPatientFromBackend(patientDto: any): Patient {
  return {
    id: patientDto.id,
    firstName: patientDto.firstName,
    lastName: patientDto.lastName,
    username: patientDto.username,
    email: patientDto.email,
    dateOfBirth: patientDto.dateOfBirth, // Format conversion may be needed (LocalDate to string)
    gender: patientDto.gender,
    roles: patientDto.roles
  };
}

// Helper function to map an array of patient DTOs
function mapPatientsFromBackend(patientDtos: any[]): Patient[] {
  return patientDtos.map(mapPatientFromBackend);
}

// Helper function to map VisitDTO from backend to frontend Visit type
function mapVisitFromBackend(visitDto: any): Visit {
  return {
    id: visitDto.id,
    date: visitDto.date, // Format conversion may be needed (LocalDateTime to string)
    cost: visitDto.cost,
    prescriptions: visitDto.prescriptions,
    description: visitDto.patientsCondition,
    doctor: visitDto.doctor ? mapDoctorFromBackend(visitDto.doctor) : null
  };
}

// Helper function to map an array of visit DTOs
function mapVisitsFromBackend(visitDtos: any[]): Visit[] {
  return visitDtos.map(mapVisitFromBackend);
}

// Doctor services - updated to use mappers
export async function getDoctors(): Promise<Doctor[]> {
  if (dataSource === 'mock') {
    return [...mockDoctors];
  }
  return fetchFromApi<Doctor[]>('doctors/', mapDoctorsFromBackend);
}

export async function getDoctor(id: number | string): Promise<Doctor | null> {
  if (dataSource === 'mock') {
    const doctor = mockDoctors.find(d => d.id === Number(id));
    return doctor ? { ...doctor } : null;
  }
  try {
    return await fetchFromApi<Doctor | null>(`doctors/${id}`, 
      data => data ? mapDoctorFromBackend(data) : null);
  } catch (error) {
    console.error('Error fetching doctor:', error);
    return null;
  }
}

export async function createDoctor(data: CreateDoctorRequest): Promise<void> {
  if (dataSource === 'mock') {
    console.log('Mock create doctor:', data);
    return;
  }
  return postToApi<void>('doctors/', data);
}

export async function addDoctorWorkSchedule(
  doctorId: number | string, 
  data: CreateWorkDayScheduleRequest
): Promise<void> {
  if (dataSource === 'mock') {
    console.log(`Mock add work schedule for doctor ${doctorId}:`, data);
    return;
  }
  return postToApi<void>(`doctors/${doctorId}`, data);
}

export async function getDoctorsByHospitalId(hospitalId: number | string): Promise<Doctor[]> {
  if (dataSource === 'mock') {
    return mockDoctors.filter(d => 
      d.workingHours.some(wh => wh.hospital.id === Number(hospitalId))
    );
  }
  return fetchFromApi<Doctor[]>(`doctors/hospital/${hospitalId}`, mapDoctorsFromBackend);
}

export async function getDoctorsByPatientId(patientId: number | string): Promise<Doctor[]> {
  if (dataSource === 'mock') {
    // In mock mode, return some sample doctors
    return mockDoctors.slice(0, 2);
  }
  return fetchFromApi<Doctor[]>(`doctors/patient/${patientId}`, mapDoctorsFromBackend);
}

export async function getRecommendedDoctors(): Promise<Doctor[]> {
  if (dataSource === 'mock') {
    // Return random doctors in mock mode
    return [...mockDoctors].sort(() => 0.5 - Math.random()).slice(0, 3);
  }
  return fetchFromApi<Doctor[]>('doctors/recommendations', mapDoctorsFromBackend);
}

export async function getRelativeRecommendedDoctors(patientId: number | string): Promise<Doctor[]> {
  if (dataSource === 'mock') {
    // Return random doctors in mock mode
    return [...mockDoctors].sort(() => 0.5 - Math.random()).slice(0, 2);
  }
  return fetchFromApi<Doctor[]>(`doctors/recommendations/${patientId}`, mapDoctorsFromBackend);
}

// Hospital services
export async function getHospitals(): Promise<Hospital[]> {
  if (dataSource === 'mock') {
    return [...mockHospitals];
  }
  return fetchFromApi<Hospital[]>('hospitals/', mapHospitalsFromBackend);
}

export async function getHospital(id: number | string): Promise<Hospital | null> {
  if (dataSource === 'mock') {
    const hospital = mockHospitals.find(h => h.id === Number(id));
    return hospital ? { ...hospital } : null;
  }
  try {
    return await fetchFromApi<Hospital | null>(
      `hospitals/${id}`,
      data => data ? mapHospitalFromBackend(data) : null
    );
  } catch (error) {
    console.error('Error fetching hospital:', error);
    return null;
  }
}

export async function createHospital(data: CreateHospitalRequest): Promise<void> {
  if (dataSource === 'mock') {
    console.log('Mock create hospital:', data);
    return;
  }
  return postToApi<void>('hospitals', data);
}

// Patient services
export async function getPatients(): Promise<Patient[]> {
  if (dataSource === 'mock') {
    return [...mockPatients];
  }
  return fetchFromApi<Patient[]>('patients/', mapPatientsFromBackend);
}

export async function getPatient(id: number | string): Promise<Patient | null> {
  if (dataSource === 'mock') {
    const patient = mockPatients.find(p => p.id === Number(id));
    return patient ? { ...patient } : null;
  }
  try {
    return await fetchFromApi<Patient | null>(
      `patients/${id}`,
      data => data ? mapPatientFromBackend(data) : null
    );
  } catch (error) {
    console.error('Error fetching patient:', error);
    return null;
  }
}

export async function registerPatient(data: CreatePatientRequest): Promise<Patient> {
  if (dataSource === 'mock') {
    console.log('Mock register patient:', data);
    return { 
      id: Date.now(), 
      firstName: data.firstName, 
      lastName: data.lastName,
      username: data.username,
      email: data.email,
      dateOfBirth: data.dateOfBirth,
      gender: data.gender,
      roles: data.roles
    };
  }
  return postToApi<Patient>('patients/auth/register', data)
    .then(response => mapPatientFromBackend(response));
}

export async function getPatientRelatives(patientId: number | string): Promise<Patient[]> {
  if (dataSource === 'mock') {
    // Return sample relatives in mock mode
    return mockPatients.filter(p => p.id !== Number(patientId)).slice(0, 2);
  }
  return fetchFromApi<Patient[]>(
    `patients/relatives/${patientId}`,
    mapPatientsFromBackend
  );
}

export async function addRelative(
  patientId: number | string, 
  relativeId: number | string
): Promise<void> {
  if (dataSource === 'mock') {
    console.log(`Mock add relative ${relativeId} to patient ${patientId}`);
    return;
  }
  return postToApi<void>(`patients/relatives/${patientId}?relativeId=${relativeId}`, {});
}

export async function recommendVisit(
  patientId: number | string, 
  visitId: number | string
): Promise<void> {
  if (dataSource === 'mock') {
    console.log(`Mock recommend visit ${visitId} for patient ${patientId}`);
    return;
  }
  const response = await fetch(
    `${APP_CONFIG.API_BASE_URL}/patients/recommended/${patientId}?visitId=${visitId}`, 
    { method: 'PATCH' }
  );
  
  if (!response.ok) {
    throw new Error(`API error: ${response.status}`);
  }
}

// Visit services
export async function getVisits(): Promise<Visit[]> {
  if (dataSource === 'mock') {
    return [...mockVisits];
  }
  return fetchFromApi<Visit[]>('visits/', mapVisitsFromBackend);
}

export async function getVisit(id: number | string): Promise<Visit | null> {
  if (dataSource === 'mock') {
    const visit = mockVisits.find(v => v.id === Number(id));
    return visit ? { ...visit } : null;
  }
  try {
    return await fetchFromApi<Visit | null>(
      `visits/${id}`,
      data => data ? mapVisitFromBackend(data) : null
    );
  } catch (error) {
    console.error('Error fetching visit:', error);
    return null;
  }
}

export async function getPatientVisits(patientId: number | string): Promise<Visit[]> {
  if (dataSource === 'mock') {
    const patient = mockPatients.find(p => p.id === Number(patientId));
    return patient?.visits || [];
  }
  return fetchFromApi<Visit[]>(
    `visits/patient/${patientId}`,
    mapVisitsFromBackend
  );
}

export async function getDoctorVisits(doctorId: number | string): Promise<Visit[]> {
  if (dataSource === 'mock') {
    return mockVisits.filter(v => v.doctor.id === Number(doctorId));
  }
  return fetchFromApi<Visit[]>(
    `visits/doctor/${doctorId}`,
    mapVisitsFromBackend
  );
}

export async function createVisit(data: CreateVisitRequest): Promise<void> {
  if (dataSource === 'mock') {
    console.log('Mock create visit:', data);
    return;
  }
  return postToApi<void>('visits/', data);
}

// Add this function to get the current user
export async function getCurrentUser(): Promise<string> {
  if (dataSource === 'mock') {
    // Return a mock username in mock mode
    return mockPatients[0]?.username || 'mockuser';
  }
  return fetchFromApi<string>('patients/auth/me');
}
