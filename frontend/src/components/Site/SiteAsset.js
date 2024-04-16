import React, { useState, useRef, useEffect } from 'react'

import { Helmet } from 'react-helmet'

import Table from '../Asset/AssetTable'

import URL from '../../tools/URL';

import '../Asset/Asset.css'
import { useParams } from 'react-router'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import { useNavigate } from 'react-router-dom'
import HTTPRequest from '../../tools/HTTPRequest'
import { jwtDecode } from 'jwt-decode'
import QRCode from 'qrcode.react'

const Main = (props) => {

  // Cookie initializer for react
  const [cookies, setCookie, removeCookie] = useCookies();

  // information variables
  const { assetID } = useParams();
  const { siteID } = useParams();
  const [name, setName] = useState();
  const [description, setDescription] = useState();
  const [attachmentLink, setAttachmentLink] = useState();
  const [attachmentName, setAttachmentName] = useState();
  const [category, setCategory] = useState();
  const [commissionDate, setCommissionDate] = useState();
  const [site, setSite] = useState();
  const [datasheet, setDatasheet] = useState();

  // Conditional variables
  const [isEditing, setIsEditing] = useState(false);
  const [isAdmin, setIsAdmin] = useState(false);

  const nameReference = useRef(null);
  const idReference = useRef(null);
  const descriptionReference = useRef(null);
  const backButtonReference = useRef(null);
  const editButtonReference = useRef(null);
  const deleteButtonReference = useRef(null);
  const cancelButtonReference = useRef(null);

  // Checks if the current user is an admin, and if so isAdmin is true. It decodes the JWT and extracts the roles.
  useEffect(() => {
    console.log(siteID);
    jwtDecode(cookies.JWT).roles.forEach(role => {
      if(role.authority === "ADMIN") {
            setIsAdmin(true);
      }
    })
  })


  // Prevents the user from inputing values when not in editing mode.
  useEffect(() => {
    if(descriptionReference.current != null) {
      nameReference.current.disabled = isEditing;
      idReference.current.disabled = isEditing;
      descriptionReference.current.disabled = isEditing;
    }}, [isEditing]);
    
  // back button functionality, goes back to the last page /asset.
  const navigate = useNavigate();
  const back = () => {
    navigate(`/site/${siteID}/assets`);
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
    HTTPRequest.get(`${URL.BACKEND}/api/assetOnSites/${assetID}`, cookies.JWT)
    .then(response => {
      console.log(response);
      let asset = response.data;
      setName(response.data.asset.name);
      setDescription(response.data.asset.description);
      setCommissionDate(response.data.asset.commissionDate);
      setCategory(response.data.asset.category);
      setSite(response.data.asset.site);
      setAttachmentName(response.data.asset.datasheet.name);
      setAttachmentLink(response.data.asset.datasheet.pdfUrl);
    });
  }, [assetID]);

  //const handleSubmit = async (e) => {
  //    e.preventDefault();
  //    const updatedAsset = {
  //        id: id,
  //        name: name,
  //        description: description,
  //        commissionDate: commissionDate,
  //        category: category,
  //        site: site,
  //        datasheet: datasheet
  //    };
  //    console.log(updatedAsset);
  //    try {
  //        await axios.put(`http://localhost:8080/api/assets/${id}`, updatedAsset, {
  //            headers: {
  //                Authorization: `Bearer ${cookies.JWT}`,
  //                'Content-Type': 'application/json',
  //            },
  //        });
  //        alert("Asset updated successfully!");
  //    } catch (error) {
  //        console.error("Error updating the asset:", error);
  //        alert("Failed to update the asset.");
  //    }
  //    setIsEditing(false);
  //};

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
      <input type="text" placeholder="Name" name={name} value={name} onChange={handleNameChange} className="nameInput" disabled={!isEditing}/>
      <br></br>
      <div className="assetContainer">
        <div className="assetInfoContainer">
          <b>Asset Site ID</b>
          <input placeholder="Enter Asset ID" value={assetID} disabled={true}></input>
          <span><b>Description</b><br></br></span>
          <textarea type="text" className='descriptionText' placeholder="Enter Description" name={description} value={description} onChange={handleDescriptionChange} disabled={!isEditing}/>
          <span><b>Asset Datasheet</b><br></br></span>
          <a href={attachmentLink} target="_blank" rel="noreferrer noopener">
            {attachmentName}.pdf
          </a>
          <br></br>
          <b>Asset QR Code</b>
          <QRCode value={`${URL.BACKEND}/site/${siteID}/assets/${assetID}`} size={256} level={"H"} bgColor={"#ffffff"} fgColor={"#000000"} includeMargin={true}/>
        </div>
        <img alt="image" src={require("../../Pages/resources/AssetImage.png")} className="assetImage"/>
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