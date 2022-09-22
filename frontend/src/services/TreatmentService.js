import axios from "axios";

const TREATMENT_API_BASE_URL = "http://localhost:8080/api/v1/treatments";

class TreatmentService {
  saveTreatment(treatment) {
    return axios.post(TREATMENT_API_BASE_URL, treatment);
  }

  getTreatments() {
    return axios.get(TREATMENT_API_BASE_URL);
  }

  deleteTreatment(id) {
    return axios.delete(TREATMENT_API_BASE_URL + "/" + id);
  }

  getTreatmentById(id) {
    return axios.get(TREATMENT_API_BASE_URL + "/" + id);
  }

  updateTreatment(treatment, id) {
    return axios.put(TREATMENT_API_BASE_URL + "/" + id, treatment);
  }
}

export default new TreatmentService();