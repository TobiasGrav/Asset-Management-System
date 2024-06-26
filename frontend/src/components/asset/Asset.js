import React, { useState, useRef, useEffect } from 'react'

import { Helmet } from 'react-helmet'

import Table from './AssetTable'

import './Asset.css'
import { useParams } from 'react-router'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import { useNavigate } from 'react-router-dom'
import HTTPRequest from '../../tools/HTTPRequest'
import { jwtDecode } from 'jwt-decode'
import URL from "../../tools/URL";
import {getAdminStatus} from "../../tools/globals";

const Main = (props) => {

  // Cookie initializer for react
  const [cookies, setCookie, removeCookie] = useCookies();

  // information variables
  const { id } = useParams();
  const [name, setName] = useState();
  const [description, setDescription] = useState();
  const [attachmentLink, setAttachmentLink] = useState();
  const [attachmentName, setAttachmentName] = useState();
  const [category, setCategory] = useState();
  const [commissionDate, setCommissionDate] = useState();
  const [site, setSite] = useState();
  const [datasheet, setDatasheet] = useState();
  const [partNumber, setPartNumber] = useState();

  // Conditional variables
  const [isEditing, setIsEditing] = useState(false);

  const nameReference = useRef(null);
  const idReference = useRef(null);
  const descriptionReference = useRef(null);
  const backButtonReference = useRef(null);
  const editButtonReference = useRef(null);
  const deleteButtonReference = useRef(null);
  const cancelButtonReference = useRef(null);
  const partNumberReference = useRef(null);

  // Prevents the user from inputing values when not in editing mode.
  useEffect(() => {
    if(descriptionReference.current != null) {
      nameReference.current.disabled = isEditing;
      idReference.current.disabled = isEditing;
      descriptionReference.current.disabled = isEditing;
      partNumberReference.current.disabled = isEditing;
    }}, [isEditing]);
    
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

  // Sends a get request to the backend and inputs the values of the asset.
  useEffect(() => {
    HTTPRequest.get(`${URL.BACKEND}/api/assets/${id}`, cookies.JWT)
    .then(response => {
      console.log(response);
      let asset = response.data;
      setName(asset.name);
      setDescription(asset.description);
      setPartNumber(asset.partNumber)
      setCommissionDate(asset.commissionDate);
      setCategory(asset.category);
      setSite(asset.site);
      setAttachmentName(asset.datasheet.name);
      setAttachmentLink(asset.datasheet.pdfUrl);
    })
    .catch(error => {console.log(error)});
  }, [id]);

  const handleSubmit = async (e) => {
      e.preventDefault();
      const updatedAsset = {
          id: id,
          name: name,
          description: description,
          partNumber: partNumber,
          commissionDate: commissionDate,
          category: category,
          site: site,
          datasheet: datasheet
      };
      console.log(updatedAsset);
      try {
          HTTPRequest.put(`${URL.BACKEND}/api/assets/${id}`, updatedAsset, cookies.JWT);
          alert("asset updated successfully!");
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

    const handlePartNumberChange = (event) => {
        setPartNumber(event.target.value);
    };

    const showServices = () => {
        navigate(`/asset/${id}/service`);
    }

    const showSpareParts = () => {
        navigate(`/asset/${id}/sparepart`);
    }

  return (
    <div className="assetBody">
      <input type="text" placeholder="Name" name={name} value={name} onChange={handleNameChange} className="nameInput" disabled={!isEditing}/>
      <br></br>
      <div className="assetContainer">
          <div className="assetInfoContainer">
              <b>Part Number</b>
              <input placeholder="Enter Part Number" value={partNumber} onChange={handlePartNumberChange}></input>
              <span><b>Description</b><br></br></span>
              <textarea type="text" className='descriptionText' placeholder="Enter Description" name={description}
                        value={description} onChange={handleDescriptionChange} disabled={!isEditing}/>
              <span><b>Asset Datasheet</b><br></br></span>
              <a href={attachmentLink} target="_blank" rel="noreferrer noopener">
                  {attachmentName}.pdf
              </a>
              <br></br>
              <b>Options</b>
              <button onClick={showServices}>Show Services</button>
              <button onClick={showSpareParts}>Show Spare Parts</button>
          </div>
          <img alt="image" src={require("../../pages/resources/AssetImage.png")} className="assetImage"/>
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

          {!isEditing && getAdminStatus() &&
              <button type="button" className="button" onClick={edit} ref={ editButtonReference }>Edit</button>}

          {isEditing &&
              <button type="button" className="button" onClick={handleSubmit} ref={ editButtonReference }>Confirm</button>}
        </div>
      </div>
    </div>
  )
}

export default Main