package com.nutritiousprog.medfriend.services;

import com.nutritiousprog.medfriend.exceptions.InvalidArgumentException;
import com.nutritiousprog.medfriend.exceptions.ObjectAlreadyExistsException;
import com.nutritiousprog.medfriend.exceptions.ObjectNotFoundException;
import com.nutritiousprog.medfriend.model.Patient;
import com.nutritiousprog.medfriend.repositories.AddressRepository;
import com.nutritiousprog.medfriend.repositories.PatientRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Component
public class PatientService implements BasicService<Patient> {
    private PatientRepository patientRepository;
    private AddressRepository addressRepository;

    public PatientService(PatientRepository patientRepository, AddressRepository addressRepository) {
        this.patientRepository = patientRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Patient create(Patient patient) throws InvalidArgumentException, ObjectAlreadyExistsException{
        if(patient == null)
            throw new InvalidArgumentException("Passed patient is invalid (null).");
        if(checkIfEntityExistsInDb(patient))
            throw new ObjectAlreadyExistsException("The same object was already found in database. Creating patient failed.");
        if(patient.getAddress() == null || patient.getName().isEmpty() || !isPhoneNumberValid(patient.getPhoneNumber()))
            throw new InvalidArgumentException("Passed patient has invalid parameters.");

        patientRepository.save(patient);
        addressRepository.save(patient.getAddress());
        return patient;
    }

    @Override
    public boolean delete(Long id) throws InvalidArgumentException, ObjectNotFoundException {
        if(id < 0)
            throw new InvalidArgumentException("Passed id is invalid.");

        boolean exists = patientRepository.existsById(id);
        if (!exists)
            throw new ObjectNotFoundException("Object not found in database. Deleting patient failed.");

        //Address addressToDelete = patientRepository.findById(id).get().getAddress();

        patientRepository.deleteById(id);
        //addressRepository.delete(addressToDelete);

        return true;
    }

    @Override
    public Patient update(Long id, Patient newPatient)  throws InvalidArgumentException, NullPointerException, ObjectNotFoundException {
        if (id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");
        if (newPatient == null)
            throw new NullPointerException("New patient is null.");

        Patient underChangePatient = patientRepository.findById(id).get();

        boolean exists = patientRepository.existsById(id);
        if (!exists)
            throw new ObjectNotFoundException("Object with given id was not found in database.");

        addressRepository.delete(underChangePatient.getAddress());

        underChangePatient.setName(newPatient.getName());
        underChangePatient.setPhoneNumber(newPatient.getPhoneNumber());
        underChangePatient.setAddress(newPatient.getAddress());

        addressRepository.save(newPatient.getAddress());
        patientRepository.save(underChangePatient);
        return newPatient;
    }

    @Override
    public Patient getById(Long id) throws InvalidArgumentException, ObjectNotFoundException{
        if(id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");

        Patient wantedPatient = patientRepository.findById(id).get();

        if (wantedPatient == null)
            throw new ObjectNotFoundException("Object with given id was not found in database.");

        return wantedPatient;
    }

    @Override
    public List<Patient> getAll() {
        return (List<Patient>) patientRepository.findAll();
    }

    @Override
    public boolean checkIfEntityExistsInDb(Patient patient) {
        Iterable<Patient> currentPatients = patientRepository.findAll();

        for(Patient p : currentPatients) {
            if(patient.equals(p)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        String regex = "^[0-9]{9}"; // Available patterns "123456789"

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}