import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './Component/Home';
import LoginPage from './Component/LoginPage';
import AdminLogin from './Component/admin/AdminLogin';
import UserLogin from './Component/user/UserLogin';
import UserRegister from './Component/user/UserRegister';
import RegisterPage from './Component/RegisterPage';
import AdminRegister from './Component/admin/AdminRegister';
import ContactPage from './Component/Contact/ContactPage';
import AboutPage from './Component/About/AboutPage';
import TrainList from './Component/user/TrainListFind';
import UserDashboard from './Component/user/UserDashboard';
import TrainSearch from './Component/user/TrainSearch';
import BookingConfirmation from './Component/user/BookingConfirmation';
import Success from './Component/user/Success';
import MyBookings from './Component/user/MyBookings';
// import AdminDashboard from './Component/admin/AdminDashboard'; 
import AdminDashboard from './Component/admin/AdminDashboard';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/user-login" element={<UserLogin />} />
        <Route path="/admin-login" element={<AdminLogin />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/user-register" element={<UserRegister />} />
        <Route path="/admin-register" element={<AdminRegister />} />
        <Route path="/contact" element={<ContactPage />} />
        <Route path="/about" element={<AboutPage />} />
        <Route path="/train-list" element={<TrainList />} />
        <Route path="/dashboard" element={<UserDashboard />} />
        <Route path="/booking-confirmation" element={<BookingConfirmation />} />
        <Route path="/search-trains" element={<TrainSearch />} />
        <Route path="/success" element={<Success />} />
        <Route path="/my-bookings" element={<MyBookings />} />
        {/* <Route path="/admin-dashboard" element={<AdminDashboard />} /> */}
        <Route path="/admin-dashboard" element={<AdminDashboard />} />

      </Routes>
    </Router>
  );
}

export default App;
