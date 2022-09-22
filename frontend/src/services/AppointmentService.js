import axios from "axios";

const APPOINTMENT_API_BASE_URL = "http://localhost:8080/api/v1/appointments";

class AppointmentService {
  saveAppointment(appointment) {
    return axios.post(APPOINTMENT_API_BASE_URL, appointment);
  }

  getAppointments() {
    return axios.get(APPOINTMENT_API_BASE_URL);
  }

  deleteAppointment(id) {
    return axios.delete(APPOINTMENT_API_BASE_URL + "/" + id);
  }

  getAppointmentById(id) {
    return axios.get(APPOINTMENT_API_BASE_URL + "/" + id);
  }

  updateAppointment(appointment, id) {
    return axios.put(APPOINTMENT_API_BASE_URL + "/" + id, appointment);
  }
}

export default new AppointmentService();