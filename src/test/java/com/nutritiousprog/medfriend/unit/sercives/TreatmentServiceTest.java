package com.nutritiousprog.medfriend.unit.sercives;

import com.nutritiousprog.medfriend.exceptions.InvalidArgumentException;
import com.nutritiousprog.medfriend.exceptions.ObjectAlreadyExistsException;
import com.nutritiousprog.medfriend.exceptions.ObjectNotFoundException;
import com.nutritiousprog.medfriend.model.Treatment;
import com.nutritiousprog.medfriend.repositories.TreatmentRepository;
import com.nutritiousprog.medfriend.services.TreatmentService;
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
public class TreatmentServiceTest {
    @Mock
    private TreatmentRepository treatmentRepository;
    private AutoCloseable autoCloseable;
    private TreatmentService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new TreatmentService(treatmentRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    /** CREATE */

    @Test
    void createTreatmentWithValidInput() {
        //given
        Treatment t = new Treatment("test", 150.00);

        //when
        underTest.create(t);

        //then
        ArgumentCaptor<Treatment> argumentCaptor = ArgumentCaptor.forClass(Treatment.class);

        verify(treatmentRepository).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(t);
    }

    @Test
    void createTreatmentWithNullInput() {
        //given
        Treatment t = null;

        //when
        assertThatThrownBy(
                () -> underTest.create(t))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed treatment is invalid (null).");

        //then
        verify(treatmentRepository, never()).save(any());
    }

    @Test
    void createTreatmentThatAlreadyExists() {
        //given
        Treatment t = new Treatment("Test", 150.00);
        List<Treatment> treatments = new ArrayList<>();
        treatments.add(t);
        underTest.create(t);
        given(treatmentRepository.findAll()).willReturn(treatments);

        //when
        assertThatThrownBy(
                () -> underTest.create(t))
                .isInstanceOf(ObjectAlreadyExistsException.class)
                .hasMessageContaining("The same object was already found in database. Creating treatment failed.");

        //then
        verify(treatmentRepository, times(1)).save(any());
    }

    @Test
    void createTreatmentWithInvalidName(){
        //given
        Treatment t = new Treatment("", 150.00);

        //when
        assertThatThrownBy(
                () -> underTest.create(t))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed treatment has invalid parameters.");

        //then
        verify(treatmentRepository, never()).save(any());
    }

    @Test
    void createTreatmentWithInvalidPrice(){
        //given
        Treatment t = new Treatment("Test", -150.00);

        //when
        assertThatThrownBy(
                () -> underTest.create(t))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed treatment has invalid parameters.");

        //then
        verify(treatmentRepository, never()).save(any());
    }

    /** DELETE */

    @Test
    void deleteTreatmentWithValidInput() {
        // given
        long id = 10;
        given(treatmentRepository.existsById(id)).willReturn(true);

        // when
        underTest.delete(id);

        // then
        verify(treatmentRepository).deleteById(id);
    }

    @Test
    void deleteTreatmentThrowingExcWhenIdLessThanZero(){
        //given
        long id = -10;

        //when
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed argument is invalid.");

        //then
        verify(treatmentRepository, never()).deleteById(any());
    }

    @Test
    void deleteTreatmentThrowingExcWhenTreatmentNotFound(){
        //given
        long id = 10;
        given(treatmentRepository.existsById(id)).willReturn(false);

        //when
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessageContaining("Object not found in database. Deleting address failed.");

        //then
        verify(treatmentRepository, never()).deleteById(any());
    }

    /** UPDATE */

    @Test
    void updateTreatmentWithValidInput() {
        //given
        Treatment t = new Treatment("Test", 150.00);
        long id = 10;
        given(treatmentRepository.findById(id)).willReturn(Optional.of(t));
        given(treatmentRepository.existsById(id)).willReturn(true);
        Treatment t2 = new Treatment("Test2", 250.00);

        //when
        underTest.update(id, t2);

        //then
        assertThat(t.getName()).isEqualTo(t2.getName());
        assertThat(t.getPrice()).isEqualTo(t2.getPrice());

        verify(treatmentRepository).save(t);
    }

    @Test
    void updateAddressWithIdLessThanZero() {
        //given
        long id = -10;
        Treatment t = new Treatment("Test", 150.00);

        //when
        assertThatThrownBy(() -> underTest.update(id, t))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed id is invalid.");

        //then
        verify(treatmentRepository, never()).save(any());
    }

    @Test
    void updateAddressWithNullAddress() {
        //given
        long id = 10;
        Treatment t = null;

        //when
        assertThatThrownBy(() -> underTest.update(id, t))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("New treatment is null.");

        //then
        verify(treatmentRepository, never()).save(any());
    }

    @Test
    void updateAddressThatDoesNotExist() {
        //given
        long id = 10;
        Treatment t = new Treatment("Test", 150.00);
        assertThat(treatmentRepository.existsById(id)).isFalse();

        //when
        assertThatThrownBy(() -> underTest.update(id, t))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");

        //then
        verify(treatmentRepository, never()).save(any());
    }

    @Test
    void updateTreatmentWithInvalidName(){
        //given
        long id = 10;
        Treatment t = new Treatment("", 150.00);

        //when
        assertThatThrownBy(
                () -> underTest.update(id, t))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed treatment has invalid parameters.");

        //then
        verify(treatmentRepository, never()).save(any());
    }

    @Test
    void updateTreatmentWithInvalidPrice(){
        //given
        long id = 10;
        Treatment t = new Treatment("Test", -150.00);

        //when
        assertThatThrownBy(
                () -> underTest.update(id, t))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed treatment has invalid parameters.");

        //then
        verify(treatmentRepository, never()).save(any());
    }

    /** getById */

    @Test
    void getByIdWithValidInput() {
        //given
        long id = 10;
        Treatment t = new Treatment("Test", 150.00);

        given(treatmentRepository.findById(id)).willReturn(Optional.of(t));

        //when
        underTest.getById(id);

        //then
        verify(treatmentRepository).findById(id);
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
        verify(treatmentRepository, never()).findById(any());
    }

    /** checkIfEntityExistsInDb */

    @Test
    void checkIfEntityExistsInDb() {
        //given
        Treatment t = new Treatment("Test", 150.00);
        List<Treatment> treatments = new ArrayList<>();
        treatments.add(t);
        underTest.create(t);
        given(treatmentRepository.findAll()).willReturn(treatments);

        //when
        boolean exists = underTest.checkIfEntityExistsInDb(t);

        //then
        assertThat(exists).isTrue();
    }

    @Test
    void checkIfEntityDoesNotExistsInDb() {
        //given
        Treatment t = new Treatment("Test", 150.00);
        List<Treatment> treatments = new ArrayList<>();
        given(treatmentRepository.findAll()).willReturn(treatments);

        //when
        boolean exists = underTest.checkIfEntityExistsInDb(t);

        //then
        assertThat(exists).isFalse();
    }
}


