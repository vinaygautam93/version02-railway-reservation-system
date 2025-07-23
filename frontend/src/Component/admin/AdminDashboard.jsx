import React from 'react';
import './AdminDashboard.css';
import { motion } from 'framer-motion';

const AdminDashboard = () => {
  return (
    <div className="admin-dashboard">
      <aside className="sidebar">
        <h2 className="logo">🚆 Admin Panel</h2>
        <nav>
          <ul>
            <li>Dashboard</li>
            <li>Users</li>
            <li>Trains</li>
            <li>Settings</li>
          </ul>
        </nav>
      </aside>

      <main className="main-content">
        <motion.header
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5 }}
          className="header"
        >
          <h1>Welcome Admin</h1>
        </motion.header>

        <section className="summary-cards">
          <motion.div className="card" whileHover={{ scale: 1.05 }}>
            <h3>Users</h3>
            <p>120</p>
          </motion.div>
          <motion.div className="card" whileHover={{ scale: 1.05 }}>
            <h3>Trains</h3>
            <p>45</p>
          </motion.div>
          <motion.div className="card" whileHover={{ scale: 1.05 }}>
            <h3>Bookings</h3>
            <p>300</p>
          </motion.div>
        </section>

        <section className="tables">
          <div className="table-container">
            <h2>User List</h2>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Email</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>U001</td>
                  <td>John Doe</td>
                  <td>john@example.com</td>
                </tr>
                <tr>
                  <td>U002</td>
                  <td>Jane Smith</td>
                  <td>jane@example.com</td>
                </tr>
              </tbody>
            </table>
          </div>

          <div className="table-container">
            <h2>Train List</h2>
            <table>
              <thead>
                <tr>
                  <th>Train No</th>
                  <th>Name</th>
                  <th>Route</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>101</td>
                  <td>Express A</td>
                  <td>City X - City Y</td>
                </tr>
                <tr>
                  <td>102</td>
                  <td>Express B</td>
                  <td>City Y - City Z</td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>
      </main>
    </div>
  );
};

export default AdminDashboard;
