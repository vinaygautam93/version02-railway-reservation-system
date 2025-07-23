import React, { useState, useEffect } from 'react';
import './UserDashboard.css';
import {
  searchTrainsByLocationAndDate,
  bookTicket,
  getUserBookings,
  cancelTicket,
} from '../../api/trainAuth';
import { useNavigate } from 'react-router-dom';

const UserDashboard = () => {
  const [trainFrom, setTrainFrom] = useState('');
  const [trainTo, setTrainTo] = useState('');
  const [travelDate, setTravelDate] = useState('');
  const [results, setResults] = useState([]);
  const [bookings, setBookings] = useState([]);
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [selectedTrain, setSelectedTrain] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    phnnumber: '',
    email: '',
    totalseats: 1,
  });

  const navigate = useNavigate();
  const userId = localStorage.getItem('userId');

  useEffect(() => {
    if (userId) {
      loadBookings();
    }
  }, [userId]);

  const loadBookings = async () => {
    const data = await getUserBookings(userId);
    setBookings(data);
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    navigate('/login');
  };

  const handleSearch = async (e) => {
    e.preventDefault();

    if (!trainFrom || !trainTo || !travelDate) {
      setError('Please fill all fields including date.');
      return;
    }

    if (trainFrom.trim().toLowerCase() === trainTo.trim().toLowerCase()) {
      setError('Source and destination cannot be the same.');
      return;
    }

    setError('');
    setMessage('');
    const data = await searchTrainsByLocationAndDate(trainFrom, trainTo, travelDate);
    setResults(data);
  };

  const openBookingModal = (train) => {
    setSelectedTrain(train);
    setShowModal(true);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleBookingSubmit = () => {
    const bookingData = {
      userId,
      ...formData,
      trainNo: selectedTrain.trainNo,
      trainName: selectedTrain.trainName,
      trainFrom: selectedTrain.trainFrom,
      trainTo: selectedTrain.trainTo,
      date: selectedTrain.time,
      time: '10:00 AM',
      totalseats: parseInt(formData.totalseats),
      fare: selectedTrain.fare * parseInt(formData.totalseats),
    };

    setShowModal(false);
    navigate('/booking-confirmation', { state: bookingData });
  };

  return (
    <div className="dashboard-wrapper">
      <nav className="navbar">
        <div className="logo">🚆 Train Portal</div>

        <div className="nav-links">
          <button onClick={() => navigate('/')}>Home</button>
          <button onClick={() => navigate('/train-list')}>View All Trains</button>
          <button onClick={() => navigate('/my-bookings')}>Booked Trains</button>
          <button onClick={() => navigate('/contact')}>Contact Us</button>
          <button onClick={() => navigate('/about')}>About</button>
        </div>

        <div className="profile-menu">
          <div className="profile-icon" onClick={() => setDropdownOpen(!dropdownOpen)}>
            <i className="fas fa-user-circle"></i>
          </div>
          {dropdownOpen && (
            <div className="dropdown">
              <button onClick={() => navigate('/profile')}>View Profile</button>
              <button onClick={() => navigate('/update-profile')}>Update Profile</button>
              <button onClick={() => navigate('/delete-profile')}>Delete Profile</button>
              <button onClick={handleLogout}>Logout</button>
            </div>
          )}
        </div>
      </nav>

      <div className="dashboard-content">
        <h2>🔍 Search Trains</h2>
        <form className="search-form" onSubmit={handleSearch}>
          <input
            type="text"
            placeholder="From (e.g. Mumbai)"
            value={trainFrom}
            onChange={(e) => setTrainFrom(e.target.value)}
          />
          <input
            type="text"
            placeholder="To (e.g. Delhi)"
            value={trainTo}
            onChange={(e) => setTrainTo(e.target.value)}
          />
          <input
            type="date"
            value={travelDate}
            onChange={(e) => setTravelDate(e.target.value)}
          />
          <button type="submit">Search</button>
        </form>

        {error && <p className="error-message">{error}</p>}
        {message && <p className="success-message">{message}</p>}

        {results.length > 0 && (
          <div className="results-table">
            <h3>Available Trains</h3>
            <table>
              <thead>
                <tr>
                  <th>Train No</th>
                  <th>Name</th>
                  <th>From</th>
                  <th>To</th>
                  <th>Fare</th>
                  <th>Seats</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {results.map((train) => (
                  <tr key={train.trainNo}>
                    <td>{train.trainNo}</td>
                    <td>{train.trainName}</td>
                    <td>{train.trainFrom}</td>
                    <td>{train.trainTo}</td>
                    <td>₹{train.fare}</td>
                    <td>{train.seats}</td>
                    <td>{train.time}</td>
                    <td>10:00 AM</td>
                    <td>
                      <button className="book-btn" onClick={() => openBookingModal(train)}>
                        Book
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}

        {bookings.length > 0 && (
          <div className="results-table">
            <h3>My Bookings</h3>
            <table>
              <thead>
                <tr>
                  <th>PNR</th>
                  <th>Train No</th>
                  <th>From</th>
                  <th>To</th>
                  <th>Seats</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {bookings.map((b) => (
                  <tr key={b.pnrId}>
                    <td>{b.pnrId}</td>
                    <td>{b.trainNo}</td>
                    <td>{b.trainFrom}</td>
                    <td>{b.trainTo}</td>
                    <td>{b.totalseats}</td>
                    <td>{b.date}</td>
                    <td>{b.time || '10:00 AM'}</td>
                    <td>
                      <button className="cancel-btn" onClick={() => cancelTicket(b.pnrId)}>
                        Cancel
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {showModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Enter Booking Details</h3>
            <input
              type="text"
              name="name"
              placeholder="Your Name"
              value={formData.name}
              onChange={handleInputChange}
            />
            <input
              type="text"
              name="phnnumber"
              placeholder="Phone Number"
              value={formData.phnnumber}
              onChange={handleInputChange}
            />
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={formData.email}
              onChange={handleInputChange}
            />
            <input
              type="number"
              name="totalseats"
              placeholder="Total Seats"
              min="1"
              value={formData.totalseats}
              onChange={handleInputChange}
            />
            <div className="modal-actions">
              <button onClick={handleBookingSubmit}>Proceed</button>
              <button onClick={() => setShowModal(false)}>Cancel</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default UserDashboard;
