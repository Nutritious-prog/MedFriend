package com.nutritiousprog.medfriend.unit.sercives;

import com.nutritiousprog.medfriend.exceptions.InvalidArgumentException;
import com.nutritiousprog.medfriend.exceptions.ObjectAlreadyExistsException;
import com.nutritiousprog.medfriend.exceptions.ObjectNotFoundException;
import com.nutritiousprog.medfriend.model.Address;
import com.nutritiousprog.medfriend.repositories.AddressRepository;
import com.nutritiousprog.medfriend.services.AddressService;
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

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    private AutoCloseable autoCloseable;
    private AddressService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new AddressService(addressRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createAddressWithValidInput() {
        //given
        Address a = new Address("Long 14c", "London", "12-345");

        //when
        underTest.create(a);

        //then
        ArgumentCaptor<Address> argumentCaptor = ArgumentCaptor.forClass(Address.class);

        verify(addressRepository).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(a);
    }

    @Test
    void createAddressWithNullInput() {
        //given
        Address a = null;

        //when
        assertThatThrownBy(
                () -> underTest.create(a))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed address is invalid (null).");

        //then
        verify(addressRepository, never()).save(any());
    }

    @Test
    void createAddressThatAlreadyExists() {
        //given
        Address a1 = new Address("Colourful 5b", "London", "12-345");
        List<Address> addresses = new ArrayList<>();
        addresses.add(a1);
        underTest.create(a1);
        given(addressRepository.findAll()).willReturn(addresses);

        //when
        assertThatThrownBy(
                () -> underTest.create(a1))
                .isInstanceOf(ObjectAlreadyExistsException.class)
                .hasMessageContaining("The same object was already found in database. Creating address failed.");

        //then
        verify(addressRepository, times(1)).save(any());
    }

    @Test
    void createAddressWithInvalidStreet(){
        //given
        Address a = new Address("", "London", "12-345");

        //when
        assertThatThrownBy(
                () -> underTest.create(a))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed address has invalid parameters.");

        //then
        verify(addressRepository, never()).save(any());
    }

    @Test
    void createAddressWithInvalidCity(){
        //given
        Address a = new Address("Long 15b", "", "12-345");

        //when
        assertThatThrownBy(
                () -> underTest.create(a))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed address has invalid parameters.");

        //then
        verify(addressRepository, never()).save(any());
    }

    @Test
    void createAddressWithInvalidPostalCode(){
        //given
        Address a = new Address("Long 15b", "London", "1234");

        //when
        assertThatThrownBy(
                () -> underTest.create(a))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed address has invalid parameters.");

        //then
        verify(addressRepository, never()).save(any());
    }

    @Test
    void deleteAddressWithValidInput() {
        // given
        long id = 10;
        given(addressRepository.existsById(id)).willReturn(true);

        // when
        underTest.delete(id);

        // then
        verify(addressRepository).deleteById(id);
    }

    @Test
    void deleteAddressThrowingExcWhenIdLessThanZero(){
        //given
        long id = -10;

        //when
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed argument is invalid.");

        //then
        verify(addressRepository, never()).deleteById(any());
    }

    @Test
    void deleteAddressThrowingExcWhenClientNotFound(){
        //given
        long id = 10;
        given(addressRepository.existsById(id)).willReturn(false);

        //when
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessageContaining("Object not found in database. Deleting address failed.");

        //then
        verify(addressRepository, never()).deleteById(any());
    }

    @Test
    void updateAddressWithValidInput() {
        //given
        Address a1 = new Address("Colourful 5b", "London", "12-345");
        long id = 10;
        given(addressRepository.findById(id)).willReturn(Optional.of(a1));
        given(addressRepository.existsById(id)).willReturn(true);
        Address a2 = new Address("Bright 8a", "Brighton", "98-765");

        //when
        underTest.update(id, a2);

        //then
        assertThat(a1.getStreet()).isEqualTo(a2.getStreet());
        assertThat(a1.getCity()).isEqualTo(a2.getCity());
        assertThat(a1.getPostalCode()).isEqualTo(a2.getPostalCode());

        verify(addressRepository).save(a1);
    }

    @Test
    void updateAddressWithIdLessThanZero() {
        //given
        long id = -10;
        Address a1 = new Address("Colourful 5b", "London", "12-345");

        //when
        assertThatThrownBy(() -> underTest.update(id, a1))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed id is invalid.");

        //then
        verify(addressRepository, never()).save(any());
    }

    @Test
    void updateAddressWithNullAddress() {
        //given
        long id = 10;
        Address a1 = null;

        //when
        assertThatThrownBy(() -> underTest.update(id, a1))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("New address is null.");

        //then
        verify(addressRepository, never()).save(any());
    }

    @Test
    void updateAddressThatDoesNotExist() {
        //given
        long id = 10;
        Address a1 = new Address("Colourful 5b", "London", "12-345");
        assertThat(addressRepository.existsById(id)).isFalse();

        //when
        assertThatThrownBy(() -> underTest.update(id, a1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");

        //then
        verify(addressRepository, never()).save(any());
    }

    @Test
    void getByIdWithValidInput() {
        //given
        long id = 10;
        Address a = new Address("Long 14c", "London", "12-345");

        given(addressRepository.findById(id)).willReturn(Optional.of(a));

        //when
        underTest.getById(id);

        //then
        verify(addressRepository).findById(id);
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
        verify(addressRepository, never()).findById(any());
    }

    @Test
    void checkIfEntityExistsInDb() {
        //given
        Address a1 = new Address("Colourful 5b", "London", "12-345");
        List<Address> addresses = new ArrayList<>();
        addresses.add(a1);
        underTest.create(a1);
        given(addressRepository.findAll()).willReturn(addresses);

        //when
        boolean exists = underTest.checkIfEntityExistsInDb(a1);

        //then
        assertThat(exists).isTrue();
    }

    @Test
    void checkIfEntityDoesNotExistsInDb() {
        //given
        Address a1 = new Address("Colourful 5b", "London", "12-345");
        List<Address> addresses = new ArrayList<>();
        given(addressRepository.findAll()).willReturn(addresses);

        //when
        boolean exists = underTest.checkIfEntityExistsInDb(a1);

        //then
        assertThat(exists).isFalse();
    }

    @Test
    void isPostalCodeValidWithValidInput() {
        //given
        String validPostalCode = "12-345";

        //when
        boolean isValid = underTest.isPostalCodeValid(validPostalCode);

        //then
        assertThat(isValid).isTrue();
    }

    @Test
    void isPostalCodeValidWithInvalidInput() {
        //given
        String invalidPostalCode = "1234";

        //when
        boolean isInvalid = underTest.isPostalCodeValid(invalidPostalCode);

        //then
        assertThat(isInvalid).isFalse();
    }
}
