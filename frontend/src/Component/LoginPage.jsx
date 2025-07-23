import React from 'react';
import './LoginPage.css';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
  const navigate = useNavigate();

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Welcome to Railway Ticketing System</h2>
        <p>Please choose your login type:</p>
        <div className="button-group">
          <button onClick={() => navigate('/user-login')} className="login-btn user-btn">
            Login as User
          </button>
          <button onClick={() => navigate('/admin-login')} className="login-btn admin-btn">
            Login as Admin
          </button>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
