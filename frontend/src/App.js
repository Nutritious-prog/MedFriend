import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Dashboard from './pages/Dashboard';
import Login from './components/logins/Login';
import MainPage from './pages/MainPage';
import SignUp from './components/logins/SignUp';
import Patients from './components/dashboard/Patients';
import AddPatient from './components/dashboard/AddPatient';
import UpdatePatient from './components/dashboard/UpdatePatient';


function App() {
  return (
    <>
    <BrowserRouter>
      <Routes>
        <Route index element={<MainPage/>}></Route>
        <Route path='/' element={<MainPage/>}></Route>
        <Route path='/login' element={<Login/>}></Route>
        <Route path='/signUp' element={<SignUp/>}></Route>
        <Route path='/dashboard/home' element={<Dashboard/>}></Route>
        <Route path='/dashboard/schedule' element={<Dashboard/>}></Route>
        <Route path='/dashboard/staff' element={<Dashboard/>}></Route>
        <Route path='/dashboard/patients' element={<Patients/>}></Route>
        <Route path='/dashboard/patients/add' element={<AddPatient/>}></Route>
        <Route path='/dashboard/patients/update/:id' element={<UpdatePatient/>}></Route>
        <Route path='/dashboard/options' element={<Dashboard/>}></Route>
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
