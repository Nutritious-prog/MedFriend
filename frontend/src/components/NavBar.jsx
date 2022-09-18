import React, {useState} from 'react';
import { useNavigate } from "react-router-dom";
import { Link, animateScroll as scroll, } from 'react-scroll'

const NavBar = () => {
    const [nav, setNav] = useState(false)
    const navigate = useNavigate();

    const handleClick = () => setNav(!nav)

    const handleClose =()=> setNav(!nav)
    const goToSignIn = () => navigate("/login");
    const goToSignUp = () => navigate("/signUp");

  return (
    <div className='w-screen h-[80px] z-10 bg-zinc-200 fixed drop-shadow-lg'>
        <div className='px-2 flex justify-between items-center w-full h-full'>
            <div className='flex items-center'>
                <h1 className='text-3xl font-thin mr-4 sm:text-4xl p-4'>MEDFRIEND.</h1>
                <ul className='hidden md:flex pl-[50px]'>
                    <li className='hover:cursor-pointer'><Link to="home" smooth={true} duration={500}>Home</Link></li>
                    <li className='hover:cursor-pointer'><Link to="about" smooth={true} offset={-200} duration={500}>About</Link></li>
                    <li className='hover:cursor-pointer'><Link to="support" smooth={true} offset={-50} duration={500}>Support</Link></li>
                    <li className='hover:cursor-pointer'><Link to="platforms" smooth={true} offset={-100} duration={500}>Platforms</Link></li>
                    <li className='hover:cursor-pointer'><Link to="pricing" smooth={true} offset={-50} duration={500}>Pricing</Link></li>
                </ul>
            </div>
            <div className='hidden md:flex pr-4'>
                <button className='border-none bg-transparent text-black mr-4' onClick={goToSignIn}>
                    Sign In
                </button>
                <button className='px-8 py-3 hover:scale-105 duration-300' onClick={goToSignUp}>Sign Up</button>
            </div>
            <div className='md:hidden mr-4' onClick={handleClick}>
            {!nav ?
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-8 h-8">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25H12" />
                </svg> :
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-8 h-8">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
                </svg>
            }
            </div>
        </div>

        <ul className={!nav ? 'hidden' : 'absolute bg-zinc-200 w-full px-8'}>
          <li className='border-b-2 border-zinc-300 w-full hover:cursor-pointer'><Link onClick={handleClose} to="home" smooth={true} duration={500}>Home</Link></li>
          <li className='border-b-2 border-zinc-300 w-full hover:cursor-pointer'><Link onClick={handleClose} to="about" smooth={true} offset={-200} duration={500}>About</Link></li>
          <li className='border-b-2 border-zinc-300 w-full hover:cursor-pointer'><Link onClick={handleClose} to="support" smooth={true} offset={-50} duration={500}>Support</Link></li>
          <li className='border-b-2 border-zinc-300 w-full hover:cursor-pointer'><Link onClick={handleClose} to="platforms" smooth={true} offset={-100} duration={500}>Platforms</Link></li>
          <li className='border-b-2 border-zinc-300 w-full hover:cursor-pointer'><Link onClick={handleClose} to="pricing" smooth={true} offset={-50} duration={500}>Pricing</Link></li>

        <div className='flex flex-col my-4'>
            <button className='bg-transparent text-blue-700 px-8 py-3 mb-4 w-[60%] m-auto' onClick={goToSignIn}>Sign In</button>
            <button className='px-8 py-3 w-[60%] m-auto hover:scale-105 duration-300' onClick={goToSignUp}>Sign Up</button>
        </div>
      </ul>
    </div>
  );
};

export default NavBar;