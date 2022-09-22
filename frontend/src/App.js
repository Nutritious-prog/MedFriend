import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Dashboard from './pages/Dashboard';
import Login from './components/logins/Login';
import MainPage from './pages/MainPage';
import SignUp from './components/logins/SignUp';
import Patients from './components/dashboard//PatientsTab/Patients';
import AddPatient from './components/dashboard/PatientsTab/AddPatient';
import UpdatePatient from './components/dashboard/PatientsTab/UpdatePatient';
import TreatmentsList from './components/dashboard/TreatmentsTab/TreatmentsList';
import AddTreatment from './components/dashboard/TreatmentsTab/AddTreatment';
import UpdateTreatment from './components/dashboard/TreatmentsTab/UpdateTreatment';
import Calendar from './components/dashboard/CalendarTab/Calendar';


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
        <Route path='/dashboard/calendar' element={<Calendar/>}></Route>
        <Route path='/dashboard/staff' element={<Dashboard/>}></Route>
        <Route path='/dashboard/patients' element={<Patients/>}></Route>
        <Route path='/dashboard/patients/add' element={<AddPatient/>}></Route>
        <Route path='/dashboard/patients/update/:id' element={<UpdatePatient/>}></Route>
        <Route path='/dashboard/treatments' element={<TreatmentsList/>}></Route>
        <Route path='/dashboard/treatments/add' element={<AddTreatment/>}></Route>
        <Route path='/dashboard/treatments/update/:id' element={<UpdateTreatment/>}></Route>
        <Route path='/dashboard/options' element={<Dashboard/>}></Route>
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
