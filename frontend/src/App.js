import './App.css';
import About from './components/About';
import AllInOne from './components/AllInOne';
import Footer from './components/Footer';
import Home from './components/Home';
import NavBar from './components/NavBar';
import Pricing from './components/Pricing';
import Support from './components/Support';

function App() {
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
  );
}

export default App;
