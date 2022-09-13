package com.nutritiousprog.medfriend.unit.sercives;

import com.nutritiousprog.medfriend.exceptions.InvalidArgumentException;
import com.nutritiousprog.medfriend.exceptions.ObjectAlreadyExistsException;
import com.nutritiousprog.medfriend.model.Address;
import com.nutritiousprog.medfriend.model.Patient;
import com.nutritiousprog.medfriend.repositories.AddressRepository;
import com.nutritiousprog.medfriend.repositories.PatientRepository;
import com.nutritiousprog.medfriend.services.AddressService;
import com.nutritiousprog.medfriend.services.PatientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private AddressRepository addressRepository;

    private AutoCloseable autoCloseable;
    private PatientService underTest;
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new PatientService(patientRepository, addressRepository);
        addressService = new AddressService(addressRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    /** CREATE */

    @Test
    void createPatientWithValidInput() {
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");

        //when
        underTest.create(p);

        //then
        ArgumentCaptor<Patient> argumentCaptor = ArgumentCaptor.forClass(Patient.class);

        verify(patientRepository).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(p);
    }

    @Test
    void createTreatmentWithNullInput() {
        //given
        Patient p = null;

        //when
        assertThatThrownBy(
                () -> underTest.create(p))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed patient is invalid (null).");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void createTreatmentThatAlreadyExists() {
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        List<Patient> patients = new ArrayList<>();
        patients.add(p);
        underTest.create(p);
        given(patientRepository.findAll()).willReturn(patients);

        //when
        assertThatThrownBy(
                () -> underTest.create(p))
                .isInstanceOf(ObjectAlreadyExistsException.class)
                .hasMessageContaining("The same object was already found in database. Creating patient failed.");

        //then
        verify(patientRepository, times(1)).save(any());
    }

    @Test
    void createTreatmentWithInvalidName(){
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("", address, "123456789");

        //when
        assertThatThrownBy(
                () -> underTest.create(p))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed patient has invalid parameters.");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void createTreatmentWithInvalidAddress(){
        //given
        Address address = null;
        Patient p = new Patient("Jan Kowalski", address, "123456789");

        //when
        assertThatThrownBy(
                () -> underTest.create(p))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed patient has invalid parameters.");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void createTreatmentWithInvalidPhoneNumber(){
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "1234");

        //when
        assertThatThrownBy(
                () -> underTest.create(p))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed patient has invalid parameters.");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void isPhoneNumberValid() {
        //given
        String phoneNumber = "123456789";

        //when
        boolean isInvalid = underTest.isPhoneNumberValid(phoneNumber);

        //then
        assertThat(isInvalid).isTrue();
    }
}
