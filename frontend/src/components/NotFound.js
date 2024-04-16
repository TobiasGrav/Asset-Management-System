import React, { useEffect } from "react";
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom'

const Welcome = (props) => {
  
    return (
        <div style={{textAlign:'center'}}>
            <h1>404 Not found</h1>
            <p>The address you are trying to access does not exist.</p>
        </div>
    )
  }
  
  export default Welcome;