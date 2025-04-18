package pl.agh.databases.health_system.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.agh.databases.health_system.repositories.PatientRepository;

@Service
public class NeoPatientDetailsService implements UserDetailsService {
    private final PatientRepository patientRepository;

    public NeoPatientDetailsService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return patientRepository.findPatientByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found: " + username));
    }
}
