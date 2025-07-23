import React, { useState } from 'react';
import './RegisterPage.css';
import { useNavigate, Link } from 'react-router-dom';

const RegisterPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8150/user/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: email, password }),
      });

      const data = await response.json();
      console.log('Register API response:', data);

      if (response.ok && data.success === '1') {
        alert(data.token); // Show success message
        setEmail('');
        setPassword('');
        navigate('/login');
      } else {
        alert(data.token || 'Registration failed');
      }
    } catch (error) {
      console.error('Registration error:', error);
      alert('Something went wrong. Please try again.');
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
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

        <div className="register-extra-links">
          <p>Already have an account? <Link to="/login">Login</Link></p>
          <p><Link to="/">← Back to Home</Link></p>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;
