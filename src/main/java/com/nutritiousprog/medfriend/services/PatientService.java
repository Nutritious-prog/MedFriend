package com.nutritiousprog.medfriend.services;

import com.nutritiousprog.medfriend.exceptions.InvalidArgumentException;
import com.nutritiousprog.medfriend.exceptions.ObjectAlreadyExistsException;
import com.nutritiousprog.medfriend.exceptions.ObjectNotFoundException;
import com.nutritiousprog.medfriend.model.Patient;
import com.nutritiousprog.medfriend.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService implements BasicService<Patient> {
    PatientRepository patientRepository;
    AddressService addressService;

    @Override
    public Patient create(Patient patient) throws InvalidArgumentException, ObjectAlreadyExistsException{
        if(patient == null)
            throw new InvalidArgumentException("Passed address is invalid (null).");
        if(checkIfEntityExistsInDb(patient))
            throw new ObjectAlreadyExistsException("The same object was already found in database. Creating address failed.");
        if(patient.getAddress() == null || patient.getName().isEmpty() || patient.getPhoneNumber().isEmpty())
            throw new InvalidArgumentException("Passed address has invalid parameters.");

        addressService.create(patient.getAddress());
        patientRepository.save(patient);
        return patient;
    }

    @Override
    public boolean delete(Long id) throws InvalidArgumentException, ObjectNotFoundException {
        if(id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");

        boolean exists = patientRepository.existsById(id);
        if (!exists)
            throw new ObjectNotFoundException("Object not found in database. Deleting address failed.");

        addressService.delete(patientRepository.findById(id).get().getID());
        patientRepository.deleteById(id);

        return true;
    }

    @Override
    public Patient update(Long id, Patient newPatient)  throws InvalidArgumentException, NullPointerException, ObjectNotFoundException {
        if (id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");
        if (newPatient == null)
            throw new NullPointerException("New address is null.");

        Patient underChangePatient = patientRepository.findById(id).get();

        if (!checkIfEntityExistsInDb(underChangePatient))
            throw new ObjectNotFoundException("Object with given id was not found in database.");

        underChangePatient.setName(newPatient.getName());
        underChangePatient.setPhoneNumber(newPatient.getPhoneNumber());
        underChangePatient.setAddress(newPatient.getAddress());

        patientRepository.save(underChangePatient);
        return newPatient;
    }

    @Override
    public Patient getById(Long id) throws InvalidArgumentException, ObjectNotFoundException{
        if(id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");

        Patient wantedPatient = patientRepository.findById(id).get();

        if(!checkIfEntityExistsInDb(wantedPatient))
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
}
