import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import './UserRegister.css';

const UserRegister = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8150/user/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username: email, password }),
      });

      const data = await response.json();

      if (response.ok && data.success === "1") {
        alert(data.message);
        navigate('/login'); // Redirect to login page
      } else {
        alert(`Registration failed: ${data.message || 'Try again later'}`);
      }
    } catch (error) {
      console.error('Error during registration:', error);
      alert('An error occurred. Please try again.');
    }
  };

  return (
    <div className="user-register-container">
      <div className="user-register-box">
        <h2>User Registration</h2>
        <form onSubmit={handleRegister}>
          <input
            type="email"
            placeholder="Email Address"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Create Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <button type="submit">Register</button>
        </form>

        {/* 👇 Extra Links Section */}
        <div className="register-extra-links">
          <p>Already have an account? <Link to="/login">Login</Link></p>
          <p><Link to="/">← Back to Home</Link></p>
        </div>
      </div>
    </div>
  );
};

export default UserRegister;
