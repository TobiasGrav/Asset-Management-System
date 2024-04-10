import React, { useState, useRef, useEffect } from 'react'

import { Helmet } from 'react-helmet'

import Table from './AssetTable'

import './Customer.css'
import { useParams } from 'react-router'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import { useNavigate } from 'react-router-dom'
import HTTPRequest from '../tools/HTTPRequest'
import { jwtDecode } from 'jwt-decode'
import QRCode from 'qrcode.react'

const Main = (props) => {

  // Cookie initializer for react
  const [cookies, setCookie, removeCookie] = useCookies();

  // information variables
  const { id } = useParams();
  const [role, setRole] = useState(null);
  const [firstName, setFirstName] = useState(null);
  const [lastName, setLastName] = useState(null);
  const [email, setEmail] = useState(null);
  const [phoneNumber, setPhoneNumber] = useState(null);
  

  // Conditional variables
  const [isEditing, setIsEditing] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);

  const backButtonReference = useRef(null);
  const editButtonReference = useRef(null);
  const deleteButtonReference = useRef(null);
  const cancelButtonReference = useRef(null);

  // Checks if the current user is an admin, and if so isAdmin is true. It decodes the JWT and extracts the roles.
  useEffect(() => {
    jwtDecode(cookies.JWT).roles.forEach(role => {
      if(role.authority == "ADMIN") {
            setIsAdmin(true);
      }
    })
  })


// // Prevents the user from inputing values when not in editing mode.
// useEffect(() => {
//   if(descriptionReference.current != null) {
//     nameReference.current.disabled = isEditing;
//     idReference.current.disabled = isEditing;
//     descriptionReference.current.disabled = isEditing;
//   }}, [isEditing]);
//   
  // back button functionality, goes back to the last page /asset.
  const navigate = useNavigate();
  const back = () => {
    navigate('/customer/');
  }

  const edit = () => {
    setIsEditing(true);
  }

  const cancel = () => {
    setIsEditing(false);

  }

  const confirm = () => {
    console.log("confimed edit");
    setIsEditing(false);
  }

  // Sends a get request to the backend and inputs the values of the asset.
  useEffect(() => {
    HTTPRequest.get(`http://localhost:8080/api/users/${id}`, cookies.JWT)
    .then(response => {
      console.log(response);
      let asset = response.data;
      setFirstName(response.data.firstName);
      setLastName(response.data.lastName);
      setEmail(response.data.email);
      setPhoneNumber(response.data.phoneNumber);
      if(response.data.roles.length > 1) {
        setRole("Admin");
      } else {
        setRole("User");
      }
    })
    .catch(error => {console.log(error)});
  }, [id]);

  const handleSubmit = async (e) => {
      e.preventDefault();
      const updatedAsset = {
          id: id,
          name: name,
          description: description,
          commissionDate: commissionDate,
          category: category,
          site: site,
          datasheet: datasheet
      };
      console.log(updatedAsset);
      try {
          await axios.put(`http://localhost:8080/api/users/${id}`, updatedAsset, {
              headers: {
                  Authorization: `Bearer ${cookies.JWT}`,
                  'Content-Type': 'application/json',
              },
          });
          alert("Asset updated successfully!");
      } catch (error) {
          console.error("Error updating the asset:", error);
          alert("Failed to update the asset.");
      }
      setIsEditing(false);
  };

  // lets the user input values
  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  // lets the user input values
  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);

  };

  return (
    <div className="assetBody">
      <div style={{display:'flex', width:'auto'}}>
        <input type="text" placeholder="Role" value={role} onChange={handleNameChange} className="nameInput" disabled={!isEditing}/>
      </div>
      <br></br>
      <div className="assetContainer">
        <div className="assetInfoContainer">
          <b>First Name</b>
          <input placeholder="Enter First Name" value={firstName} disabled={true}></input>
          <b>Last Name</b>
          <input placeholder="Enter Last Name" value={lastName} disabled={true}></input>
          <b>Email:</b>
          <input placeholder="Enter Email" value={email} disabled={true}></input>
          <b>Phone number:</b>
          <input placeholder="Enter Phone number" value={phoneNumber} disabled={true}></input>
          <b>Customer ID</b>
          <input placeholder="Enter Customer ID" value={id} disabled={true}></input>
        </div>
        <img alt="image" src={require("../Pages/resources/profileImage.png")} className="assetImage"/>
      </div>
      <div className="buttonContainer">
        <div className="leftButtonContainer">
          {isEditing && <button type="button" className="button">Delete</button>}
        </div>
        <div className="rightButtonContainer">
          
          {!isEditing &&
              <button type="button" className="button" onClick={back} ref={ backButtonReference }>Back</button>}

          {isEditing &&
              <button type="button" className="button" onClick={cancel} ref={ cancelButtonReference }>Cancel</button>}

          {!isEditing && isAdmin &&
              <button type="button" className="button" onClick={edit} ref={ editButtonReference }>Edit</button>}

          {isEditing &&
              <button type="button" className="button" onClick={handleSubmit} ref={ editButtonReference }>Confirm</button>}
        </div>
      </div>
    </div>
  )
}

export default Main