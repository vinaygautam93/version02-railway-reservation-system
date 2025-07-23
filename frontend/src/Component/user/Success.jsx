import React, { useState, useEffect } from 'react';
import './Success.css';
import { cancelTicket } from '../../api/adminApi';

const Success = () => {
  const [message, setMessage] = useState('');
  const [bookingId, setBookingId] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [showCancelModal, setShowCancelModal] = useState(false);
  const [loading, setLoading] = useState(false);
  const [cancelLoading, setCancelLoading] = useState(false);
  const userEmail = localStorage.getItem('username');
  const token = localStorage.getItem('token');

  useEffect(() => {
    const fetchLatestPnr = async () => {
      try {
        const res = await fetch("http://localhost:8150/booking/get-latest-pnr");
        const data = await res.text();
        setBookingId(data);
      } catch (err) {
        console.error("Failed to fetch PNR:", err);
      }
    };

    fetchLatestPnr();
  }, []);

  const handleConfirmBooking = async () => {
    setLoading(true);
    try {
      const response = await fetch('http://localhost:8082/payment/confirm-booking', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ bookingId, userEmail })
      });

      if (response.ok) {
        setMessage('✅ Booking confirmed successfully!');
        setShowModal(true);
      } else {
        setMessage('❌ Failed to confirm booking.');
      }
    } catch (error) {
      console.error('Error:', error);
      setMessage('❌ Something went wrong while confirming booking.');
    } finally {
      setLoading(false);
    }
  };

  const handleCancelBooking = async () => {
    setCancelLoading(true);
    try {
      const result = await cancelTicket(bookingId, token);
      setMessage('🚫 Ticket cancelled successfully!');
      setShowCancelModal(true);
    } catch (error) {
      setMessage('❌ Failed to cancel ticket.');
    } finally {
      setCancelLoading(false);
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setShowCancelModal(false);
  };

  return (
    <div className="success-container">
      <div className="success-card">
        <h2>🎉 Payment Successful!</h2>
        <p>Your payment has been processed. Please confirm your booking below.</p>

        <div className="button-group">
          <button className="confirm-btn" onClick={handleConfirmBooking} disabled={!bookingId || loading}>
            {loading ? <span className="spinner"></span> : 'Confirm Booking'}
          </button>
          <button className="cancel-btn" onClick={handleCancelBooking} disabled={!bookingId || cancelLoading}>
            {cancelLoading ? <span className="spinner red"></span> : 'Cancel Booking'}
          </button>
        </div>

        {message && <p className="status-message">{message}</p>}

        <div className="terms">
          <h3>Terms and Conditions</h3>
          <ul>
            <li>Tickets once confirmed cannot be transferred.</li>
            <li>Cancellation charges may apply as per IRCTC rules.</li>
            <li>Refund of 50% will be processed if cancelled within 24 hours.</li>
            <li>No refund will be issued after train departure.</li>
          </ul>
        </div>
      </div>

      {showModal && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h2>🎫 Booking Confirmed!</h2>
            <p><strong>PNR ID:</strong> {bookingId}</p>
            <p>📧 A confirmation email has been sent to <strong>{userEmail}</strong></p>

            <div className="modal-buttons">
              <button onClick={() => window.location.href = '/home'}>🏠 Home</button>
              <button onClick={() => window.location.href = '/dashboard'}>📊 Dashboard</button>
              <button onClick={() => {
                localStorage.clear();
                window.location.href = '/login';
              }}>🚪 Logout</button>
              <button className="close-btn" onClick={handleCloseModal}>Close</button>
            </div>
          </div>
        </div>
      )}

      {showCancelModal && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h2>🚫 Booking Cancelled</h2>
            <p><strong>PNR ID:</strong> {bookingId}</p>
            <p>Your ticket has been successfully cancelled.</p>

            <div className="modal-buttons">
              <button onClick={() => window.location.href = '/home'}>🏠 Home</button>
              <button onClick={() => window.location.href = '/dashboard'}>📊 Dashboard</button>
              <button onClick={() => {
                localStorage.clear();
                window.location.href = '/login';
              }}>🚪 Logout</button>
              <button className="close-btn" onClick={handleCloseModal}>Close</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Success;
