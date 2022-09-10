package com.nutritiousprog.medfriend.repositories;

import com.nutritiousprog.medfriend.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
