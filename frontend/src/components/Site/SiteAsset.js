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

const Main = ({ value, size }) => {

  // Cookie initializer for react
  const [cookies, setCookie, removeCookie] = useCookies();

  // information variables
  const { assetID } = useParams();
  const { siteID } = useParams();
  const { companyID } = useParams();
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
  const [userRole, setUserRole] = useState();

  const nameReference = useRef(null);
  const idReference = useRef(null);
  const descriptionReference = useRef(null);
  const backButtonReference = useRef(null);
  const editButtonReference = useRef(null);
  const deleteButtonReference = useRef(null);
  const cancelButtonReference = useRef(null);

  // Checks if the current user is an admin, and if so isAdmin is true. It decodes the JWT and extracts the roles.
  useEffect(() => {
    if(cookies.JWT != null) {
      setUserRole("user");
      jwtDecode(cookies.JWT).roles.forEach(role => {
        if(role.authority === "ADMIN") {
              setUserRole("admin");
        }
      });
    }
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
    if(userRole != null) {
      HTTPRequest.get(`${URL.BACKEND}/api/${userRole}/sites/${siteID}/assetsOnSite/${assetID}`, cookies.JWT)
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
    }
  }, [userRole]);

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

  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);

  };

  const printFrameRef = useRef();
  const [contextMenu, setContextMenu] = useState(null);

  const handlePrint = () => {
    const qrCanvas = document.querySelector('canvas');
    const imgData = qrCanvas.toDataURL('image/png');
    const img = printFrameRef.current.contentDocument.createElement('img');
    img.onload = () => {
      printFrameRef.current.contentWindow.print();
    };
    img.src = imgData;
    // Clear the iframe's content
    printFrameRef.current.contentDocument.body.innerHTML = '';
    printFrameRef.current.contentDocument.body.appendChild(img);
  };

  const handleContextMenu = (event) => {
    event.preventDefault();

    // Remove any existing context menu
    if (contextMenu) {
      contextMenu.remove();
    }

    const menu = document.createElement('div');
    menu.innerHTML = 'Print QR';
    menu.style.position = 'fixed';
    menu.style.left = `${event.clientX}px`;
    menu.style.top = `${event.clientY}px`;
    menu.style.backgroundColor = '#fff';
    menu.style.border = '1px solid #ddd';
    menu.style.padding = '5px';
    menu.style.cursor = 'pointer';
    menu.style.zIndex = '1000';
    menu.onclick = handlePrint;
    menu.onblur = () => {
      menu.remove();
      setContextMenu(null);
    };

    // Append to body and focus
    document.body.appendChild(menu);
    menu.focus();

    // Update the state to the current menu
    setContextMenu(menu);
  };

  useEffect(() => {
    const handleGlobalClick = (e) => {
      if (contextMenu && !contextMenu.contains(e.target)) {
        contextMenu.remove();
        setContextMenu(null);
      }
    };

    document.addEventListener('click', handleGlobalClick);
    return () => {
      document.removeEventListener('click', handleGlobalClick);
    };
  }, [contextMenu]);

  return (
    <div className="assetBody">
      <input type="text" placeholder="Name" name={name} value={name} onChange={handleNameChange} className="nameInput" disabled={!isEditing}/>
      <br></br>
      <div className="assetContainer">
        <div className="assetInfoContainer">
          <b>Asset Site ID</b>
          <input placeholder="Enter Asset ID" value={assetID} disabled={true}></input>
          <span><b>Description</b><br></br></span>
          <textarea type="text" className='descriptionText' placeholder="Enter Description" name={description}
                    value={description} onChange={handleDescriptionChange} disabled={!isEditing}/>
          <span><b>Asset Datasheet</b><br></br></span>
          <a href={attachmentLink} target="_blank" rel="noreferrer noopener">
            {attachmentName}.pdf
          </a>
          <br></br>
          <b>Asset QR Code</b>
          <div onContextMenu={handleContextMenu}>
            <QRCode value={value} size={size} level="H" bgColor="#ffffff" fgColor="#000000" includeMargin={true}/>
            <iframe ref={printFrameRef} style={{display: 'none'}} title="Print Frame"/>
          </div>
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

          {!isEditing && userRole &&
              <button type="button" className="button" onClick={edit} ref={ editButtonReference }>Edit</button>}

          {isEditing &&
              <button type="button" className="button" onClick={handleSubmit} ref={ editButtonReference }>Confirm</button>}
        </div>
      </div>
    </div>
  )
}

export default Main