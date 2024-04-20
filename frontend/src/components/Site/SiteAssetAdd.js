import React, { useState, useRef, useEffect } from 'react';

import { Helmet } from 'react-helmet';

import Table from '../Asset/AssetTable';
import URL from '../../tools/URL';

import '../Asset/Asset.css';
import { useParams } from 'react-router';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';
import HTTPRequest from '../../tools/HTTPRequest';
import { jwtDecode } from 'jwt-decode';
import QRCode from 'qrcode.react';
import QuantityIncrementer from '../Utility/QuantityIncrementer';

const Main = (props) => {

  // Cookie initializer for react
  const [cookies, setCookie, removeCookie] = useCookies();
  
  const [commissionDate, setCommissionDate] = useState();
  const [asset, setAsset] = useState();
  const [site, setSite] = useState();
  const [amount, setAmount] = useState(1);

  // information variables
  const { assetID } = useParams();
  const { siteID } = useParams();
  const { companyID } = useParams();
  const [name, setName] = useState();
  const [description, setDescription] = useState();
  const [attachmentLink, setAttachmentLink] = useState();
  const [attachmentName, setAttachmentName] = useState();
  const [category, setCategory] = useState();
  const [datasheet, setDatasheet] = useState();
    
  // back button functionality, goes back to the last page /asset.
  const navigate = useNavigate();

  const handleQuantityChange = (quantity) => {
    setAmount(quantity);
  }

  const back = () => {
    navigate(-1);
  }

  // Sends a get request to the backend and inputs the values of the asset.
  useEffect(() => {
    setSite({ id: siteID });
    HTTPRequest.get(`${URL.BACKEND}/api/assets/${assetID}`, cookies.JWT)
    .then(response => {
      setAsset(response.data);
      setName(response.data.name);
      setDescription(response.data.description);
      setCommissionDate(response.data.commissionDate);
      setCategory(response.data.category);
      setAttachmentName(response.data.datasheet.name);
      setAttachmentLink(response.data.datasheet.pdfUrl);
    })
    .catch(error => {console.log(error)});
  }, [assetID]);

  return (
    <div className="assetBody">
      <input type="text" placeholder="Name" name={name} value={name} className="nameInput"/>
      <br></br>
      <div className="assetContainer">
        <div className="assetInfoContainer">
          <b>Asset ID</b>
          <input placeholder="Enter Asset ID" value={assetID} disabled={true}></input>
          <span><b>Description</b><br></br></span>
          <textarea type="text" className='descriptionText' placeholder="Enter Description" name={description} value={description}/>
          <span><b>Asset Datasheet</b><br></br></span>
          <a href={attachmentLink} target="_blank" rel="noreferrer noopener">
            {attachmentName}.pdf
          </a>
        </div>
        <img alt="image" src={require("../../Pages/resources/AssetImage.png")} className="assetImage"/>
      </div>
      <div className="buttonContainer">
        <div className="leftButtonContainer">
        </div>
        <div className="rightButtonContainer">
          <button type="button" className="button" onClick={back}>Back</button>
          <div style={{gap:'0px'}}>
            <p style={{width:'auto', marginLeft:'18px', marginBottom:'0px', marginTop:'0px', color:'#083F4C', fontSize:14}}>Quantity</p>
            <QuantityIncrementer assetID={assetID} siteID={siteID} />
          </div>
        </div>
      </div>
    </div>
  )
}

export default Main