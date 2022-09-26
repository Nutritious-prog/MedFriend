package com.nutritiousprog.medfriend.services;

import com.nutritiousprog.medfriend.exceptions.InvalidArgumentException;
import com.nutritiousprog.medfriend.exceptions.ObjectAlreadyExistsException;
import com.nutritiousprog.medfriend.exceptions.ObjectNotFoundException;
import com.nutritiousprog.medfriend.model.Doctor;
import com.nutritiousprog.medfriend.repositories.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService implements BasicService<Doctor>{
    private DoctorRepository doctorRepository;

    @Override
    public Doctor create(Doctor doctor) {
        if(doctor == null)
            throw new InvalidArgumentException("Passed treatment is invalid (null).");
        if(checkIfEntityExistsInDb(doctor))
            throw new ObjectAlreadyExistsException("The same object was already found in database. Creating treatment failed.");
        if(doctor.getName().isEmpty() || doctor.getPhoneNumber().isEmpty() || doctor.getRole().isEmpty() || doctor.getSpecialization().isEmpty())
            throw new InvalidArgumentException("Passed treatment has invalid parameters.");

        doctorRepository.save(doctor);
        return doctor;
    }

    @Override
    public boolean delete(Long id) {
        if(id <= 0)
            throw new InvalidArgumentException("Passed argument is invalid.");

        boolean exists = doctorRepository.existsById(id);
        if (!exists)
            throw new ObjectNotFoundException("Object not found in database. Deleting doctor failed.");

        doctorRepository.deleteById(id);
        return true;
    }

    @Override
    public Doctor update(Long id, Doctor newDoctor) throws InvalidArgumentException, NullPointerException, ObjectNotFoundException {
        if (id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");
        if (newDoctor == null)
            throw new NullPointerException("New treatment is null.");
        if(newDoctor.getName().isEmpty() || newDoctor.getPhoneNumber().isEmpty() || newDoctor.getRole().isEmpty() || newDoctor.getSpecialization().isEmpty())
            throw new InvalidArgumentException("Passed treatment has invalid parameters.");

        Doctor underChangeDoctor = doctorRepository.findById(id).get();

        boolean exists = doctorRepository.existsById(id);
        if (!exists)
            throw new ObjectNotFoundException("Object not found in database. Updating address failed.");

        underChangeDoctor.setName(newDoctor.getName());
        underChangeDoctor.setPhoneNumber(newDoctor.getPhoneNumber());
        underChangeDoctor.setSpecialization(newDoctor.getSpecialization());
        underChangeDoctor.setRole(newDoctor.getRole());

        doctorRepository.save(underChangeDoctor);
        return newDoctor;
    }

    @Override
    public Doctor getById(Long id) throws InvalidArgumentException, ObjectNotFoundException {
        if(id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");

        return doctorRepository.findById(id).get();
    }

    @Override
    public List<Doctor> getAll() {
        return (List<Doctor>) doctorRepository.findAll();
    }

    @Override
    public boolean checkIfEntityExistsInDb(Doctor doctor) {
        Iterable<Doctor> currentDoctors = doctorRepository.findAll();

        for(Doctor d : currentDoctors) {
            if(doctor.equals(d)) {
                return true;
            }
        }
        return false;
    }
}
