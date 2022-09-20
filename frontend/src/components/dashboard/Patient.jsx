import React from "react";
import { useNavigate } from "react-router-dom";

const Patient = ({ patient, deletePatient }) => {
  const navigate = useNavigate();
  const editPatient = (e, id) => {
    e.preventDefault();
    navigate(`/editPatient/${id}`);
  };

  return (
    <tr key={patient.id}>
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
        <div className="text-sm text-gray-500">{patient.address.postalCode} </div>
      </td>
      <td className="text-right px-6 py-4 whitespace-nowrap font-medium text-sm">
        <a
          onClick={(e, id) => editPatient(e, patient.id)}
          className="text-blue-700 hover:text-blue-800 px-4 hover:cursor-pointer">
          Edit
        </a>
        <a
          onClick={(e, id) => deletePatient(e, patient.id)}
          className="text-blue-700 hover:text-blue-800 hover:cursor-pointer">
          Delete
        </a>
      </td>
    </tr>
  );
};

export default Patient;