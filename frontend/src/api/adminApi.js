
//const jwtToken = localStorage.getItem('token');

export const cancelTicket = async (pnr, token) => {
      console.log('Cancelling ticket for PNR:', pnr);
      console.log('Using token:', token);
  try {
    const response = await fetch(`http://localhost:8150/user/cancelticket/${pnr}`, {        
      
      method: 'DELETE', 
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    }); 

    if (!response.ok) {
      throw new Error('Failed to cancel ticket');
    }
    const result = await response.text(); 
    
    return result;
  } catch (error) {
    console.error('Cancel Ticket Error:', error);
    throw error;
  }
};
