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

const Service = (props) => {

    // Cookie initializer for react
    const [cookies, setCookie, removeCookie] = useCookies();
    const [isEditing, setIsEditing] = useState(false);
    const [isAdmin, setIsAdmin] = useState(true);

    const [description, setDescription] = useState();
    const [intervalName, setIntervalName] = useState();
    const { id } = useParams();

    // information variables
    const { serviceID } = useParams();

    // back button functionality, goes back to the last page /asset.
    const navigate = useNavigate();
    const back = () => {
        navigate(-1);
    }

    // If user doesn't have a JWT cookie it will redirect them to the login page.
    useEffect(() => {
        if(cookies.JWT == null) {
            navigate('/login');
        }
    }, []);

    const handleSubmit = () => {
        const data = {
            intervalName: intervalName,
            description: description,
            asset: {
                id: id
            }};
        HTTPRequest.post(`${URL.BACKEND}/api/services`, data, cookies.JWT)
            .then(response => {
                console.log(response);
                navigate(-1);
            })
            .catch(error => {alert('Something went wrong!\nService not created')});
    };

    useEffect(() => {
        HTTPRequest.get(`${URL.BACKEND}/api/services/${serviceID}`, cookies.JWT)
            .then(response => {
                setDescription(response.data.description);
                setIntervalName(response.data.intervalName);
            })
            .catch(error => {console.log(error)});
    }, [serviceID, cookies.JWT]);

    const handleDescriptionChange = (event) => {
        setDescription(event.target.value);

    };

    const handleIntervalNameChange = (event) => {
        setIntervalName(event.target.value);

    };

    return (
        <div className='companyBody'>
            <div style={{textAlign:'center'}}><h1>Create Service</h1></div>
            <div className='companyContainer'>
                <div className='valueContainer'>
                    <b>Service Description</b>
                    <input value={description} onChange={handleDescriptionChange}></input>
                    <br></br>
                    <b>Service Interval</b>
                    <input value={intervalName} onChange={handleIntervalNameChange}></input>
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

                    {<button type="button" className="button" onClick={back} >Back</button>}

                    {<button type="button" className="button" onClick={handleSubmit} >Confirm</button>}
                </div>
            </div>
        </div>
    )
}

export default Service;