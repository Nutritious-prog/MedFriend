package com.nutritiousprog.medfriend.repositories;

import com.nutritiousprog.medfriend.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
}
