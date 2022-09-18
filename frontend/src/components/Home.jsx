import React from 'react'

import { FaAddressBook, FaCalendarAlt, FaMoneyBillAlt} from "react-icons/fa";

import bgImg from '../assets/time-management.png'

const Home = () => {
  return (
    <div name='home' className='w-full h-screen bg-zinc-200 flex flex-col justify-between'>
        <div className='grid md:grid-cols-2 max-w-[1240px] m-auto'>
            <div className='flex flex-col justify-center md:items-start w-full px-2 py-8'>
                <p className='text-2xl font-thin'>Unique Client & Time Management</p>
                <h1 className='py-3 text-5xl md:text-7xl font-thin'>Your personal assistant.</h1>
                <p className='text-2xl font-thin'>Because we value your time.</p>
                <button className='py-3 px-6 sm:w-[60%] my-4 hover:scale-105 duration-300'>Get Started</button>
            </div>
            <div>
                <img className='hidden md:flex' src={bgImg} alt="/" />
            </div>
            <div className='absolute flex flex-col py-8 md:min-w-[560px] bottom-[5%]
            mx-1 md:left-1/2 transform md:-translate-x-1/2 bg-zinc-200
            border border-slate-300 rounded-xl text-center shadow-xl'>
                <div className='flex justify-between flex-wrap px-4'>
                    <p className='flex px-4 py-2 text-black'><FaAddressBook className='h-6 mr-2 text-blue-700' /> Patients Management & History</p>
                    <p className='flex px-4 py-2 text-black'><FaMoneyBillAlt className='h-6 mr-2 text-blue-700' /> Budgeting</p>
                    <p className='flex px-4 py-2 text-black'><FaCalendarAlt className='h-6 mr-2 text-blue-700' /> Appointments Callendar</p>
                </div>
            </div>
        </div>
    </div>
  )
}

export default Home