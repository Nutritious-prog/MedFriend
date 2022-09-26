import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Sidebar from "../Sidebar";
import { useStateContext } from "../../../contexts/ContextProvider";
import DoctorCard from "./DoctorCard";
import Navbar from "../Navbar";

import DoctorService from "../../../services/DoctorService";

import { FiSettings } from "react-icons/fi";
import { TooltipComponent } from "@syncfusion/ej2-react-popups";

import { FaPlus } from "react-icons/fa";

function DoctorsList() {
  const navigate = useNavigate();
  const { activeMenu } = useStateContext();

  const [loading, setLoading] = useState(true);
  const [doctors, setDoctors] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await DoctorService.getDoctors();
        console.log(response.data);
        setDoctors(response.data);
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    fetchData();
  }, []);

  const deleteDoctor = (e, id) => {
    e.preventDefault();
    DoctorService.deleteDoctor(id).then((res) => {
      if (doctors) {
        setDoctors((prevElement) => {
          return prevElement.filter((doctor) => doctor.id !== id);
        });
      }
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

            <div className="container mx-auto my-8">
              <div className="h-12">
                <button
                  onClick={() => navigate("/dashboard/doctors/add")}
                  className="rounded bg-blue-700 text-white px-6 py-2 font-semibold flex"
                >
                  <FaPlus className="mt-auto mb-auto mr-2 h-[15px]"/>Add Doctor
                </button>
              </div>
              <div className="mt-3">
                {!loading && (
                  <div className="bg-white flex flex-wrap">
                    {doctors.map((doctor) => (
                      <DoctorCard
                        doctor={doctor}
                        deleteDoctor={deleteDoctor}
                        key={doctor.id}
                      ></DoctorCard>
                    ))}
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default DoctorsList;
