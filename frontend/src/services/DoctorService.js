import axios from "axios";

const DOCTOR_API_BASE_URL = "http://localhost:8080/api/v1/doctors";

class DoctorService {
  saveDoctor(doctor) {
    return axios.post(DOCTOR_API_BASE_URL, doctor);
  }

  getDoctors() {
    return axios.get(DOCTOR_API_BASE_URL);
  }

  deleteDoctor(id) {
    return axios.delete(DOCTOR_API_BASE_URL + "/" + id);
  }

  getDoctorById(id) {
    return axios.get(DOCTOR_API_BASE_URL + "/" + id);
  }

  updateDoctor(doctor, id) {
    return axios.put(DOCTOR_API_BASE_URL + "/" + id, doctor);
  }
}

export default new DoctorService();