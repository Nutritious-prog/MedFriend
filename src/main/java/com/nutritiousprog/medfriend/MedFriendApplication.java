package com.nutritiousprog.medfriend;

import com.nutritiousprog.medfriend.model.Address;
import com.nutritiousprog.medfriend.model.Patient;
import com.nutritiousprog.medfriend.repositories.AddressRepository;
import com.nutritiousprog.medfriend.repositories.PatientRepository;
import com.nutritiousprog.medfriend.services.AddressService;
import com.nutritiousprog.medfriend.services.PatientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MedFriendApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext =
                SpringApplication.run(MedFriendApplication.class, args);

        AddressRepository addressRepository =
                configurableApplicationContext.getBean(AddressRepository.class);

        PatientRepository patientRepository =
                configurableApplicationContext.getBean(PatientRepository.class);

        AddressService addressService = new AddressService(addressRepository);
        PatientService patientService = new PatientService(patientRepository,addressRepository);

        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");

        Address address2 = new Address("Short 8a", "Birmingham", "98-765");
        Patient p2 = new Patient("Adam Nowak", address2, "987654321");

        patientService.delete(1L);


    }

}
