package com.nutritiousprog.medfriend.repositories;

import com.nutritiousprog.medfriend.model.Treatment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentRepository extends CrudRepository<Treatment, Long> {
}
