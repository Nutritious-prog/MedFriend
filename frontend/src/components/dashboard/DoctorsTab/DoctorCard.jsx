import React from "react";
import { useNavigate } from "react-router-dom";

import { FaPen, FaMinus } from "react-icons/fa";

function DoctorCard({ doctor, deleteDoctor }) {
  const navigate = useNavigate();
  const editDoctor = (e, id) => {
    e.preventDefault();
    navigate(`/dashboard/doctors/update/${id}`);
  };

  const ColorList = ["#C63ABD", "#C63ABD", "#FF8165", "#FFBF52", "#00AE7E"];

  return (
    <div className="md:w-[45%] w-[90%] flex ml-3 border justify-between mt-2">
      <div className="flex">
        <div
          className="w-[60px] h-[60px] text-white bg-blue-700 text-center rounded-full p-3 mt-auto mb-auto text-2xl ml-2"
          style={{
            backgroundColor: ColorList[parseInt(doctor.id) % ColorList.length],
          }}
        >
          {doctor.name
            .split(/\s/)
            .reduce((response, word) => (response += word.slice(0, 1)), "")}
        </div>
        <div className="p-2 ml-3">
          <p className="text-left font-bold">Dr. {doctor.name}</p>
          <p className="text-left font-thin">Role: {doctor.role}</p>
          <p className="text-left font-thin">
            Specialization: {doctor.specialization}
          </p>
          <p className="text-left">Tel. {doctor.phoneNumber}</p>
        </div>
      </div>
      <div className="text-right px-6 py-4 whitespace-nowrap font-medium text-sm">
        <a
          onClick={(e, id) => editDoctor(e, doctor.id)}
          className="text-white bg-blue-700 border rounded-md mr-2 mb-4 border-blue-800 hover:text-blue-800 px-4 py-1 hover:cursor-pointer hover:bg-white flex"
        >
          <FaPen className="mt-auto mb-auto mr-2 h-[12px]" />
          Edit
        </a>
        <a
          onClick={(e, id) => deleteDoctor(e, doctor.id)}
          className="text-white bg-red-600 border rounded-md mr-2 mb-4 border-red-600 hover:text-red-600 px-4 py-1 hover:cursor-pointer hover:bg-white flex"
        >
          <FaMinus className="mt-auto mb-auto mr-2 h-[12px]" />
          Delete
        </a>
      </div>
    </div>
  );
}

export default DoctorCard;
