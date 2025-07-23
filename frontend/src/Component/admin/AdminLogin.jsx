import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import './AdminLogin.css';

const AdminLogin = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8150/admin/authenticate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
      });

      const data = await response.json();

      if (response.ok && data.success === "true") {
        localStorage.setItem('token', data.token);
        localStorage.setItem('adminId', data.adminId);
        localStorage.setItem('adminUsername', data.username);

        navigate('/admin-dashboard');
      } else {
        alert(`Login failed: ${data.token || 'Invalid credentials'}`);
      }
    } catch (error) {
      console.error('Error during admin login:', error);
      alert('An error occurred. Please try again.');
    }
  };

  return (
    <div className="admin-login-container">
      <motion.div
        className="admin-login-box"
        initial={{ opacity: 0, y: -50 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6 }}
      >
        <h2>Admin Login</h2>
        <form onSubmit={handleLogin}>
          <input
            type="email"
            placeholder="Admin Email Address"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <motion.button
            type="submit"
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
          >
            Login
          </motion.button>
        </form>

        <div className="login-extra-links">
          <p>
            New admin? <Link to="/admin-register">Register here</Link>
          </p>
          <p>
  <Link to="/">← Back to Home</Link>
</p>

        </div>
      </motion.div>
    </div>
  );
};

export default AdminLogin;
