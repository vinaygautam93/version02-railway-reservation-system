import React, { useEffect, useState } from 'react';
import './TrainListFind.css';
import { fetchAllTrains } from '../../api/trainAuth';

function TrainListFind() {
  const [trains, setTrains] = useState([]);

  useEffect(() => {
    fetchAllTrains().then(setTrains);
  }, []);

  return (
    <div className="train-list-container">
      <h2 className="train-heading">🚆 Available Trains</h2>
      {trains.length > 0 ? (
        <div className="train-table-wrapper">
          <table className="train-table">
            <thead>
              <tr>
                <th>Train No</th>
                <th>Name</th>
                <th>From</th>
                <th>To</th>
                <th>Fare</th>
                <th>Seats</th>
                <th>Time</th>
              </tr>
            </thead>
            <tbody>
              {trains.map((train) => (
                <tr key={train.trainNo}>
                  <td>{train.trainNo}</td>
                  <td>{train.trainName}</td>
                  <td>{train.trainFrom}</td>
                  <td>{train.trainTo}</td>
                  <td>₹{train.fare}</td>
                  <td>{train.seats}</td>
                  <td>{train.time}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <p className="loading-text">Loading train data...</p>
      )}
    </div>
  );
}

export default TrainListFind;
