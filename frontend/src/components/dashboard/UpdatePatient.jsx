import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import PatientService from "../../services/PatientService";
import { useStateContext } from "../../contexts/ContextProvider";

import Navbar from "./Navbar";
import Sidebar from "./Sidebar";

import { FiSettings } from "react-icons/fi";
import { TooltipComponent } from "@syncfusion/ej2-react-popups";

function UpdatePatient() {
  const { activeMenu } = useStateContext();

  const { id } = useParams();
  const navigate = useNavigate();
  const [patient, setPatient] = useState({
    id: id,
    name: "",
    phoneNumber: "",
    address: { street: "", city: "", postalCode: "" },
  });

  const handleChange = (e) => {
    const value = e.target.value;
    setPatient({ ...patient, [e.target.name]: value });
  };

  const handleAddresChange = (e) => {
    const value = e.target.value;

    const patAddress = {
      ...patient.address,
      [e.target.name]: value,
    };

    setPatient({ ...patient, address: patAddress });
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await PatientService.getPatientById(patient.id);
        setPatient(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, []);

  const updatePatient = (e) => {
    e.preventDefault();
    console.log(patient);
    PatientService.updatePatient(patient, id)
      .then((response) => {
        navigate("/dashboard/patients");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <div>
      <div className="flex relative dark:bg-main-dark-bg">
        <div className="fixed right-4 bottom-4" style={{ zIndex: "1000" }}>
          <TooltipComponent content="Settings">
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

            <div className="flex max-w-2xl mx-auto shadow border-b">
              <div className="px-8 py-8">
                <div className="font-thin text-2xl tracking-wider">
                  <h1>Update Patient</h1>
                </div>
                <div className="items-center justify-center h-14 w-full my-4">
                  <label className="block text-gray-600 text-sm font-normal">
                    Full Name
                  </label>
                  <input
                    type="text"
                    name="name"
                    value={patient.name}
                    onChange={(e) => handleChange(e)}
                    className="h-10 w-96 border mt-2 px-2 py-2 shadow"
                  ></input>
                </div>
                <div className="items-center justify-center h-14 w-full my-4">
                  <label className="block text-gray-600 text-sm font-normal">
                    Phone Number
                  </label>
                  <input
                    type="text"
                    name="phoneNumber"
                    value={patient.phoneNumber}
                    onChange={(e) => handleChange(e)}
                    className="h-10 w-96 border mt-2 px-2 py-2 shadow"
                  ></input>
                </div>
                <div className="items-center justify-center h-14 w-full my-4">
                  <label className="block text-gray-600 text-sm font-normal">
                    Street
                  </label>
                  <input
                    type="text"
                    name="street"
                    value={patient.address.street}
                    onChange={(e) => handleAddresChange(e)}
                    className="h-10 w-96 border mt-2 px-2 py-2 shadow"
                  ></input>
                </div>

                <div className="items-center justify-center h-14 w-full my-4">
                  <label className="block text-gray-600 text-sm font-normal">
                    City
                  </label>
                  <input
                    type="text"
                    name="city"
                    value={patient.address.city}
                    onChange={(e) => handleAddresChange(e)}
                    className="h-10 w-96 border mt-2 px-2 py-2 shadow"
                  ></input>
                </div>

                <div className="items-center justify-center h-14 w-full my-4">
                  <label className="block text-gray-600 text-sm font-normal">
                    Postal Code
                  </label>
                  <input
                    type="text"
                    name="postalCode"
                    value={patient.address.postalCode}
                    onChange={(e) => handleAddresChange(e)}
                    className="h-10 w-96 border mt-2 px-2 py-2 shadow"
                  ></input>
                </div>

                <div className="items-center justify-center h-14 w-full my-4 space-x-4 pt-4">
                  <button
                    onClick={updatePatient}
                    className="rounded text-white font-semibold bg-blue-700 hover:bg-white hover:text-blue-700 py-2 px-6"
                    >
                    Update
                  </button>
                  <button
                    onClick={() => navigate("/dashboard/patients")}
                    className="rounded text-black font-semibold bg-white hover:bg-blue-200 py-2 px-6"
                 >
                    Cancel
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default UpdatePatient;
