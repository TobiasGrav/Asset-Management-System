import React, { useState, useRef, useEffect } from 'react'

import { Helmet } from 'react-helmet'

import Table from './table'

import './Asset.css'
import { useParams } from 'react-router'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import { useNavigate } from 'react-router-dom';


const Main = (props) => {

  const [cookies, setCookie, removeCookie] = useCookies();

  const { id } = useParams();

  const [name, setName] = useState();
  const [description, setDescription] = useState();
  const [attachmentLink, setAttachmentLink] = useState();
  const [attachmentName, setAttachmentName] = useState();
  const [category, setCategory] = useState();
  const [commissionDate, setCommissionDate] = useState();
  const [site, setSite] = useState();
  const [datasheet, setDatasheet] = useState();

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

  const navigate = useNavigate();
  const back = () => {
    navigate('/asset/');
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

  useEffect(() => {
    // Fetch asset details on component mount
    axios.get(`http://localhost:8080/api/assets/${id}`, {
      headers: {
        Authorization: `Bearer ${cookies.JWT}`,
          Accept: "application/json"
      },
    })
        .then(response => {
          const asset = response.data;
          setName(asset.name);
          setDescription(asset.description);
          setCommissionDate(asset.commissionDate);
          setCategory(asset.category);
          setSite(asset.site);
          setDatasheet(asset.datasheet);
        })
        .catch(error => console.error("Fetching asset failed", error));
  }, [id, cookies.JWT]);

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
  };

  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);
  };

  return (
    <div className="assetBody">
      <input type="text" placeholder="Name" name={name} value={name} onChange={handleNameChange} className="nameInput" disabled={!isEditing}/>
      <div className="assetContainer">
        <div className="assetInfoContainer">
          <span><span>Asset ID</span><br></br></span>
          <textarea placeholder="Enter Asset ID" value={id} disabled={isEditing}></textarea>
          <span><span>Description</span><br></br></span>
          <input type="text" placeholder="Enter Description" name={description} value={description} onChange={handleDescriptionChange} className="inputID" disabled={!isEditing}/>
          <span><span>Asset Datasheet</span><br></br></span>
          <a href={attachmentLink} target="_blank" rel="noreferrer noopener">
            {attachmentName}.pdf
          </a>
        </div>
        <img alt="image" src="/metabokompressor-500h.png" className="assetImage"/>
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

          {!isEditing &&
              <button type="button" className="button" onClick={edit} ref={ editButtonReference }>Edit</button>}

          {isEditing &&
              <button type="button" className="button" onClick={handleSubmit} ref={ editButtonReference }>Confirm</button>}
        </div>
      </div>
    </div>
  )
}

export default Main
