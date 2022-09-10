package com.nutritiousprog.medfriend.services;

import com.nutritiousprog.medfriend.exceptions.InvalidArgumentException;
import com.nutritiousprog.medfriend.exceptions.ObjectAlreadyExistsException;
import com.nutritiousprog.medfriend.exceptions.ObjectNotFoundException;
import com.nutritiousprog.medfriend.model.Address;
import com.nutritiousprog.medfriend.repositories.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class AddressService implements BasicService<Address> {
    private AddressRepository addressRepository;

    @Override
    public Address create(Address address) throws InvalidArgumentException, ObjectAlreadyExistsException {
        if(address == null)
            throw new InvalidArgumentException("Passed address is invalid (null).");
        if(checkIfEntityExistsInDb(address))
            throw new ObjectAlreadyExistsException("The same object was already found in database. Creating address failed.");
        if(!isPostalCodeValid(address.getPostalCode()) || address.getStreet().isEmpty() || address.getCity().isEmpty())
            throw new InvalidArgumentException("Passed address has invalid parameters.");

        addressRepository.save(address);
        return address;
    }

    @Override
    public boolean delete(Long id) throws InvalidArgumentException, ObjectNotFoundException {
        if(id <= 0)
            throw new InvalidArgumentException("Passed argument is invalid.");

        boolean exists = addressRepository.existsById(id);
        if (!exists)
            throw new ObjectNotFoundException("Object not found in database. Deleting address failed.");

        addressRepository.deleteById(id);
        return true;
    }

    @Override
    public Address update(Long id, Address newAddress) throws InvalidArgumentException, NullPointerException, ObjectNotFoundException{
        if(id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");
        if(newAddress == null)
            throw new NullPointerException("New address is null.");

        Address underChangeAddress = addressRepository.findById(id).get();

        if(!checkIfEntityExistsInDb(underChangeAddress))
            throw new ObjectNotFoundException("Object with given id was not found in database.");

        underChangeAddress.setStreet(newAddress.getStreet());
        underChangeAddress.setCity(newAddress.getCity());
        underChangeAddress.setPostalCode(newAddress.getPostalCode());

        addressRepository.save(underChangeAddress);
        return newAddress;
    }

    @Override
    public Address getById(Long id) throws InvalidArgumentException, ObjectNotFoundException{
        if(id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");

        Address wantedAddress = addressRepository.findById(id).get();

        if(!checkIfEntityExistsInDb(wantedAddress))
            throw new ObjectNotFoundException("Object with given id was not found in database.");

        return wantedAddress;
    }

    @Override
    public List<Address> getAll() {
        return (List<Address>) addressRepository.findAll();
    }

    public boolean checkIfEntityExistsInDb(Address address) {
        Iterable<Address> currentAddresses = addressRepository.findAll();

        for(Address a : currentAddresses) {
            if(address.equals(a)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPostalCodeValid(String postalCode) {
        String regex = "^[0-9]{2}\\-[0-9]{3}$"; // "Available patterns "12-345", "09-876" etc."

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(postalCode);
        return matcher.matches();
    }
}
