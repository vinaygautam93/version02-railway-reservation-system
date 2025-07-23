import React, { useState } from 'react';
import './AdminRegister.css';

const AdminRegister = () => {
  const [adminId, setAdminId] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleRegister = (e) => {
    e.preventDefault();
    // Add your registration logic here
    console.log('Admin ID:', adminId);
    console.log('Email:', email);
    console.log('Password:', password);
  };

  return (
    <div className="admin-register-container">
      <div className="admin-register-box">
        <h2>Admin Registration</h2>
        <form onSubmit={handleRegister}>
          <input
            type="text"
            placeholder="Admin ID"
            value={adminId}
            onChange={(e) => setAdminId(e.target.value)}
            required
          />
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
      </div>
    </div>
  );
};

export default AdminRegister;
