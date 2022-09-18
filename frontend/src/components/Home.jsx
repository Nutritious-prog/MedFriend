import React from 'react'
import {
    CloudUploadIcon,
    DatabaseIcon,
    PaperAirplaneIcon,
    ServerIcon,
} from '@heroicons/react/solid'

import { BiFolderOpen, BiCalendar, BiMoney} from "react-icons/bi";

import bgImg from '../assets/time-management.png'

const Home = () => {
  return (
    <div name='home' className='w-full h-screen bg-zinc-200 flex flex-col justify-between'>
        <div className='grid md:grid-cols-2 max-w-[1240px] m-auto'>
            <div className='flex flex-col justify-center md:items-start w-full px-2 py-8'>
                <p className='text-2xl'>Unique Client & Time Management</p>
                <h1 className='py-3 text-5xl md:text-7xl font-bold'>Your personal MedFriend</h1>
                <p className='text-2xl'>Because we value your time.</p>
                <button className='py-3 px-6 sm:w-[60%] my-4'>Get Started</button>
            </div>
            <div>
                <img className='hidden md:flex' src={bgImg} alt="/" />
            </div>
            <div className='absolute flex flex-col py-8 md:min-w-[560px] bottom-[5%]
            mx-1 md:left-1/2 transform md:-translate-x-1/2 bg-zinc-200
            border border-slate-300 rounded-xl text-center shadow-xl'>
                <div className='flex justify-between flex-wrap px-4'>
                    <p className='flex px-4 py-2 text-black'><BiFolderOpen className='h-6 text-indigo-600' /> Patients History</p>
                    <p className='flex px-4 py-2 text-black'><BiCalendar className='h-6 text-indigo-600' /> Appointments Callendar</p>
                    <p className='flex px-4 py-2 text-black'><BiMoney className='h-6 text-indigo-600' /> Budgeting</p>
                </div>
            </div>
        </div>
    </div>
  )
}

export default Home