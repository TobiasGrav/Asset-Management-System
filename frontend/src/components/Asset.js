import React, { useState, useRef, useEffect } from 'react'

import { Helmet } from 'react-helmet'

import Table from './table'

import './Asset.css'
import { useParams } from 'react-router'
import axios from 'axios'
import { useCookies } from 'react-cookie'

const Main = (props) => {

  const [cookies, setCookie, removeCookie] = useCookies();

  const { id } = useParams();
  
  const [name, setName] = useState();
  const [description, setDescription] = useState();
  const [attachmentLink, setAttachmentLink] = useState();
  const [attachmentName, setAttachmentName] = useState();

  const [isEditing, setIsEditing] = useState(false);
  var hasLoaded = false;

  const nameReference = useRef(null);
  const idReference = useRef(null);
  const descriptionReference = useRef(null);
  const backButtonReference = useRef(null);
  const editButtonReference = useRef(null);
  const deleteButtonReference = useRef(null);
  const cancelButtonReference = useRef(null);

  useEffect(() => {
    if(descriptionReference.current != null) {
      nameReference.current.disabled = isEditing;
      idReference.current.disabled = isEditing;
      descriptionReference.current.disabled = isEditing;
    }}, [isEditing]);

  const edit = () => {
    setIsEditing(true);
  }

  const cancel = () => {
    setIsEditing(false);
  }

  useEffect(() => {
    axios.get('http://localhost:8080/api/assets/' + id, {
      headers: {
        Authorization: 'Bearer ' + cookies.JWT,
        Accept: "application/json",
        'Content-Type': "application/json"
      }})
    
    .then(response => {
      console.log(response);
      setName(response.data.asset.category.name);
      setDescription(response.data.asset.description);
      setAttachmentLink(response.data.asset.datasheet.pdfUrl);
      setAttachmentName(response.data.asset.datasheet.name);
    })

    .catch(error => {
      console.log(error);
    });
  }, []);
  
  return (
    <div className="assetBody">
      <input
        type="text"
        placeholder="Name"
        value={name}
        className="nameInput"
        ref={ nameReference }
      />
      <div className="assetContainer">
        <div className="assetInfoContainer">
          <span>
            <span>Asset ID</span>
            <br></br>
          </span>
          <input
            type="text"
            placeholder={ id }
            className="inputID"
            ref={ idReference }
          />
          <span>
            <span>Description</span>
            <br></br>
          </span>
          <textarea
            placeholder="placeholder"
            value={description}
          ></textarea>
          <span>
            <span>Asset Attachments</span>
            <br></br>
          </span>
          <a
            href={ attachmentLink }
            target="_blank"
            rel="noreferrer noopener"
          >
            { attachmentName }.pdf
          </a>
        </div>
        <img
          alt="image"
          src="/metabokompressor-500h.png"
          className="assetImage"
        />
      </div>
      <div className="buttonContainer">
        <div className="leftButtonContainer">
          {isEditing && <button type="button" className="button">
            Delete
          </button>
          }
        </div>
        <div className="rightButtonContainer">
          
          {!isEditing && <button 
            type="button" 
            className="button"
            ref={ backButtonReference }
          >
            Back
          </button>
          }

          {isEditing && <button 
            type="button" 
            className="button"
            onClick={cancel}
            ref={ cancelButtonReference }
          >
            Cancel
          </button>
          }

          {!isEditing && <button 
            type="button" 
            className="button" 
            onClick={edit}
            ref={ editButtonReference }
          >
            edit
          </button>
          }
        </div>
      </div>
    </div>
  )
}

export default Main
