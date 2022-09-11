package com.nutritiousprog.medfriend.services;

import com.nutritiousprog.medfriend.exceptions.InvalidArgumentException;
import com.nutritiousprog.medfriend.exceptions.ObjectAlreadyExistsException;
import com.nutritiousprog.medfriend.exceptions.ObjectNotFoundException;
import com.nutritiousprog.medfriend.model.Treatment;
import com.nutritiousprog.medfriend.repositories.TreatmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TreatmentService implements BasicService<Treatment>{
    private TreatmentRepository treatmentRepository;

    @Override
    public Treatment create(Treatment treatment) throws InvalidArgumentException, ObjectAlreadyExistsException{
        if(treatment == null)
            throw new InvalidArgumentException("Passed treatment is invalid (null).");
        if(checkIfEntityExistsInDb(treatment))
            throw new ObjectAlreadyExistsException("The same object was already found in database. Creating treatment failed.");
        if(treatment.getName().isEmpty() || treatment.getPrice() <= 0)
            throw new InvalidArgumentException("Passed treatment has invalid parameters.");

        treatmentRepository.save(treatment);
        return treatment;
    }

    @Override
    public boolean delete(Long id) throws InvalidArgumentException, ObjectNotFoundException{
        if(id <= 0)
            throw new InvalidArgumentException("Passed argument is invalid.");

        boolean exists = treatmentRepository.existsById(id);
        if (!exists)
            throw new ObjectNotFoundException("Object not found in database. Deleting address failed.");

        treatmentRepository.deleteById(id);
        return true;
    }

    @Override
    public Treatment update(Long id, Treatment newTreatment) throws InvalidArgumentException, NullPointerException, ObjectNotFoundException {
        if (id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");
        if (newTreatment == null)
            throw new NullPointerException("New treatment is null.");

        Treatment underChangeTreatment = treatmentRepository.findById(id).get();

        if (!checkIfEntityExistsInDb(underChangeTreatment))
            throw new ObjectNotFoundException("Object with given id was not found in database.");

        underChangeTreatment.setName(newTreatment.getName());
        underChangeTreatment.setPrice(newTreatment.getPrice());

        treatmentRepository.save(underChangeTreatment);
        return newTreatment;
    }

    @Override
    public Treatment getById(Long id) throws InvalidArgumentException, ObjectNotFoundException {
        if(id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");

        Treatment wantedTreatment = treatmentRepository.findById(id).get();

        if(!checkIfEntityExistsInDb(wantedTreatment))
            throw new ObjectNotFoundException("Object with given id was not found in database.");

        return wantedTreatment;
    }

    @Override
    public List<Treatment> getAll() {
        return (List<Treatment>) treatmentRepository.findAll();
    }

    @Override
    public boolean checkIfEntityExistsInDb(Treatment treatment) {
        Iterable<Treatment> currentTreatments = treatmentRepository.findAll();

        for(Treatment t : currentTreatments) {
            if(treatment.equals(t)) {
                return true;
            }
        }
        return false;
    }
}