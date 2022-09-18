import React from 'react'
import About from './About';
import AllInOne from './AllInOne';
import Footer from './Footer';
import Home from './Home';
import NavBar from './NavBar';
import Pricing from './Pricing';
import Support from './Support';

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