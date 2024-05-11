import React, { useState, useRef, useEffect } from 'react'

import { Helmet } from 'react-helmet'

import Table from '../Asset/AssetTable'

import './Asset.css'
import { useParams } from 'react-router'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import { useNavigate } from 'react-router-dom'
import HTTPRequest from '../../tools/HTTPRequest'
import { jwtDecode } from 'jwt-decode'
import URL from "../../tools/URL";
import {getAdminStatus} from "../../tools/globals";

const AssetService = (props) => {

    // Cookie initializer for react
    const [cookies, setCookie, removeCookie] = useCookies();
    const [isEditing, setIsEditing] = useState(false);

    const [description, setDescription] = useState();
    const [intervalName, setIntervalName] = useState();
    const { id } = useParams();
    const { serviceID } = useParams();

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

    const handleDescriptionChange = (event) => {
        setDescription(event.target.value);

    };

    const handleIntervalNameChange = (event) => {
        setIntervalName(event.target.value);

    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const updateService = {
            intervalName: intervalName,
            description: description,
            asset: {
                id: id
            }};
        console.log(updateService);
        try {
            HTTPRequest.put(`${URL.BACKEND}/api/user/services/${serviceID}`, updateService, cookies.JWT);
            alert("Service updated successfully!");
        } catch (error) {
            console.error("Error updating the service:", error);
            alert("Failed to update the service.");
        }
        setIsEditing(false);
    };

    useEffect(() => {
        HTTPRequest.get(`${URL.BACKEND}/api/user/services/${serviceID}`, cookies.JWT)
            .then(response => {
                setDescription(response.data.description);
                setIntervalName(response.data.intervalName);
            })
            .catch(error => {console.log(error)});
    }, [serviceID, cookies.JWT]);

    return (
        <div className='companyBody'>
            <div style={{textAlign:'center'}}><h1>Service</h1></div>
            <div className='companyContainer'>
                <div className='valueContainer'>
                    <b>Service Description</b>
                    <input value={description} disabled={!isEditing} onChange={handleDescriptionChange}></input>
                    <br></br>
                    <b>Service Interval</b>
                    <input value={intervalName} disabled={!isEditing} onChange={handleIntervalNameChange}></input>
                </div>
                <div className='imageContainer'>
                    <img alt="image" src={require("../../Pages/resources/CompanyLogo.png")}
                         className="companyImage"></img>
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

export default AssetService;