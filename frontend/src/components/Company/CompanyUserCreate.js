import React, { useState, useRef, useEffect } from 'react';

import URL from '../../tools/URL';

import { Helmet } from 'react-helmet';

import Table from '../Asset/AssetTable';

import './Company.css';
import { useParams } from 'react-router';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';
import HTTPRequest from '../../tools/HTTPRequest';
import { jwtDecode } from 'jwt-decode';
import QRCode from 'qrcode.react';
import DataTable from 'react-data-table-component';

const Main = (props) => {

  // Cookie initializer for react
  const [cookies, setCookie, removeCookie] = useCookies();

  // information variables
  const { userID } = useParams();
  const { companyID } = useParams();
  const [role, setRole] = useState(null);
  const [firstName, setFirstName] = useState(null);
  const [lastName, setLastName] = useState(null);
  const [email, setEmail] = useState(null);
  const [phoneNumber, setPhoneNumber] = useState(null);
  const [image, setImage] = useState(require('../../Pages/resources/nerd.png'));

  // Conditional variables
  const [isAdmin, setIsAdmin] = useState(false);

  // Checks if the current user is an admin, and if so isAdmin is true. It decodes the JWT and extracts the roles.
  useEffect(() => {
    if(cookies.JWT != null) {
      jwtDecode(cookies.JWT).roles.forEach(role => {
        if(role.authority == "ADMIN") {
            setIsAdmin(true);
        }
      })
    }
  }, []);

  const navigate = useNavigate();

  const cancel = () => {
    navigate(-1);
  }

  const handleFirstNameChange = (event) => {
    setFirstName(event.target.value);
  }
  const handleLastNameChange = (event) => {
    setLastName(event.target.value);
  }
  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  }
  const handlePhoneNumberChange = (event) => {
    setPhoneNumber(event.target.value);
  }
  const handleRoleChange = (event) => {
    if(event.target.value == 'USER') {
        setImage(require('../../Pages/resources/nerd.png'));
    }
    if(event.target.value == 'MODERATOR') {
        setImage(require('../../Pages/resources/superior.png'));
    }
  }

  const handleSubmit = async (event) => {
    const userData = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: 'test',
        phoneNumber: phoneNumber,
        company: { id: companyID }
    }
    console.log(userData);
    HTTPRequest.post(`${URL.BACKEND}/api/admin/users`, userData, cookies.JWT)
    .then(response => {
        console.log(response);
    })
    .catch(error => (alert('Something went wrong!\nUser not created')))
  };

  return (
    <div className="assetBody">
      <div style={{display:'flex', width:'auto', fontSize:32}}>
        <b>Create new user</b>
      </div>
      <br></br>
      <div className="assetContainer">
        <div className="containerLeft">
          <b>First Name</b>
          <input className='input' placeholder="Enter First Name" value={firstName} onChange={handleFirstNameChange} ></input>
          <b>Last Name</b>
          <input className='input' placeholder="Enter Last Name" value={lastName} onChange={handleLastNameChange} ></input>
          <b>Email:</b>
          <input className='input' placeholder="Enter Email" value={email} onChange={handleEmailChange} ></input>
          <b>Phone number:</b>
          <input className='input' placeholder="Enter Phone number" value={phoneNumber} onChange={handlePhoneNumberChange} ></input>
          
          <b>Pick role</b>

          { isAdmin && 
          <b>Pick role</b>
          &&
          <select onChange={handleRoleChange}>
            <option value={'USER'}>User</option>
            <option value={'MODERATOR'}>Moderator</option>
          </select>
          }

        </div>
        <img alt="image" src={image} className="assetImage"/>
      </div>
      <div className="buttonContainer">
        <div className="leftButtonContainer">

        </div>
        <div className="rightButtonContainer">
            <button type="button" className="button" onClick={cancel} >Cancel</button>
            <button type="button" className="button" onClick={handleSubmit} >Create</button>
        </div>
      </div>
    </div>
  )
}

export default Main