import React from 'react'
import { useNavigate } from "react-router-dom";

function Treatment({ treatment, deleteTreatment }) {
  const navigate = useNavigate();
  const editTreatment = (e, id) => {
    e.preventDefault();
    navigate(`/dashboard/treatments/update/${id}`);
  };

  return (
    <div className='md:w-[65%] w-[90%] flex ml-3'>
    <div className='py-1 w-[45%] border-b border-blue-700 flex text-2xl justify-between mb-4 p-2 bg-white mt-3'>
      <div>
        <p className='text-left'>{treatment.name}</p>
      </div>
      <div>
        <p className='text-left'>{treatment.price} $</p>
      </div>
    </div>
    <div className="text-right px-6 py-4 whitespace-nowrap font-medium text-sm">
    <a
      onClick={(e, id) => editTreatment(e, treatment.id)}
      className="text-white bg-blue-700 border rounded-md mr-2 border-blue-800 hover:text-black px-4 py-1 hover:cursor-pointer hover:bg-white"
    >
      Edit
    </a>
    <a
      onClick={(e, id) => deleteTreatment(e, treatment.id)}
      className="text-white bg-red-600 border rounded-md border-red-600 hover:text-black px-3 py-1 hover:cursor-pointer hover:bg-white"
    >
      Delete
    </a>
  </div>
  </div>
  )
}

export default Treatment