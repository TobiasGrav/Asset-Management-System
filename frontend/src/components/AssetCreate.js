import React, { useState, useRef, useEffect } from 'react'

import './AssetCreate.css'
import { useParams } from 'react-router'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import { useNavigate } from 'react-router-dom'
import HTTPRequest from '../tools/HTTPRequest'

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

  const nameReference = useRef(null);
  const descriptionReference = useRef(null);
    
  // back button functionality, goes back to the last page /asset.
  const navigate = useNavigate();

  const cancel = () => {
    navigate('/asset');
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

  // lets the user input values
  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  // lets the user input values
  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);

  };

  return (
    <div>
      <div style={{textAlign:'center', width:'100%', marginBottom:'32px'}}><input ref={nameReference} onChange={handleNameChange} placeholder='Input Name' style={{width:'100%', textAlign:'center', fontSize:32}} /></div>
      <div className='center'>
        <div className='infoContainer'>
          <b>Description</b>
          <textarea ref={descriptionReference} onChange={handleDescriptionChange} className='description' placeholder='' />
          <b>Is active?</b>
          <select>
            <option value='yes' >yes</option>
            <option value='no' >no</option>
          </select>
        </div>
        <div className='imageContainer'>
          <img src={require('../Pages/resources/AssetImage.png')} style={{width:360, height:360}}></img>
        </div>
      </div>
      <div className='buttonContainer1'>
        <button className='button' onClick={cancel} >Cancel</button>
        <button className='button' onSubmit={handleDescriptionChange} >Create</button>
      </div>
    </div>
  )
}

export default Main;