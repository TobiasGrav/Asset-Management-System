import React, { useState, useRef, useEffect } from 'react'

import { Helmet } from 'react-helmet'

import Table from '../Asset/AssetTable'

import './Company.css'
import { useParams } from 'react-router'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import { useNavigate } from 'react-router-dom'
import HTTPRequest from '../../tools/HTTPRequest'
import { jwtDecode } from 'jwt-decode'

const Company = (props) => {

  // Cookie initializer for react
  const [cookies, setCookie, removeCookie] = useCookies();
  const [isEditing, setIsEditing] = useState(false);
  const [isAdmin, setIsAdmin] = useState(true);

  const [companyName, setCompanyName] = useState();
  const [companyID, setCompanyID] = useState();

  // information variables
  const { id } = useParams();

    // back button functionality, goes back to the last page /asset.
    const navigate = useNavigate();
    const back = () => {
      navigate('/company/');
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
            await axios.put(`http://localhost:8080/api/assets/${id}`, updatedAsset, {
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

    //useEffect(() => {
    //    HTTPRequest.get(`http://localhost:8080/api/sites/${id}`, cookies.JWT)
    //    .then(response => {
    //      console.log(response);
    //      setCompanyName(response.data.company.name);
    //      setCompanyID(response.data.company.id);
    //    })
    //    .catch(error => {console.log(error)});
    //}, [id, cookies.JWT]);

  return (
    <div className='companyBody'>
        <div style={{textAlign:'center'}}><h1>Company name</h1></div>
        <div className='companyContainer'>
            <div className='valueContainer'>
                <p>Company ID</p>
                <input value={"text"}></input>
            </div>
            <div className='imageContainer'>
                <img alt="image" src={require("../../Pages/resources/CompanyLogo.png")} className="companyImage"></img>
            </div>
        </div>
        <div className="buttonContainer">
        <div className="leftButtonContainer">
          {isEditing && <button type="button" className="button">Delete</button>}
        </div>
        <div className="rightButtonContainer">
          
          {!isEditing &&
              <button type="button" className="button" onClick={back} >Back</button>}

          {isEditing &&
              <button type="button" className="button" onClick={cancel} >Cancel</button>}

          {!isEditing && isAdmin &&
              <button type="button" className="button" onClick={edit} >Edit</button>}

          {isEditing &&
              <button type="button" className="button" onClick={handleSubmit} >Confirm</button>}
        </div>
      </div>
    </div>
  )
}

export default Company;