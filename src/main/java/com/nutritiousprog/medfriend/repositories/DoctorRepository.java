package com.nutritiousprog.medfriend.repositories;

import com.nutritiousprog.medfriend.model.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
}
