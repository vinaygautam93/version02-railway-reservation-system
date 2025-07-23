import React from 'react';
import './AboutPage.css';

const AboutPage = () => {
  return (
    <div className="about-container">
      <div className="about-header">
        <h1>About Our Railway Reservation System</h1>
        <p>Empowering seamless travel with cutting-edge technology</p>
      </div>

      <div className="about-section">
        <h2>🚀 Our Mission</h2>
        <p>
          We aim to revolutionize railway ticket booking with a fast, secure, and user-friendly platform that ensures a smooth travel experience for every passenger.
        </p>
      </div>

      <div className="about-section tech-stack">
        <h2>🛠️ Technologies We Use</h2>
        <ul>
          <li><strong>Java Spring Boot Microservices:</strong> Scalable and modular backend architecture</li>
          <li><strong>React.js:</strong> Fast and responsive frontend for seamless user experience</li>
          <li><strong>Kafka:</strong> Real-time notifications and event-driven communication</li>
          <li><strong>Stripe / Razorpay:</strong> Secure and reliable payment gateway integration</li>
          <li><strong>MySQL / PostgreSQL:</strong> Robust and optimized relational database</li>
        </ul>
      </div>

      <div className="about-section achievements">
        <h2>🏆 Our Achievements</h2>
        <ul>
          <li>Handled 1k+ ticket bookings with 99.99% uptime</li>
          <li>Integrated real-time train  seat availability</li>
          <li>Implemented secure payment flow with fraud detection</li>
          <li>Microservice architecture with load balancing and auto-scaling</li>
          <li>Push notifications and email alerts using Kafka</li>
        </ul>
      </div>

      <div className="about-section">
        <h2>📱 About the App</h2>
        <p>
          Our app is designed for both desktop and mobile users. With a clean UI, real-time updates, and secure login, users can book tickets, view train schedules, and manage their journeys with ease.
        </p>
      </div>
    </div>
  );
};

export default AboutPage;
