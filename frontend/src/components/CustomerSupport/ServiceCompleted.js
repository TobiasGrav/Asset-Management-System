import React, { useState, useRef, useEffect } from 'react';

import { Helmet } from 'react-helmet';

import Table from '../Asset/AssetTable';

import './ServuceCompleted.css';
import { useParams } from 'react-router';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';
import HTTPRequest from '../../tools/HTTPRequest';
import { jwtDecode } from 'jwt-decode';
import URL from '../../tools/URL';
import {format} from "date-fns";

const ServiceCompleted = (props) => {

    // Cookie initializer for react
    const [cookies, setCookie, removeCookie] = useCookies();
    const [isEditing, setIsEditing] = useState(false);
    const [isAdmin, setIsAdmin] = useState();

    const [assetName, setAssetName] = useState();
    const [assetOnSiteTag, setAssetOnSiteTag] = useState();

    const [serviceUrl, setServiceUrl] = useState();
    const [timeCompleted, setTimeCompleted] = useState();
    const [comment, setComment] = useState();

    // information variables
    const { serviceCompletedID } = useParams();

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
    const showAsset = () => {
        navigate(`/support/${serviceCompletedID}`);
    }
    const showService = () => {
        navigate(`/support/${serviceCompletedID}`);
    }

    const formatLocalDateTime = (localDateTime) => {
        return format(new Date(localDateTime), 'dd.MM.yyyy HH:mm');
    };

    useEffect(() => {
        if(isAdmin != null) {
            HTTPRequest.get(`${URL.BACKEND}/api/servicesCompleted/${serviceCompletedID}`, cookies.JWT)
                .then(response => {
                    console.log(response);
                    setAssetName(response.data.assetOnSite.asset.name);
                    setAssetOnSiteTag(response.data.assetOnSite.assetOnSiteTag);
                    setServiceUrl(response.data.service.serviceUrl);
                    setTimeCompleted(response.data.timeCompleted !== null ? formatLocalDateTime(response.data.timeCompleted) : "Not Completed Yet");
                });
        }
    }, [isAdmin]);

    return (
        <div className='companyBody'>
            <div style={{textAlign:'center'}}><input className='inputSiteName' value={assetName} disabled={!isEditing} /></div>
            <div className='companyContainer'>
                <div className='valueContainer'>
                    <h3>Asset On Site Tag</h3>
                    <input className='inputField' value={assetOnSiteTag} disabled={!isEditing}></input>
                    <h3>Service URL</h3>
                    <input className='inputField' value={serviceUrl} disabled={!isEditing}></input>
                    <h3>Completion Date</h3>
                    <input className='inputField' value={timeCompleted} disabled={!isEditing}></input>
                    <h3>Options</h3>
                    <button onClick={showAsset}>Show Asset On Site</button>
                    <button onClick={showService}>Show Service</button>

                    <h3>Comments</h3>
                    <textarea value={comment}></textarea>
                    <button onClick={showService}>Add Comment</button>
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

export default ServiceCompleted;