import React, { useState, useRef, useEffect } from 'react'

import { Helmet } from 'react-helmet'

import Table from './/AssetTable'

import './Asset.css'
import { useParams } from 'react-router'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import { useNavigate } from 'react-router-dom'
import HTTPRequest from '../../tools/HTTPRequest'
import { jwtDecode } from 'jwt-decode'
import URL from "../../tools/URL";
import {getAdminStatus} from "../../tools/globals";

const AssetSparePart = (props) => {

    // Cookie initializer for react
    const [cookies] = useCookies();
    const [isEditing, setIsEditing] = useState(false);

    const [positionDiagramUrl, setPositionDiagramUrl] = useState();
    const [numberOfParts, setNumberOfParts] = useState();
    const { id } = useParams();
    const { sparePartID } = useParams();
    const [isNumberValid, setIsNumberValid] = useState(true);


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

    const handlePositionDiagramUrlChange = (event) => {
        setPositionDiagramUrl(event.target.value);
    };

    const handleNumberOfPartsChange = (event) => {
        const value = event.target.value;
        if (/^\d*$/.test(value)) {
            setNumberOfParts(value);
            setIsNumberValid(true);
        } else {
            setIsNumberValid(false);
        }

    };

    const handleSubmit = async (e) => {
        if (!/^\d+$/.test(numberOfParts)) {
            setIsNumberValid(false);
            return;
        }
        setIsNumberValid(true);
        e.preventDefault();
        const updateService = {
            numberOfParts: numberOfParts,
            positionDiagramUrl: positionDiagramUrl,
            asset: {
                id: id
            }};
        console.log(updateService);
        try {
            HTTPRequest.put(`${URL.BACKEND}/api/admin/spareParts/${sparePartID}`, updateService, cookies.JWT);
            alert("Spare part updated successfully!");
        } catch (error) {
            console.error("Error updating the spare part:", error);
            alert("Failed to update the spare part.");
        }
        setIsEditing(false);
    };

    useEffect(() => {
        HTTPRequest.get(`${URL.BACKEND}/api/user/spareParts/${sparePartID}`, cookies.JWT)
            .then(response => {
                setPositionDiagramUrl(response.data.positionDiagramUrl);
                setNumberOfParts(response.data.numberOfParts);
            })
            .catch(error => {console.log(error)});
    }, [sparePartID, cookies.JWT]);

    return (
        <div className='companyBody'>
            <div style={{textAlign:'center'}}><h1>Spare Part</h1></div>
            <div className='companyContainer'>
                <div className='valueContainer'>
                    <b>Position Diagram URL</b>
                    <input value={positionDiagramUrl} disabled={!isEditing} onChange={handlePositionDiagramUrlChange}></input>
                    <br></br>
                    <b>Number of parts</b>
                    <input value={numberOfParts} disabled={!isEditing} onChange={handleNumberOfPartsChange}></input>
                    {!isNumberValid && <p style={{color: 'red'}}>Please enter a valid number</p>}
                </div>
                <div className='imageContainer'>
                    <img alt="image" src={require("../../pages/resources/CompanyLogo.png")}
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

export default AssetSparePart;