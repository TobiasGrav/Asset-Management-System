import React, { useState, useRef, useEffect } from 'react'

import { Helmet } from 'react-helmet'

import Table from './AssetTable'

import './Asset.css'
import { useParams } from 'react-router'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import { useNavigate } from 'react-router-dom'
import HTTPRequest from '../../tools/HTTPRequest'
import { jwtDecode } from 'jwt-decode'
import URL from "../../tools/URL";

const SparePartCreate = (props) => {

    // Cookie initializer for react
    const [cookies] = useCookies();
    const [positionDiagramUrl, setPositionDiagramUrl] = useState();
    const [numberOfParts, setNumberOfParts] = useState();
    const { id } = useParams();
    const [isNumberValid, setIsNumberValid] = useState(true);


    // back button functionality, goes back to the last page /asset.
    const navigate = useNavigate();
    const back = () => {
        navigate(-1);
    }

    const handleSubmit = () => {
        if (!/^\d+$/.test(numberOfParts)) {
            setIsNumberValid(false);
            return;
        }
        setIsNumberValid(true);
        const data = {
            numberOfParts: parseInt(numberOfParts, 10),
            positionDiagramUrl: positionDiagramUrl,
            asset: {
                id: id
            }
        };
        HTTPRequest.post(`${URL.BACKEND}/api/admin/spareParts`, data, cookies.JWT)
            .then(response => {
                console.log(response);
                navigate(-1);
                alert("Spare part was created successfully")
            })
            .catch(error => {
                console.error(error);
                alert('Something went wrong!\nSpare part not created')
            });
    };

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

    return (
        <div className='companyBody'>
            <div style={{textAlign:'center'}}><h1>Create Spare Part</h1></div>
            <div className='companyContainer'>
                <div className='valueContainer'>
                    <b>Position Diagram URL</b>
                    <input value={positionDiagramUrl} onChange={handlePositionDiagramUrlChange}></input>
                    <br></br>
                    <b>Number of parts</b>
                    <input value={numberOfParts} onChange={handleNumberOfPartsChange}></input>
                    {!isNumberValid && <p style={{color: 'red'}}>Please enter a valid number</p>}
                </div>
                <div className='imageContainer'>
                    <img alt="image" src={require("../../pages/resources/CompanyLogo.png")}
                         className="companyImage"></img>
                </div>
            </div>
            <div className="buttonContainer">
                <div className="rightButtonContainer">

                    {<button type="button" className="button" onClick={back} >Back</button>}

                    {<button type="button" className="button" onClick={handleSubmit} >Confirm</button>}
                </div>
            </div>
        </div>
    )
}

export default SparePartCreate;