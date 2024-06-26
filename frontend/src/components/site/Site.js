import React, { useState, useRef, useEffect } from 'react';

import { Helmet } from 'react-helmet';

import Table from '../asset/AssetTable';

import './Site.css';
import { useParams } from 'react-router';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';
import HTTPRequest from '../../tools/HTTPRequest';
import { jwtDecode } from 'jwt-decode';
import URL from '../../tools/URL';
import {getAdminStatus, getManagerStatus, setAdminStatus} from "../../tools/globals";

const Site = (props) => {

  // Cookie initializer for react
  const [cookies, setCookie, removeCookie] = useCookies();
  const [isEditing, setIsEditing] = useState(false);

  const [siteName, setSiteName] = useState();
  const [siteID, setSiteID] = useState();

  const [compName, setCompanyName] = useState();
  const [compID, setCompanyID] = useState();

  // information variables
  const { id } = useParams();
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
          alert("asset updated successfully!");
      } catch (error) {
          console.error("Error updating the asset:", error);
          alert("Failed to update the asset.");
      }
      setIsEditing(false);
  };
  useEffect(() => {

      let endpoint = getAdminStatus() ? `${URL.BACKEND}/api/admin/sites/${id}` : `${URL.BACKEND}/api/user/sites/${id}`;
      HTTPRequest.get(endpoint, cookies.JWT)
      .then(response => {
        console.log(response);
        setSiteName(response.data.name);
        setSiteID(response.data.id);
        setCompanyName(response.data.company.name);
        setCompanyID(response.data.company.id);
      });
  }, []);

  return (
    <div className='companyBody'>
        <div style={{textAlign:'center'}}><input className='inputSiteName' value={siteName} disabled={!isEditing} /></div>
        <div className='companyContainer'>
            <div className='valueContainer'>
                <div style={{display: 'flex', flexDirection: 'column'}}>
                    <p>Site ID</p>
                    <input className='inputField' value={siteID} disabled={!isEditing}></input>
                    <p>Company Name</p>
                    <input className='inputField' value={compName} disabled={!isEditing}></input>
                    <p>Company ID</p>
                    <input className='inputField' value={compID} disabled={!isEditing}></input>
                    <br></br>
                    <button className='optionButton' onClick={showAssets}>Show Assets</button>
                    {(getAdminStatus() || getManagerStatus()) && <button className='optionButton' onClick={showUsers}>Show Users</button>}
                </div>
            </div>
            <div className='imageContainer'>
                <img alt="image" src={require("../../pages/resources/CompanyLogo.png")} className="companyImage"></img>
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

          {!isEditing && getAdminStatus() &&
              <button type="button" className="button" onClick={edit} >Edit</button>}

          {isEditing &&
              <button type="button" className="button" onClick={handleSubmit} >Confirm</button>}
        </div>
      </div>
    </div>
  )
}

export default Site;