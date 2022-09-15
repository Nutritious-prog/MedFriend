package com.nutritiousprog.medfriend.unit.sercives;

import com.nutritiousprog.medfriend.exceptions.InvalidArgumentException;
import com.nutritiousprog.medfriend.exceptions.ObjectAlreadyExistsException;
import com.nutritiousprog.medfriend.exceptions.ObjectNotFoundException;
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
import java.util.NoSuchElementException;
import java.util.Optional;

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
        ArgumentCaptor<Address> argumentCaptorAddress = ArgumentCaptor.forClass(Address.class);

        verify(patientRepository).save(argumentCaptor.capture());
        verify(addressRepository).save(argumentCaptorAddress.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(p);
        assertThat(argumentCaptorAddress.getValue()).isEqualTo(address);
    }

    @Test
    void createPatientWithNullInput() {
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
    void createPatientThatAlreadyExists() {
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
    void createPatientWithInvalidName(){
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
    void createPatientWithInvalidAddress(){
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
    void createPatientWithInvalidPhoneNumber(){
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

    /** DELETE */

    @Test
    void deletePatientWithValidInput() {
        // given
        long id = 10;
        given(patientRepository.existsById(id)).willReturn(true);

        // when
        underTest.delete(id);

        // then
        verify(patientRepository).deleteById(id);
    }

    @Test
    void deletePatientThrowingExcWhenIdLessThanZero(){
        //given
        long id = -10;

        //when
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed id is invalid.");

        //then
        verify(patientRepository, never()).deleteById(any());
    }

    @Test
    void deletePatientThrowingExcWhenPatientNotFound(){
        //given
        long id = 10;
        given(patientRepository.existsById(id)).willReturn(false);

        //when
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessageContaining("Object not found in database. Deleting patient failed.");

        //then
        verify(patientRepository, never()).deleteById(any());
    }

    /** UPDATE */

    @Test
    void updatePatientWithValidInput() {
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        long id = 10;
        given(patientRepository.findById(id)).willReturn(Optional.of(p));
        given(patientRepository.existsById(id)).willReturn(true);
        Address address2 = new Address("Short 8a", "Birmingham", "98-765");
        Patient p2 = new Patient("Adam Nowak", address2, "987654321");

        //when
        underTest.update(id, p2);

        //then
        assertThat(p.getName()).isEqualTo(p2.getName());
        assertThat(p.getPhoneNumber()).isEqualTo(p2.getPhoneNumber());
        assertThat(p.getAddress()).isEqualTo(p2.getAddress());

        verify(patientRepository).save(p);
    }

    @Test
    void updatePatientWithIdLessThanZero() {
        //given
        long id = -10;
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");

        //when
        assertThatThrownBy(() -> underTest.update(id, p))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed id is invalid.");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void updatePatientWithNullPatientParameter() {
        //given
        long id = 10;
        Patient p = null;

        //when
        assertThatThrownBy(() -> underTest.update(id, p))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("New patient is null.");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void updatePatientThatDoesNotExist() {
        //given
        long id = 10;
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        assertThat(patientRepository.existsById(id)).isFalse();

        //when
        assertThatThrownBy(() -> underTest.update(id, p))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void updatePatientWithInvalidName(){
        //given
        long id = 10;
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("", address, "123456789");

        //when
        assertThatThrownBy(
                () -> underTest.update(id, p))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void updatePatientWithInvalidPhoneNumber(){
        //given
        long id = 10;
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "12345");

        //when
        assertThatThrownBy(
                () -> underTest.update(id, p))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void updatePatientWithInvalidAddress(){
        //given
        long id = 10;
        Address address = null;
        Patient p = new Patient("Jan Kowalski", address, "123456789");

        //when
        assertThatThrownBy(
                () -> underTest.update(id, p))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");

        //then
        verify(patientRepository, never()).save(any());
    }

    /** getById */

    @Test
    void getByIdWithValidInput() {
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        long id = 10;

        given(patientRepository.findById(id)).willReturn(Optional.of(p));

        //when
        underTest.getById(id);

        //then
        verify(patientRepository).findById(id);
    }

    @Test
    void getByIdWithInvalidIdParameter() {
        //given
        long id = -10;

        //when
        assertThatThrownBy(() -> underTest.getById(id))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed id is invalid.");

        //then
        verify(patientRepository, never()).findById(any());
    }

    /** checkIfEntityExistsInDb */

    @Test
    void checkIfEntityExistsInDb() {
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        List<Patient> patients = new ArrayList<>();
        patients.add(p);
        underTest.create(p);
        given(patientRepository.findAll()).willReturn(patients);

        //when
        boolean exists = underTest.checkIfEntityExistsInDb(p);

        //then
        assertThat(exists).isTrue();
    }

    @Test
    void checkIfEntityDoesNotExistsInDb() {
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        List<Patient> patients = new ArrayList<>();
        given(patientRepository.findAll()).willReturn(patients);

        //when
        boolean exists = underTest.checkIfEntityExistsInDb(p);

        //then
        assertThat(exists).isFalse();
    }

    /** isPhoneNumberValid */

    @Test
    void isPhoneNumberValid() {
        //given
        String phoneNumber = "123456789";

        //when
        boolean isInvalid = underTest.isPhoneNumberValid(phoneNumber);

        //then
        assertThat(isInvalid).isTrue();
    }

    @Test
    void isPhoneNumberInValid() {
        //given
        String phoneNumber = "123456";

        //when
        boolean isInvalid = underTest.isPhoneNumberValid(phoneNumber);

        //then
        assertThat(isInvalid).isFalse();
    }
}
