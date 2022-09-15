package com.nutritiousprog.medfriend.unit.sercives;

import com.nutritiousprog.medfriend.exceptions.InvalidArgumentException;
import com.nutritiousprog.medfriend.exceptions.ObjectAlreadyExistsException;
import com.nutritiousprog.medfriend.exceptions.ObjectNotFoundException;
import com.nutritiousprog.medfriend.model.Address;
import com.nutritiousprog.medfriend.model.Appointment;
import com.nutritiousprog.medfriend.model.Patient;
import com.nutritiousprog.medfriend.model.Treatment;
import com.nutritiousprog.medfriend.repositories.AddressRepository;
import com.nutritiousprog.medfriend.repositories.AppointmentRepository;
import com.nutritiousprog.medfriend.repositories.PatientRepository;
import com.nutritiousprog.medfriend.repositories.TreatmentRepository;
import com.nutritiousprog.medfriend.services.AppointmentService;
import com.nutritiousprog.medfriend.services.PatientService;
import com.nutritiousprog.medfriend.services.TreatmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private TreatmentRepository treatmentRepository;
    private AddressRepository addressRepository;

    private AutoCloseable autoCloseable;
    private AppointmentService underTest;
    private PatientService patientService;
    private TreatmentService treatmentService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new AppointmentService(appointmentRepository, treatmentRepository, patientRepository);
        patientService = new PatientService(patientRepository, addressRepository);
        treatmentService = new TreatmentService(treatmentRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    /** CREATE */

    @Test
    void createAppointmentWithValidInput() {
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        Treatment t = new Treatment("Back rub", 150.00);
        Treatment t2= new Treatment("Joints loosening", 150.00);
        List<Treatment> treatments = Arrays.asList(t, t2);

        Appointment appointment = new Appointment(p, treatments, LocalDateTime.of(2022, 9, 15, 13, 40));

        //when
        underTest.create(appointment);

        //then
        ArgumentCaptor<Appointment> argumentCaptor = ArgumentCaptor.forClass(Appointment.class);
        ArgumentCaptor<Patient> argumentCaptorPatient = ArgumentCaptor.forClass(Patient.class);

        verify(appointmentRepository).save(argumentCaptor.capture());
        verify(patientRepository).save(argumentCaptorPatient.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(appointment);
        assertThat(argumentCaptorPatient.getValue()).isEqualTo(p);
    }

    @Test
    void createAppointmentWithNullInput() {
        //given
        Appointment a = null;

        //when
        assertThatThrownBy(
                () -> underTest.create(a))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed appointment is invalid (null).");

        //then
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void createAppointmentThatAlreadyExists() {
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        Treatment t = new Treatment("Back rub", 150.00);
        Treatment t2= new Treatment("Joints loosening", 150.00);
        List<Treatment> treatments = Arrays.asList(t, t2);

        Appointment appointment = new Appointment(p, treatments, LocalDateTime.of(2022, 9, 15, 13, 40));

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        underTest.create(appointment);
        given(appointmentRepository.findAll()).willReturn(appointments);

        //when
        assertThatThrownBy(
                () -> underTest.create(appointment))
                .isInstanceOf(ObjectAlreadyExistsException.class)
                .hasMessageContaining("There cannot be two appointments at the same  time. Creating appointment failed.");

        //then
        verify(appointmentRepository, times(1)).save(any());
    }

    @Test
    void createAppointmentWithInvalidPatient(){
        //given
        Patient p = null;
        Treatment t = new Treatment("Back rub", 150.00);
        Treatment t2= new Treatment("Joints loosening", 150.00);
        List<Treatment> treatments = Arrays.asList(t, t2);

        Appointment appointment = new Appointment(p, treatments, LocalDateTime.of(2022, 9, 15, 13, 40));

        //when
        assertThatThrownBy(
                () -> underTest.create(appointment))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed appointment has invalid parameters.");

        //then
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void createAppointmentWithInvalidTreatmentList(){
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");

        Appointment appointment = new Appointment(p, null, LocalDateTime.of(2022, 9, 15, 13, 40));

        //when
        assertThatThrownBy(
                () -> underTest.create(appointment))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed appointment has invalid parameters.");

        //then
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void createAppointmentWithInvalidDate(){
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        Treatment t = new Treatment("Back rub", 150.00);
        Treatment t2= new Treatment("Joints loosening", 150.00);
        List<Treatment> treatments = Arrays.asList(t, t2);

        Appointment appointment = new Appointment(p, treatments, null);

        //when
        assertThatThrownBy(
                () -> underTest.create(appointment))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed appointment has invalid parameters.");

        //then
        verify(appointmentRepository, never()).save(any());
    }

    /** DELETE */

    @Test
    void deleteAppointmentWithValidInput() {
        // given
        long id = 10;
        given(appointmentRepository.existsById(id)).willReturn(true);

        // when
        underTest.delete(id);

        // then
        verify(appointmentRepository).deleteById(id);
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
        verify(appointmentRepository, never()).deleteById(any());
    }

    @Test
    void deletePatientThrowingExcWhenPatientNotFound(){
        //given
        long id = 10;
        given(appointmentRepository.existsById(id)).willReturn(false);

        //when
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessageContaining("Object not found in database. Deleting appointment failed.");

        //then
        verify(appointmentRepository, never()).deleteById(any());
    }

    /** UPDATE */

    @Test
    void updateAppointmentWithValidInput() {
        //given
        long id = 10;
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        Treatment t = new Treatment("Back rub", 150.00);
        Treatment t2= new Treatment("Joints loosening", 250.00);
        List<Treatment> treatments = Arrays.asList(t, t2);

        Appointment appointment = new Appointment(p, treatments, LocalDateTime.of(2022, 9, 15, 13, 40));

        given(appointmentRepository.findById(id)).willReturn(Optional.of(appointment));
        given(appointmentRepository.existsById(id)).willReturn(true);

        Address address2 = new Address("Short 8a", "Birmingham", "98-765");
        Patient p2 = new Patient("Adam Nowak", address2, "987654321");
        Treatment t3 = new Treatment("Allergy tests", 350.00);
        Treatment t4= new Treatment("Stress Therapy", 450.00);
        List<Treatment> treatments2 = Arrays.asList(t3, t4);

        Appointment appointment2 = new Appointment(p2, treatments2, LocalDateTime.of(2022, 9, 16, 18, 0));

        //when
        underTest.update(id, appointment2);

        //then
        assertThat(appointment.getPatient()).isEqualTo(appointment2.getPatient());
        assertThat(appointment.getTreatment()).isEqualTo(appointment2.getTreatment());
        assertThat(appointment.getDateTime()).isEqualTo(appointment2.getDateTime());

        verify(appointmentRepository).save(appointment);
    }

    @Test
    void updateAppointmentWithIdLessThanZero() {
        //given
        long id = -10;
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        Treatment t = new Treatment("Back rub", 150.00);
        Treatment t2= new Treatment("Joints loosening", 250.00);
        List<Treatment> treatments = Arrays.asList(t, t2);

        Appointment appointment = new Appointment(p, treatments, LocalDateTime.of(2022, 9, 15, 13, 40));

        //when
        assertThatThrownBy(() -> underTest.update(id, appointment))
                .isInstanceOf(InvalidArgumentException.class)
                .hasMessageContaining("Passed id is invalid.");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void updateAppointmentWithNullAppointmentParameter() {
        //given
        long id = 10;
        Appointment appointment = null;

        //when
        assertThatThrownBy(() -> underTest.update(id, appointment))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("New appointment is null.");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void updateAppointmentThatDoesNotExist() {
        //given
        long id = 10;
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        Treatment t = new Treatment("Back rub", 150.00);
        Treatment t2= new Treatment("Joints loosening", 250.00);
        List<Treatment> treatments = Arrays.asList(t, t2);

        Appointment appointment = new Appointment(p, treatments, LocalDateTime.of(2022, 9, 15, 13, 40));

        assertThat(appointmentRepository.existsById(id)).isFalse();

        //when
        assertThatThrownBy(() -> underTest.update(id, appointment))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void updateAppointmentWithInvalidPatientParameter(){
        //given
        long id = 10;
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = null;
        Treatment t = new Treatment("Back rub", 150.00);
        Treatment t2= new Treatment("Joints loosening", 250.00);
        List<Treatment> treatments = Arrays.asList(t, t2);

        Appointment appointment = new Appointment(p, treatments, LocalDateTime.of(2022, 9, 15, 13, 40));


        //when
        assertThatThrownBy(
                () -> underTest.update(id, appointment))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void updateAppointmentWithInvalidTreatmentListParameter(){
        //given
        long id = 10;
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");


        Appointment appointment = new Appointment(p, null, LocalDateTime.of(2022, 9, 15, 13, 40));


        //when
        assertThatThrownBy(
                () -> underTest.update(id, appointment))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");

        //then
        verify(patientRepository, never()).save(any());
    }

    @Test
    void updateAppointmentWithInvalidDate(){
        //given
        long id = 10;
        Address address = null;
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        Treatment t = new Treatment("Back rub", 150.00);
        Treatment t2= new Treatment("Joints loosening", 250.00);
        List<Treatment> treatments = Arrays.asList(t, t2);

        Appointment appointment = new Appointment(p, treatments, null);


        //when
        assertThatThrownBy(
                () -> underTest.update(id, appointment))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");

        //then
        verify(patientRepository, never()).save(any());
    }

    /** getById */

    @Test
    void getByIdWithValidInput() {
        //given
        long id = 10;
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        Treatment t = new Treatment("Back rub", 150.00);
        Treatment t2= new Treatment("Joints loosening", 250.00);
        List<Treatment> treatments = Arrays.asList(t, t2);

        Appointment appointment = new Appointment(p, treatments, LocalDateTime.of(2022, 9, 15, 13, 40));

        given(appointmentRepository.findById(id)).willReturn(Optional.of(appointment));

        //when
        underTest.getById(id);

        //then
        verify(appointmentRepository).findById(id);
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
        verify(appointmentRepository, never()).findById(any());
    }

    /** checkIfEntityExistsInDb */

    @Test
    void checkIfEntityExistsInDb() {
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        Treatment t = new Treatment("Back rub", 150.00);
        Treatment t2= new Treatment("Joints loosening", 250.00);
        List<Treatment> treatments = Arrays.asList(t, t2);

        Appointment appointment = new Appointment(p, treatments, LocalDateTime.of(2022, 9, 15, 13, 40));

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        underTest.create(appointment);
        given(appointmentRepository.findAll()).willReturn(appointments);

        //when
        boolean exists = underTest.checkIfEntityExistsInDb(appointment);

        //then
        assertThat(exists).isTrue();
    }

    @Test
    void checkIfEntityDoesNotExistsInDb() {
        //given
        Address address = new Address("Long 14c", "London", "12-345");
        Patient p = new Patient("Jan Kowalski", address, "123456789");
        Treatment t = new Treatment("Back rub", 150.00);
        Treatment t2= new Treatment("Joints loosening", 250.00);
        List<Treatment> treatments = Arrays.asList(t, t2);

        Appointment appointment = new Appointment(p, treatments, LocalDateTime.of(2022, 9, 15, 13, 40));

        List<Appointment> appointments = new ArrayList<>();
        given(appointmentRepository.findAll()).willReturn(appointments);

        //when
        boolean exists = underTest.checkIfEntityExistsInDb(appointment);

        //then
        assertThat(exists).isFalse();
    }

}
