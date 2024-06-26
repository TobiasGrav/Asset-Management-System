import React, { useState, useRef, useEffect } from 'react';

import URL from '../../tools/URL';

import { Helmet } from 'react-helmet';

import Table from '../asset/AssetTable';

import './Company.css';
import { useParams } from 'react-router';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';
import HTTPRequest from '../../tools/HTTPRequest';
import { jwtDecode } from 'jwt-decode';
import QRCode from 'qrcode.react';
import DataTable from 'react-data-table-component';
import {getAdminStatus, getManagerStatus} from "../../tools/globals";

const Main = (props) => {

  // Cookie initializer for react
  const [cookies, setCookie, removeCookie] = useCookies();

  // information variables
  const { userID } = useParams();
  const { companyID } = useParams();
  const [role, setRole] = useState('USER');
  const [firstName, setFirstName] = useState(null);
  const [lastName, setLastName] = useState(null);
  const [email, setEmail] = useState(null);
  const [phoneNumber, setPhoneNumber] = useState(null);
  const [image, setImage] = useState(require('../../pages/resources/nerd.png'));

  // Conditional variables
  const [validFirstName, setValidFirstName] = useState();
  const [validLastName, setValidLastName] = useState();
  const [validEmail, setValidEmail] = useState();
  const [emailUsed, setEmailUsed] = useState();
  const [validPhoneNumber, setValidPhoneNumber] = useState();

  const emailInput = useRef();


  const navigate = useNavigate();

  const cancel = () => {
    navigate(-1);
  }

  const handleFirstNameChange = (event) => {
    setFirstName(event.target.value);
    setValidFirstName(isValidName(event.target.value));
  }
  const handleLastNameChange = (event) => {
    setLastName(event.target.value);
    setValidLastName(isValidName(event.target.value));
  }
  const handleEmailChange = (event) => {
    setEmailUsed(false);
    setEmail(event.target.value);
    setValidEmail(isValidEmail(event.target.value));
  }
  const handlePhoneNumberChange = (event) => {
    setPhoneNumber(event.target.value);
    //if(!validPhoneNumber) {
    //    setValidPhoneNumber(isValidPhoneNumber(event.target.value));
    //}
  }
  const handleRoleChange = (event) => {
    if(event.target.value === 'USER') {
        setImage(require('../../pages/resources/nerd.png'));
        setRole("USER");
    }
    if(event.target.value === 'MANAGER') {
        setImage(require('../../pages/resources/superior.png'));
        setRole("MANAGER");
    }
    if (event.target.value === 'TECHNICIAN'){
        setImage(require('../../pages/resources/technician.png'));
        setRole("TECHNICIAN");
    }
  }
  function isValidName(name) {
    // Regular expression for email validation
    const nameRegex = /^(?:[a-zA-ZæØøÅå-]+\s)*[a-zA-ZÆæØøÅå-]+$/;
    return name != null && name.length < 128 && nameRegex.test(name);
  }
  function isValidEmail(email) {
      // Regular expression for email validation
      //const emailRegex = /^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z])?$/;
      const emailRegex = /^(?=.{1,64}@)(?!.*@-.*)[A-Za-z0-9_-]+(\.[A-Za-z0-9_-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*\.[A-Za-z]{2,}$/;

      return emailRegex.test(email);
  }

  //function isValidPhoneNumber(phoneNumber) {
  //    let isValid = false;
  //    if (phoneNumber != null && phoneNumber.length == 8 && /^\d+$/.test(phoneNumber)) {
  //        isValid = true;
  //    }
  //    return isValid;
  //}

  const handleSubmit = (event) => {
    setValidFirstName(isValidName(firstName));
    setValidLastName(isValidName(lastName));
    setValidEmail(isValidEmail(email));
    //setValidPhoneNumber(isValidPhoneNumber(phoneNumber));
    if(validFirstName && validLastName && validEmail) {
        const userData = {
            firstName: firstName,
            lastName: lastName,
            email: email,
            password: 'tester',
            phoneNumber: phoneNumber,
            company: companyID ? { id: companyID } : null,
            role: role
        }
        console.log(userData);
        let endpoint = getAdminStatus() ? `${URL.BACKEND}/api/admin/users` : `${URL.BACKEND}/api/manager/users`;
        HTTPRequest.post(endpoint, userData, cookies.JWT)
        .then(response => {
            if(response == null) {
                //alert('Email already in use!');
                setEmailUsed(true);
            }else if(response.status == 201) {
                navigate(-1);
            }
        })
    }
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
            {validFirstName != null && !validFirstName && <p style={{color:'red', fontSize:10, marginTop:'0px', marginBottom:'0px'}}>Invalid first name</p>}
            <b>Last Name</b>
            <input className='input' placeholder="Enter Last Name" value={lastName} onChange={handleLastNameChange} ></input>
            {validLastName != null && !validLastName && <p style={{color:'red', fontSize:10, marginTop:'0px', marginBottom:'0px'}}>Invalid last name</p>}
            <b>Email:</b>
            <input className='inputField' ref={emailInput} placeholder="Enter Email" value={email} onChange={handleEmailChange} ></input>
            {validEmail != null && !validEmail && <p style={{color:'red', fontSize:10, marginTop:'0px', marginBottom:'0px'}}>Not a valid email format</p>}
            {emailUsed != null && emailUsed && <p style={{color:'red', fontSize:10, marginTop:'0px', marginBottom:'0px'}}>Email already used</p>}
            <b>Phone number:</b>
            <input className='input' placeholder="Enter Phone number" value={phoneNumber} onChange={handlePhoneNumberChange} ></input>
            {/*{validPhoneNumber != null && !validPhoneNumber && <p style={{color:'red', fontSize:10, marginTop:'0px', marginBottom:'0px'}}>Invalid Phone Number</p>}*/}

            {/* Admin and manager options */}
            {(getAdminStatus() || getManagerStatus()) && (
                <>
                    <b>Pick role</b>
                    <select onChange={handleRoleChange}>
                        <option value={'USER'}>User</option>
                        {/* Only admin options */}
                        {getAdminStatus() && (
                            <>
                                <option value={'MANAGER'}>Manager</option>
                                <option value={'TECHNICIAN'}>Technician</option>
                            </>
                        )}
                    </select>
                </>
            )}

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