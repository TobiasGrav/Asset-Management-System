import React, { useState, useRef, useEffect } from 'react';

import { Helmet } from 'react-helmet';

import Table from '../Asset/AssetTable';

import './Site.css';
import { useParams } from 'react-router';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';
import HTTPRequest from '../../tools/HTTPRequest';
import { jwtDecode } from 'jwt-decode';
import URL from '../../tools/URL';

const Company = (props) => {

  // Cookie initializer for react
  const [cookies, setCookie, removeCookie] = useCookies();
  const [isEditing, setIsEditing] = useState(false);
  const [isAdmin, setIsAdmin] = useState();

  const [siteName, setSiteName] = useState();
  const [siteID, setSiteID] = useState();

  const [compName, setCompanyName] = useState();
  const [compID, setCompanyID] = useState();

  // information variables
  const { id } = useParams();
  const { companyID } = useParams();

  useEffect(() => {
    if(cookies.JWT != null) {
        setIsAdmin("user");
        jwtDecode(cookies.JWT).roles.forEach(role => {
            if(role.authority === "ADMIN") {
                setIsAdmin("admin");
            }
        });
    }
  }, []);

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
  const showAssets = () => {
    navigate(`/company/${companyID}/site/${id}/assets`);
  }
  const showUsers = () => {
    navigate(`/company/${companyID}/site/${id}/users`);
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
          await axios.put(`${URL.BACKEND}/api/assets/${id}`, updatedAsset, {
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
  useEffect(() => {
    if(isAdmin != null) {
      HTTPRequest.get(`${URL.BACKEND}/api/${isAdmin}/sites/${id}`, cookies.JWT)
      .then(response => {
        console.log(response);
        setSiteName(response.data.name);
        setSiteID(response.data.id);
        setCompanyName(response.data.company.name);
        setCompanyID(response.data.company.id);
      });
    }
  }, [isAdmin]);

  return (
    <div className='companyBody'>
        <div style={{textAlign:'center'}}><input className='inputSiteName' value={siteName} disabled={!isEditing} /></div>
        <div className='companyContainer'>
            <div className='valueContainer'>
              <div style={{display:'flex', flexDirection:'column'}}>
                <p>Site ID</p>
                <input className='inputField' value={siteID} disabled={!isEditing}></input>
                <p>This site belongs to:</p>
                <p>Company Name</p>
                <input className='inputField' value={compName} disabled={!isEditing}></input>
                <p>Company ID</p>
                <input className='inputField' value={compID} disabled={!isEditing}></input>
                <button className='optionButton' onClick={showAssets}>Show Assets</button>
                <button className='optionButton' onClick={showUsers}>Show Users</button>
              </div>
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