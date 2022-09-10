package com.nutritiousprog.medfriend.services;

import com.nutritiousprog.medfriend.exceptions.InvalidArgumentException;
import com.nutritiousprog.medfriend.exceptions.ObjectAlreadyExistsException;
import com.nutritiousprog.medfriend.exceptions.ObjectNotFoundException;
import com.nutritiousprog.medfriend.model.Appointment;
import com.nutritiousprog.medfriend.model.Treatment;
import com.nutritiousprog.medfriend.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService implements BasicService<Appointment> {
    private AppointmentRepository appointmentRepository;
    private TreatmentService treatmentService;
    private PatientService patientService;

    @Override
    public Appointment create(Appointment appointment) throws InvalidArgumentException, ObjectAlreadyExistsException{
        if(appointment == null)
            throw new InvalidArgumentException("Passed appointment is invalid (null).");
        if(checkIfEntityExistsInDb(appointment))
            throw new ObjectAlreadyExistsException("There cannot be two appointments at the same  time. Creating appointment failed.");
        if(appointment.getPatient() ==null || appointment.getTreatment() == null || appointment.getDateTime() == null)
            throw new InvalidArgumentException("Passed appointment has invalid parameters.");

        if(!patientService.checkIfEntityExistsInDb(appointment.getPatient()))
            patientService.create(appointment.getPatient());

        appointmentRepository.save(appointment);
        return appointment;
    }

    @Override
    public boolean delete(Long id) throws InvalidArgumentException, ObjectNotFoundException{
        if(id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");

        boolean exists = appointmentRepository.existsById(id);
        if (!exists)
            throw new ObjectNotFoundException("Object not found in database. Deleting address failed.");

        appointmentRepository.deleteById(id);

        return true;
    }

    @Override
    public Appointment update(Long id, Appointment newAppointment) throws InvalidArgumentException, NullPointerException, ObjectAlreadyExistsException{
        if (id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");
        if (newAppointment == null)
            throw new NullPointerException("New appointment is null.");

        Appointment underChangeAppointment = appointmentRepository.findById(id).get();

        if (!checkIfEntityExistsInDb(underChangeAppointment))
            throw new ObjectNotFoundException("Object with given id was not found in database.");

        underChangeAppointment.setPatient(newAppointment.getPatient());
        underChangeAppointment.setTreatment(newAppointment.getTreatment());
        underChangeAppointment.setDateTime(newAppointment.getDateTime());

        patientService.create(newAppointment.getPatient());
        for(Treatment t : newAppointment.getTreatment()) treatmentService.create(t);
        appointmentRepository.save(underChangeAppointment);
        return newAppointment;
    }

    @Override
    public Appointment getById(Long id) throws InvalidArgumentException, ObjectNotFoundException {
        if(id <= 0)
            throw new InvalidArgumentException("Passed id is invalid.");

        Appointment wantedAppointment = appointmentRepository.findById(id).get();

        if(!checkIfEntityExistsInDb(wantedAppointment))
            throw new ObjectNotFoundException("Object with given id was not found in database.");

        return wantedAppointment;
    }

    @Override
    public List<Appointment> getAll() {
        return (List<Appointment>) appointmentRepository.findAll();
    }

    @Override
    public boolean checkIfEntityExistsInDb(Appointment appointment) {
        Iterable<Appointment> currentAppointments = appointmentRepository.findAll();

        for(Appointment a : currentAppointments) {
            if(appointment.equals(a)) {
                return true;
            }
        }
        return false;
    }
}
