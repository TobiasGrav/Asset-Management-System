import React, { useState, useRef, useEffect } from 'react';

import URL from '../../tools/URL';

import { Helmet } from 'react-helmet';

import Table from '../Asset/AssetTable';

import './User.css';
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
  const [role, setRole] = useState(null);
  const [firstName, setFirstName] = useState(null);
  const [lastName, setLastName] = useState(null);
  const [email, setEmail] = useState(null);
  const [phoneNumber, setPhoneNumber] = useState(null);
  const [image, setImage] = useState("../../Pages/resources/nerd.png");

  const [companyName, setCompanyName] = useState(null);
  const [companyID, setCompanyID] = useState(null);

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);

  // Conditional variables
  const [isEditing, setIsEditing] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);

  const backButtonReference = useRef(null);
  const editButtonReference = useRef(null);
  const deleteButtonReference = useRef(null);
  const cancelButtonReference = useRef(null);

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
    navigate(-1);
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

  const showSites = () => {
    navigate('/user/' + userID + '/sites');
  }

  // Sends a get request to the backend and inputs the values of the asset.
  useEffect(() => {
    HTTPRequest.get(URL.BACKEND + `/api/admin/users/${userID}`, cookies.JWT)
    .then(response => {
      console.log(response);
      let asset = response.data;
      setFirstName(response.data.firstName);
      setLastName(response.data.lastName);
      setEmail(response.data.email);
      setPhoneNumber(response.data.phoneNumber);
      setCompanyName(response.data.company.name);
      setCompanyID(response.data.company.id);
      if(response.data.roles.length > 1) {
        setRole("Admin");
        setImage(require('../../Pages/resources/superior.png'));
      } else {
        setRole("User");
        setImage(require('../../Pages/resources/nerd.png'));
      }
    })
    .catch(error => {console.log(error)});
  }, [userID]);

  useEffect(() => {
    HTTPRequest.get(URL.BACKEND + '/api/admin/users/' + userID + '/sites', cookies.JWT)
    .then(response => {
      console.log(response);
      setData(response.data);
    })
    .catch(error => {console.log(error)});
  }, []);
  

  const handleSubmit = async (e) => {
      e.preventDefault();
      const updatedUser = {
          id: userID,
          firstName: firstName,
          lastName: lastName,
          email: email,
          phoneNumber: phoneNumber,
          role: role,
      };
      console.log(updatedUser);
      try {
          await axios.put(`${URL.BACKEND}/api/admin/users/${userID}`, updatedUser, {
              headers: {
                  Authorization: `Bearer ${cookies.JWT}`,
                  'Content-Type': 'application/json',
              },
          });
          alert("User updated successfully!");
      } catch (error) {
          console.error("Error updating the user:", error);
          alert("Failed to update the user.");
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

  // Handler for row click event using navigate
  const handleRowClicked = (row) => {
      navigate(`/site/${row.id}`); // Use navigate to change the route
  };

  return (
    <div className="assetBody">
      <div style={{display:'flex', width:'auto'}}>
        <input type="text" placeholder="Role" value={role} onChange={handleNameChange} className="nameInput" disabled={!isEditing}/>
      </div>
      <br></br>
      <div className="assetContainer">
        <div className="containerLeft">
          <b>First Name</b>
          <input className='input' placeholder="Enter First Name" value={firstName} disabled={!isEditing}></input>
          <b>Last Name</b>
          <input className='input' placeholder="Enter Last Name" value={lastName} disabled={!isEditing}></input>
          <b>Email:</b>
          <input className='input' placeholder="Enter Email" value={email} disabled={!isEditing}></input>
          <b>Phone number:</b>
          <input className='input' placeholder="Enter Phone number" value={phoneNumber} disabled={!isEditing}></input>
          <b>Customer ID</b>
          <input className='input' placeholder="Enter Customer ID" value={userID} disabled={!isEditing}></input>
        </div>
        <div className="containerCenter">
          <b>Company:</b>
          <input className='companyInput' placeholder="Enter First Name" value={companyName} disabled={true}></input>
          <b>Options:</b>
          <button className='button' onClick={showSites}>Show sites</button>
        </div>
        <img alt="image" src={image} className="assetImage"/>
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