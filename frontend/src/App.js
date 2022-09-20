import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Dashboard from './pages/Dashboard';
import Login from './components/logins/Login';
import MainPage from './pages/MainPage';
import SignUp from './components/logins/SignUp';


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
        <Route path='/dashboard/patients' element={<Dashboard/>}></Route>
        <Route path='/dashboard/options' element={<Dashboard/>}></Route>
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
