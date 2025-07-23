import React, { useEffect, useState } from 'react';
import { cancelTicket } from '../../api/adminApi';
import { useNavigate } from 'react-router-dom';
import './MyBookings.css';

const MyBookings = () => {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);

  const userId = localStorage.getItem('userId');
  const token = localStorage.getItem('token');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const response = await fetch('http://localhost:8150/booking/getallorders');
        const data = await response.json();
        const userBookings = data.filter(booking => booking.userId === userId);
        setBookings(userBookings);
      } catch (error) {
        console.error('Error fetching bookings:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchBookings();
  }, [userId]);

  const handleCancel = async (pnrId) => {
    try {
      await cancelTicket(pnrId, token);
      setBookings(prev => prev.filter(b => b.pnrId !== pnrId));
      alert('Booking cancelled successfully!');
    } catch (error) {
      console.error('Cancel error:', error);
      alert('Failed to cancel booking.');
    }
  };

  const handleConfirm = (booking) => {
    navigate('/booking-confirmation', { state: booking });
  };

  const confirmedBookings = bookings.filter(b => b.status === 'CONFIRMED');
  const pendingBookings = bookings.filter(b => b.status === 'PENDING');
  const cancelledBookings = bookings.filter(b => b.status === 'CANCELLED');

  if (loading) {
    return <div className="loader">Loading your bookings...</div>;
  }

  const renderBookingCard = (booking, actionType) => (
    <div className="booking-card" key={booking.id}>
      <div className="booking-header">
        <h2>{booking.trainName}</h2>
        <span className={`status-badge ${booking.status.toLowerCase()}`}>
          {booking.status}
        </span>
      </div>

      <div className="booking-details">
        <p><strong>PNR:</strong> {booking.pnrId}</p>
        <p><strong>From:</strong> {booking.trainFrom} → <strong>To:</strong> {booking.trainTo}</p>
        <p><strong>Date:</strong> {booking.date} at {booking.time}</p>
        <p><strong>Seats:</strong> {booking.totalseats}</p>
        <p><strong>Fare:</strong> ₹{booking.fare}</p>
      </div>

      {actionType === 'cancel' && (
        <button className="cancel-button" onClick={() => handleCancel(booking.pnrId)}>
          ❌ Cancel Booking
        </button>
      )}

      {actionType === 'confirm' && (
        <button className="confirm-button" onClick={() => handleConfirm(booking)}>
          ✅ Confirm Booking
        </button>
      )}
    </div>
  );

  return (
    <div className="my-bookings-container">
      <h1 className="page-title">🎟️ My Train Bookings</h1>

      {bookings.length === 0 ? (
        <p className="no-bookings">You have no bookings yet.</p>
      ) : (
        <>
          {confirmedBookings.length > 0 && (
            <>
              <h2 className="section-title">✅ Confirmed Bookings</h2>
              <div className="bookings-grid">
                {confirmedBookings.map(b => renderBookingCard(b, 'cancel'))}
              </div>
            </>
          )}

          {pendingBookings.length > 0 && (
            <>
              <h2 className="section-title">⏳ Pending Bookings</h2>
              <div className="bookings-grid">
                {pendingBookings.map(b => renderBookingCard(b, 'confirm'))}
              </div>
            </>
          )}

          {cancelledBookings.length > 0 && (
            <>
              <h2 className="section-title">❌ Cancelled Bookings</h2>
              <div className="bookings-grid">
                {cancelledBookings.map(b => renderBookingCard(b, 'none'))}
              </div>
            </>
          )}
        </>
      )}
    </div>
  );
};

export default MyBookings;
