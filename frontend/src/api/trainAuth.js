const jwtToken = localStorage.getItem('token'); // Replace with actual token




export const fetchAllTrains = async () => {
  try {
    const response = await fetch('http://localhost:8150/user/viewalltrains', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${jwtToken}`,
        'Content-Type': 'application/json'
      }
    });

    if (!response.ok) {
      throw new Error('Failed to fetch trains');
    }

    return await response.json();
  } catch (error) {
    console.error('Error fetching trains:', error);
    return [];
  }
};


export const searchTrainsByLocation = async (from, to) => {
  const token = localStorage.getItem('token');
  try {
    const response = await fetch(`http://localhost:8150/user/findbw/${from}/${to}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error('Failed to fetch trains');
    }

    return await response.json();
  } catch (error) {
    console.error('Error searching trains:', error);
    return [];
  }
};


export const searchTrainsByLocationAndDate = async (from, to, travelDate) => {
  const allTrains = await searchTrainsByLocation(from, to);

  // Convert travelDate (yyyy-mm-dd) to dd-mm-yyyy
  const [year, month, day] = travelDate.split('-');
  const formattedDate = `${day}-${month}-${year}`;

  // Filter trains by date match
  const filteredTrains = allTrains.filter(train => train.time === formattedDate);

  return filteredTrains;
};


export const bookTicket = async (trainNo, userId) => {
  const token = localStorage.getItem('token');
  try {
    const response = await fetch(`http://localhost:8150/user/bookticket/${trainNo}/${userId}`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error('Booking failed');
    }

    return await response.json();
  } catch (error) {
    console.error('Error booking ticket:', error);
    return null;
  }
};


export const getUserBookings = async (userId) => {
  const token = localStorage.getItem('token');
  try {
    const response = await fetch(`http://localhost:8150/user/getorder/${userId}`, {
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    });
    return response.ok ? await response.json() : [];
  } catch (error) {
    console.error('Error fetching bookings:', error);
    return [];
  }
};

export const cancelTicket = async (pnrId) => {
  const token = localStorage.getItem('token');
  try {
    const response = await fetch(`http://localhost:8150/user/cancelticket/${pnrId}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    });
    return response.ok;
  } catch (error) {
    console.error('Error cancelling ticket:', error);
    return false;
  }
};




// export const bookTicket = async (trainNo, userId) => {
//   try {
//     const response = await fetch(`http://localhost:8150/user/bookticket/${trainNo}/${userId}`, {
//       method: 'POST',
//       headers: {
//         'Authorization': `Bearer ${jwtToken}`,
//         'Content-Type': 'application/json'
//       }
//     });

//     if (!response.ok) {
//       throw new Error('Failed to book ticket');
//     }

//     return await response.json();
//   } catch (error) {
//     console.error('Error booking ticket:', error);
//     return null;
//   }
// };


