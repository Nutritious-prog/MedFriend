import React from 'react'
import About from '../components/mainPage/About';
import AllInOne from '../components/mainPage/AllInOne';
import Footer from '../components/mainPage/Footer';
import Home from '../components/mainPage/Home';
import NavBar from '../components/mainPage/NavBar';
import Pricing from '../components/mainPage/Pricing';
import Support from '../components/mainPage/Support';

function MainPage() {
  return (
    <>
    <NavBar/>
    <Home />
    <About />
    <Support />
    <AllInOne />
    <Pricing />
    <Footer/>
    </>
  )
}

export default MainPage