import React, { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import Sidebar from "../Sidebar";
import { useStateContext } from "../../../contexts/ContextProvider";
import Navbar from "../Navbar";

import { FiSettings } from "react-icons/fi";
import { TooltipComponent } from "@syncfusion/ej2-react-popups";

import Modal from "react-modal";

import FullCalendar from "@fullcalendar/react"; // must go before plugins
import dayGridPlugin from "@fullcalendar/daygrid"; // a plugin!

import "react-datetime/css/react-datetime.css";
import AddAppointmentModal from "./AddAppointmentModal";

Modal.setAppElement("#root");

function CalendarTemp() {
    const navigate = useNavigate();
  const { activeMenu } = useStateContext();

  const [modalOpen, setModalOpen] = useState(false);

  const calendarRef = useRef(null);

  const onEventAdded = (event) => {
    let calendarApi = calendarRef.current.getApi();
    calendarApi.addEvent(event);  
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

            <button
              onClick={() => setModalOpen(true)}
              className="rounded ml-[40px] bg-blue-700 text-white px-6 py-2 font-semibold"
            >
              Add Appointment
            </button>

            <div className="relative z-0 w-[95%] m-auto">
            <FullCalendar
              ref={calendarRef}
              plugins={[dayGridPlugin]}
              initialView="dayGridMonth"
            />
            </div>

            {modalOpen
            ?<AddAppointmentModal
            isOpen={modalOpen}
            onClose={() => setModalOpen(false)}
            onEventAdded={(event) => onEventAdded(event)}
          />: null}
          </div>
        </div>
      </div>
    </div>
  );
}

export default CalendarTemp;
