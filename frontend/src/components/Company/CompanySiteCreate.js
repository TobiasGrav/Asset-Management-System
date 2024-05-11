import React, { useState, useRef, useEffect } from 'react'

import './Company.css'
import { useParams } from 'react-router'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import { useNavigate } from 'react-router-dom'
import HTTPRequest from '../../tools/HTTPRequest'
import URL from '../../tools/URL'

const Main = (props) => {

  // Cookie initializer for react
  const [cookies, setCookie, removeCookie] = useCookies();

  // information variables
  const { companyID } = useParams();
  const [name, setName] = useState();
  const [image, setImage] = useState();

  const nameReference = useRef(null);
  const imageInput = useRef(null);

  // back button functionality, goes back to the last page /asset.
  const navigate = useNavigate();

  const cancel = () => {
    navigate(-1);
  }

    // If user doesn't have a JWT cookie it will redirect them to the login page.
    useEffect(() => {
        if(cookies.JWT == null) {
            navigate('/login');
        }
    }, []);

  const imageSelect = () => {
    setImage(imageInput.current.value);
    console.log(imageInput.current.value);
  }

  const handleSubmit = () => {
      const data = {
          name: name,
          company: {
              id: companyID
          }};
    HTTPRequest.post(`${URL.BACKEND}/api/admin/sites`, data, cookies.JWT)
    .then(response => {
        console.log(response);
    })
    .catch(error => {alert('Something went wrong!\nSite not created')});
  };

  // lets the user input values
  const handleNameInput = (event) => {
    setName(event.target.value);
    console.log(name);
  };

  return (
    <div>
        <form onSubmit={handleSubmit}>
            <div style={{textAlign: 'center', width: '100%', marginBottom: '32px', fontSize:32}}><b>Create new Site</b></div>
            <div className='center'>
                <div className='infoContainer'>
                    <b>Site name</b>
                    <input className='inputField' placeholder='Input name' onChange={handleNameInput}></input>
                </div>
                <div className='imageContainer'>
                    <img src={image} style={{width: 360, height: 360}}></img>
                    <input ref={imageInput} onChange={imageSelect} type='file' accept='.png'></input>
                </div>
            </div>
            <div className='buttonContainer1'>
                <button className='button' type='button' onClick={cancel} >Cancel</button>
                <button className='button' type="button" onClick={handleSubmit}>Create</button>
            </div>
        </form>
    </div>
  )
}

export default Main;