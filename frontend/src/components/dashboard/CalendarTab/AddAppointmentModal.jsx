import React, { useState, useEffect } from "react";
import "react-datetime/css/react-datetime.css";
import Datetime from "react-datetime";
import AppointmentService from "../../../services/AppointmentService";
import Multiselect from "multiselect-react-dropdown";

import TreatmentService from "../../../services/TreatmentService";
import PatientService from "../../../services/PatientService";
import DoctorService from "../../../services/DoctorService";

function AddAppointmentModal({ isOpen, onClose, onEventAdded }) {
  const [treatmentsArr, setTreatments] = useState([]);
  const [patientsArr, setPatients] = useState([]);
  const [doctorsArr, setDoctros] = useState([]);
  const [Cdate, setDate] = useState(new Date().toLocaleDateString("fr-FR"));
  const [startDate, setStartDate] = useState(new Date());

  const [loading, setLoading] = useState(true);
  const [appointment, setAppointment] = useState({
    id: "",
    patient: {},
    doctor: {},
    treatment: [],
    dateTime: new Date(),
  });

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await TreatmentService.getTreatments();
        console.log(response.data);
        setTreatments(response.data);
        try {
          const response = await PatientService.getPatients();
          console.log(response.data);
          setPatients(response.data);
          try {
            const response = await DoctorService.getDoctors();
            console.log(response.data);
            setDoctros(response.data);
          } catch (error) {
            console.log(error);
          }
        } catch (error) {
          console.log(error);
        }
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    fetchData();
  }, []);

  const handleChange = (e) => {
    const value = e.target.value;
    setAppointment({ ...appointment, [e.target.name]: value });
    console.log(appointment);
  };

  const saveAppointment = (e) => {
    e.preventDefault();

    console.log(appointment);

    AppointmentService.saveAppointment(appointment)
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });

    onClose();
  };

  const findSeletedElementInPatientsDropdown = async (e) => {
    try {
      const response = await PatientService.getPatientById(e.target.value);
      console.log(response.data);
      setAppointment({ ...appointment, patient: response.data });
    } catch (error) {
      console.log(error);
    }
    console.log(appointment);
  };

  const findSeletedElementInDoctorsDropdown = async (e) => {
    try {
      const response = await DoctorService.getDoctorById(e.target.value);
      console.log(response.data);
      setAppointment({ ...appointment, doctor: response.data });
    } catch (error) {
      console.log(error);
    }
    console.log(appointment);
  };

  const onSelect = async (selectedList, selectedItem) => {
    try {
      const response = await TreatmentService.getTreatmentById(
        selectedItem.cat
      );
      console.log(response.data);
      setAppointment({
        ...appointment,
        treatment: [...appointment.treatment, response.data],
      });
    } catch (error) {
      console.log(error);
    }
    console.log(appointment);
  };

  const onRemove = async (selectedList, removedItem) => {
    let leftTreatments = [];
    for (let i = 0; i < selectedList.length; i++) {
      if (selectedList[i].cat !== removedItem.cat) {
        try {
          const response = await TreatmentService.getTreatmentById(
            selectedList[i].cat
          );
          console.log(response.data);
          leftTreatments.push(response.data);
        } catch (error) {
          console.log(error);
        }
      }
    }
    setAppointment({
      ...appointment,
      treatment: leftTreatments,
    });
  };

  return (
    <>
      <div className="justify-center items-center flex overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none">
        <div className="relative w-auto my-6 mx-auto max-w-3xl">
          {/*content*/}
          <div className="border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-white outline-none focus:outline-none">
            {/*header*/}
            <div className="flex items-start justify-between p-5 border-b border-solid border-slate-200 rounded-t">
              <h3 className="text-3xl font-semibold">Create New Appointment</h3>
              <button
                className="p-1 ml-auto bg-transparent border-0 text-black opacity-5 float-right text-3xl leading-none font-semibold outline-none focus:outline-none"
                onClick={() => onClose()}
              >
                <span className="bg-transparent text-black opacity-5 h-6 w-6 text-2xl block outline-none focus:outline-none">
                  ×
                </span>
              </button>
            </div>
            {/*body*/}
            <div className="relative p-6 flex-auto">
              <label>Select Treatments</label>
              <Multiselect
                displayValue="key"
                onKeyPressFn={function noRefCheck() {}}
                onRemove={onRemove}
                onSearch={function noRefCheck() {}}
                onSelect={onSelect}
                options={treatmentsArr.map((treatment) => ({
                  cat: treatment.id,
                  key: treatment.name,
                }))}
              />

              <div className="flex flex-col">
                <label for="patients">Choose a patient:</label>
                <select
                  id="patient"
                  name="patient"
                  onChange={(e) => findSeletedElementInPatientsDropdown(e)}
                >
                  <option>Select...</option>
                  {patientsArr.map((patient) => (
                    <option value={patient.id}>{patient.name}</option>
                  ))}
                </select>
              </div>

              <div className="flex flex-col">
                <label for="doctors">Choose a doctor:</label>
                <select
                  id="doctor"
                  name="doctor"
                  onChange={(e) => findSeletedElementInDoctorsDropdown(e)}
                >
                  <option>Select...</option>
                  {doctorsArr.map((doctor) => (
                    <option value={doctor.id}>Dr. {doctor.name}</option>
                  ))}
                </select>
              </div>

              <div className="flex flex-col">
                <label for="patients">Choose a date:</label>
                <Datetime
                  name="dateTime"
                  initialValue={new Date()}
                  onChange={(d) => {
                    setAppointment({
                      ...appointment,
                      dateTime: d.format("YYYY-MM-DD HH:mm"),
                    });
                  }}
                />
              </div>
            </div>
            {/*footer*/}
            <div className="flex items-center justify-end p-6 border-t border-solid border-slate-200 rounded-b">
              <button
                className="text-red-500 border-white background-transparent font-bold uppercase px-6 py-2 text-sm outline-none focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                type="button"
                onClick={() => onClose()}
              >
                Close
              </button>
              <button
                className="bg-emerald-500 border-emerald-500 text-white active:bg-emerald-600 font-bold uppercase text-sm px-6 py-3 rounded shadow hover:shadow-lg outline-none hover:border-blue-700 focus:outline-none mr-1 mb-1 ease-linear transition-all duration-150"
                type="button"
                onClick={(e) => saveAppointment(e)}
              >
                Save Changes
              </button>
            </div>
          </div>
        </div>
      </div>
      <div className="opacity-25 fixed inset-0 z-40 bg-black"></div>
    </>
  );
}

export default AddAppointmentModal;
