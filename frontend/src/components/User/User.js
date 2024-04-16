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
  const { id } = useParams();
  const [role, setRole] = useState(null);
  const [firstName, setFirstName] = useState(null);
  const [lastName, setLastName] = useState(null);
  const [email, setEmail] = useState(null);
  const [phoneNumber, setPhoneNumber] = useState(null);

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
    navigate(`/user`);
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
    navigate('/user/' + id + '/sites');
  }

  // Sends a get request to the backend and inputs the values of the asset.
  useEffect(() => {
    HTTPRequest.get(URL.URL + `/api/admin/users/${id}`, cookies.JWT)
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
      } else {
        setRole("User");
      }
    })
    .catch(error => {console.log(error)});
  }, [id]);

  useEffect(() => {
    HTTPRequest.get(URL.URL + '/api/admin/users/' + id + '/sites', cookies.JWT)
    .then(response => {
      console.log(response);
      setData(response.data);
    })
    .catch(error => {console.log(error)});
  }, []);
  

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
          await axios.put(URL.URL + `/api/admin/users/${id}`, updatedAsset, {
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

    const columns = [
      {
          name: 'Name',
          selector: row => row.name,
          sortable: true,
      },
      {
          name: 'Site ID',
          selector: row => row.id,
          sortable: true,
      },
  ];

  // Handler for row click event using navigate
  const handleRowClicked = (row) => {
      navigate(`/site/${row.id}`); // Use navigate to change the route
  };

  const customStyles = {
      headCells: {
          style: {
              backgroundColor: '#E7EDF0',
              color: '#333',
              paddingLeft: '12px',
              paddingRight: '12px',
          },
      },
      cells: {
          style: {
              paddingLeft: '12px',
              paddingRight: '12px',
              borderColor: '#ddd',
              cursor: 'default', // Ensures the cursor indicates the cell is not editable
          },
      },
      rows: {
          style: {
              '&:nth-of-type(even)': {
                  backgroundColor: '#E7EDF0', // Even rows background color
              },
              '&:nth-of-type(odd)': {
                  backgroundColor: '#F9FBFC', // Even rows background color
              },
              '&:hover': {
                  backgroundColor: '#ddd', // Hover row background color
              },
              borderColor: '#ddd', // Row border color
          },
      },
      pagination: {
          style: {
              marginTop: '20px', // Pagination margin top
          },
          pageButtonsStyle: {
              borderRadius: '4px', // Pagination buttons border radius
              backgroundColor: '#E7EDF0', // Pagination buttons background color
              borderColor: '#ddd', // Pagination buttons border color
              color: '#333', // Pagination buttons text color
              height: 'auto',
              padding: '8px', // Pagination buttons padding
              '&:hover': {
                  backgroundColor: '#ddd', // Pagination buttons hover background color
              },
          },
      },
  };

  const noDataComponent = <b style={{padding:"10px"}}>Does not belong to any site</b>;

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
          <input className='input' placeholder="Enter Customer ID" value={id} disabled={!isEditing}></input>
        </div>
        <div className="containerCenter">
          <b>Company:</b>
          <input className='companyInput' placeholder="Enter First Name" value={companyName} disabled={true}></input>
          <b>Options:</b>
          <button className='button' onClick={showSites}>Show sites</button>
        </div>
        <img alt="image" src={require("../../Pages/resources/profileImage.png")} className="assetImage"/>
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