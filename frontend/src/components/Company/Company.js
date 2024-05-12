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
import URL from "../../tools/URL";

const Company = (props) => {

  // Cookie initializer for react
  const [cookies, setCookie, removeCookie] = useCookies();
  const [isEditing, setIsEditing] = useState(false);
  const [isAdmin, setIsAdmin] = useState(true);

  const [companyName, setCompanyName] = useState();
  //const [companyID, setCompanyID] = useState();

  // information variables
  const { companyID } = useParams();

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

    const showUsers = () => {
      navigate(`/company/${companyID}/users`);
    }

    const showSites = () => {
      navigate(`/company/${companyID}/sites`);
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        const updatedCompany = {
            name: companyName
        };
        console.log(updatedCompany);
        try {
            HTTPRequest.put(`${URL.BACKEND}/api/companies/${companyID}`, updatedCompany, cookies.JWT);
            alert("Company updated successfully!");
        } catch (error) {
            console.error("Error updating the company:", error);
            alert("Failed to update the asset.");
        }
        setIsEditing(false);
    };

    useEffect(() => {
        HTTPRequest.get(`${URL.BACKEND}/api/admin/companies/${companyID}`, cookies.JWT)
        .then(response => {
          setCompanyName(response.data.name);
          setCompanyID(response.data.id);
        })
        .catch(error => {console.log(error)});
    }, [companyID, cookies.JWT]);

  return (
    <div className='companyBody'>
        <div style={{textAlign:'center'}}><h1>{companyName}</h1></div>
        <div className='companyContainer'>
            <div className='valueContainer'>
                <b>Company ID</b>
                <input value={companyID}></input>
                <b>Options</b>
                <button onClick={showUsers} >Show Users</button>
                <button onClick={showSites} >Show Sites</button>
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