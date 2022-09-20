import axios from "axios";

const PATIENT_API_BASE_URL = "http://localhost:8080/api/v1/patients";

class PatientService {
  savePatient(patient) {
    return axios.post(PATIENT_API_BASE_URL, patient);
  }

  getPatients() {
    return axios.get(PATIENT_API_BASE_URL);
  }

  deletePatient(id) {
    return axios.delete(PATIENT_API_BASE_URL + "/" + id);
  }

  getPatientById(id) {
    return axios.get(PATIENT_API_BASE_URL + "/" + id);
  }

  updatePatient(patient, id) {
    return axios.put(PATIENT_API_BASE_URL + "/" + id, patient);
  }
}

export default new PatientService();