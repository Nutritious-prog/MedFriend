import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Sidebar from "../Sidebar";
import { useStateContext } from "../../../contexts/ContextProvider";
import Treatment from "./Treatment";
import Navbar from "../Navbar";

import { FiSettings } from "react-icons/fi";
import { TooltipComponent } from "@syncfusion/ej2-react-popups";
import TreatmentService from "../../../services/TreatmentService";

function TreatmentsList() {
  const navigate = useNavigate();
  const { activeMenu } = useStateContext();

  const [loading, setLoading] = useState(true);
  const [treatments, setTreatments] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await TreatmentService.getTreatments();
        console.log(response.data);
        setTreatments(response.data);
      } catch (error) {
        console.log(error);
      }
      setLoading(false);
    };
    fetchData();
  }, []);

  const deleteTreatment = (e, id) => {
    e.preventDefault();
    TreatmentService.deleteTreatment(id).then((res) => {
      if (treatments) {
        setTreatments((prevElement) => {
          return prevElement.filter((treatment) => treatment.id !== id);
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
          onClick={() => navigate("/dashboard/treatments/add")}
          className="rounded ml-3 bg-blue-700 text-white px-6 py-2 font-semibold">
          Add Treatment
        </button>
      </div>

      {treatments.map((treatment) => (
                <Treatment
                  treatment={treatment}
                  deleteTreatment={deleteTreatment}
                  key={treatment.id}></Treatment>
              ))}
    </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default TreatmentsList