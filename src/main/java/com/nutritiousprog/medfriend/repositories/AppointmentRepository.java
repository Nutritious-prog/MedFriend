package com.nutritiousprog.medfriend.repositories;

import com.nutritiousprog.medfriend.model.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}
