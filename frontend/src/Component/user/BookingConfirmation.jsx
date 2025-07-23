import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './BookingConfirmation.css'; 
const BookingConfirmation = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const booking = location.state;

  if (!booking) {
    return <p>❌ No booking data found. Please go back and try again.</p>;
  }

  const handleConfirmBooking = async () => {
  const token = localStorage.getItem('token'); // ✅ Get token from localStorage

  try {
    const response = await fetch('http://localhost:8150/user/bookticket', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}` // ✅ Add token here
      },
      body: JSON.stringify(booking),
    });

    if (response.ok) {
      const result = await response.json();
      if (result.checkoutUrl) {
        window.location.href = result.checkoutUrl; // ✅ Redirect to Stripe
      } else {
        alert('✅ Booking done, but no payment URL received.');
      }
    } else {
      alert('❌ Booking failed. Please try again.');
    }
  } catch (error) {
    console.error('Booking error:', error);
    alert('⚠️ Something went wrong.');
  }
};



  return (
    <div className="confirmation-page">
      <h2>🎫 Booking Confirmation</h2>
      <div className="booking-summary">
        <h3>Train Details</h3>
        <p><strong>Train No:</strong> {booking.trainNo}</p>
        <p><strong>Train Name:</strong> {booking.trainName}</p>
        <p><strong>From:</strong> {booking.trainFrom}</p>
        <p><strong>To:</strong> {booking.trainTo}</p>
        <p><strong>Date:</strong> {booking.date}</p>
        <p><strong>Time:</strong> {booking.time}</p>

        <h3>Passenger Info</h3>
        <p><strong>Name:</strong> {booking.name}</p>
        <p><strong>Phone:</strong> {booking.phnnumber}</p>
        
        <p><strong>Email:</strong> {booking.email}</p>
        <p><strong>Seats:</strong> {booking.totalseats}</p>

        <h3>Total Fare</h3>
        <p><strong>₹ {booking.fare}</strong></p>

        <button className="confirm-btn" onClick={handleConfirmBooking}>
          ✅ Confirm Booking
        </button>
      </div>
    </div>
  );
};

export default BookingConfirmation;
