import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Sidebar from "./Sidebar";
import { useStateContext } from "../../contexts/ContextProvider";
import Patient from "./Patient";
import Navbar from "./Navbar";

import PatientService from "../../services/PatientService";

import { FiSettings } from "react-icons/fi";
import { TooltipComponent } from "@syncfusion/ej2-react-popups";

function Patients() {
  const navigate = useNavigate();
  const { activeMenu } = useStateContext();
  const editing = { allowDeleting: true, allowEditing: true , allowAdding: true};

  const [loading, setLoading] = useState(true);
  const [patients, setPatients] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await PatientService.getPatients();
        console.log(response.data);
        setPatients(response.data);
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    fetchData();
  }, []);

  const deletePatient = (e, id) => {
    e.preventDefault();
    PatientService.deletePatient(id).then((res) => {
      if (patients) {
        setPatients((prevElement) => {
          return prevElement.filter((patient) => patient.id !== id);
        });
      }
    });
  };
  return (
    <div>
      <div className="flex relative dark:bg-main-dark-bg">
        <div className="fixed right-4 bottom-4" style={{ zIndex: "1000" }}>
          <TooltipComponent content="Settings" >
            <button
              type="button"
              style={{ background: "blue", borderRadius: "50%" }}
              className="text-3xl text-white p-3 hover:drop-shadow-xl hover:bg-light-gray"
            >
              <FiSettings />
            </button>
          </TooltipComponent>
        </div>

        {activeMenu ? (
          <div className="w-72 sidebar fixed dark:bg-secondary-dark-bg bg-white ">
            <Sidebar />
          </div>
        ) : (
          <div className="w-0 dark:bg-secondary-dark-bg">
            <Sidebar />
          </div>
        )}

        <div
          className={
            activeMenu
              ? "dark:bg-main-dark-bg  bg-main-bg min-h-screen md:ml-72 w-full  "
              : "bg-main-bg dark:bg-main-dark-bg  w-full min-h-screen flex-2 "
          }
        >
          <div className="fixed md:static bg-main-bg dark:bg-main-dark-bg navbar w-full ">
            <Navbar />

            <div className="container mx-auto my-8">
      <div className="h-12">
        <button
          onClick={() => navigate("/dashboard/patients/add")}
          className="rounded bg-blue-700 text-white px-6 py-2 font-semibold">
          Add Patient
        </button>
      </div>
      <div className="flex shadow border-b mt-3">
        <table className="min-w-full">
          <thead className="bg-gray-50">
            <tr>
              <th className="text-left font-medium text-gray-500 uppercase tracking-wider py-3 px-6">
                Full Name
              </th>
              <th className="text-left font-medium text-gray-500 uppercase tracking-wider py-3 px-6">
                Phone Number
              </th>
              <th className="text-left font-medium text-gray-500 uppercase tracking-wider py-3 px-6">
                Street
              </th>
              <th className="text-left font-medium text-gray-500 uppercase tracking-wider py-3 px-6">
                City
              </th>
              <th className="text-left font-medium text-gray-500 uppercase tracking-wider py-3 px-6">
                Postal Code
              </th>
              <th className="text-right font-medium text-gray-500 uppercase tracking-wider py-3 px-6">
                Actions
              </th>
            </tr>
          </thead>
          {!loading && (
            <tbody className="bg-white">
              {patients.map((patient) => (
                <Patient
                  patient={patient}
                  deletePatient={deletePatient}
                  key={patient.id}></Patient>
              ))}
            </tbody>
          )}
        </table>
      </div>
    </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Patients;
