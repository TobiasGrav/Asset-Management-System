import React, { useEffect } from "react";
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom'

const Welcome = (props) => {
  
    return (
        <div style={{textAlign:'center'}}>
            <h1>403 Access Denied</h1>
            <p>Contact support or your company moderator for access.</p>
        </div>
    )
  }
  
  export default Welcome;