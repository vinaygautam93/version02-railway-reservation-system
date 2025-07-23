import React, { useState } from 'react';
import './TrainSearch.css';
import { searchTrainsByLocation, bookTicket } from '../../api/trainAuth';

const TrainSearch = () => {
  const [trainFrom, setTrainFrom] = useState('');
  const [trainTo, setTrainTo] = useState('');
  const [results, setResults] = useState([]);
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');

  const handleSearch = async (e) => {
    e.preventDefault();

    if (!trainFrom || !trainTo) {
      setError('Both source and destination are required.');
      return;
    }

    if (trainFrom.trim().toLowerCase() === trainTo.trim().toLowerCase()) {
      setError('Source and destination cannot be the same.');
      return;
    }

    setError('');
    setMessage('');
    const data = await searchTrainsByLocation(trainFrom, trainTo);
    setResults(data);
  };

  const handleBookTicket = async (trainNo) => {
    const userId = localStorage.getItem('userId'); // Make sure this is stored at login
    if (!userId) {
      setMessage('User not logged in.');
      return;
    }

    const response = await bookTicket(trainNo, userId);
    if (response) {
      setMessage(`✅ Ticket booked successfully for Train No: ${trainNo}`);
    } else {
      setMessage(`❌ Failed to book ticket for Train No: ${trainNo}`);
    }
  };

  return (
    <div className="search-container">
      <h2>🔍 Search Trains by Route</h2>
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
        <button type="submit">Search</button>
      </form>

      {error && <p className="error-message">{error}</p>}
      {message && <p className="success-message">{message}</p>}

      {results.length > 0 && (
        <div className="results-table">
          <table>
            <thead>
              <tr>
                <th>Train No</th>
                <th>Name</th>
                <th>From</th>
                <th>To</th>
                <th>Fare</th>
                <th>Seats</th>
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
                  <td>
                    <button
                      className="book-btn"
                      onClick={() => handleBookTicket(train.trainNo)}
                    >
                      Book Ticket
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default TrainSearch;
