import React from "react";
import { useNavigate } from "react-router-dom";
import { Avatar } from "antd";

import { FaPen, FaMinus } from "react-icons/fa";

const Patient = ({ patient, deletePatient }) => {
  const navigate = useNavigate();
  const editPatient = (e, id) => {
    e.preventDefault();
    navigate(`/dashboard/patients/update/${id}`);
  };

  const ColorList = ['#C63ABD', '#C63ABD', '#FF8165', '#FFBF52', '#00AE7E'];

  return (
    <tr key={patient.id} className='border-b-2 border-gray-200'>
      <td className="text-left px-3 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500">
          <div className="w-[35px] text-white bg-blue-700 text-center rounded-full p-2" style={{ backgroundColor: ColorList[parseInt(patient.id) % ColorList.length]}}>
            {patient.name
              .split(/\s/)
              .reduce((response, word) => (response += word.slice(0, 1)), "")}
          </div>
        </div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500">{patient.name}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500">{patient.phoneNumber}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500">{patient.address.street}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500">{patient.address.city}</div>
      </td>
      <td className="text-left px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500">
          {patient.address.postalCode}{" "}
        </div>
      </td>
      <td className="text-right px-6 py-4 whitespace-nowrap font-medium text-sm">
        <a
          onClick={(e, id) => editPatient(e, patient.id)}
          className="text-white bg-blue-700 border rounded-md mr-2 border-blue-800 hover:text-black px-4 py-1 hover:cursor-pointer hover:bg-white"
        >
          Edit
        </a>
        <a
          onClick={(e, id) => deletePatient(e, patient.id)}
          className="text-white bg-red-600 border rounded-md border-red-600 hover:text-black px-3 py-1 hover:cursor-pointer hover:bg-white"
        >
          Delete
        </a>
      </td>
    </tr>
  );
};

export default Patient;
