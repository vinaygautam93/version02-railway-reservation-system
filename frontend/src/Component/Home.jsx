import React from 'react';
import './Home.css';

const HomePage = () => {
  return (
    <div className="homepage">
      <Navbar />
      <MainContent />
      <Footer />
    </div>
  );
};

const Navbar = () => (
  <nav className="navbar">
    <h1>Railway Ticketing System</h1>
    <ul className="nav-links">
      <li><a href="/">Home</a></li>
      <li><a href="/login">Login</a></li>
      <li><a href="/register">Register</a></li>
      <li><a href="/about">About</a></li>
      <li><a href="/contact">Contact</a></li>
    </ul>
  </nav>
);

const MainContent = () => (
  <main className="main-content">
    <h2>Welcome to the Railway Ticketing System</h2>
    <p>
      Our Railway Ticketing System is a modern web application designed to streamline the process of booking train tickets online.
      Users can register, log in, search for trains, check seat availability, and book tickets with ease.
      The system offers real-time updates, secure transactions, and a user-friendly interface.
      Whether you're planning a short commute or a long-distance journey, our platform ensures a smooth and hassle-free experience.
    </p>
  </main>
);

const Footer = () => (
  <footer className="footer">
    <p>© Vinay, 2025. All rights reserved.</p>
    <a href="/policies">Policies</a>
  </footer>
);

export default HomePage;
