import React, { useState, useRef, useEffect } from 'react';

import './ServuceCompleted.css';
import { useParams } from 'react-router';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';
import HTTPRequest from '../../tools/HTTPRequest';
import { jwtDecode } from 'jwt-decode';
import URL from '../../tools/URL';
import {format} from "date-fns";
import ServiceCompletedComment from "./ServiceCompletedComment";
import {getAdminStatus} from "../../tools/globals";

const ServiceCompleted = (props) => {

    // Cookie initializer for react
    const [cookies, setCookie, removeCookie] = useCookies();
    const [isEditing, setIsEditing] = useState(false);
    const [isAdmin, setIsAdmin] = useState();

    const [assetName, setAssetName] = useState();
    const [assetOnSiteTag, setAssetOnSiteTag] = useState();

    const [serviceUrl, setServiceUrl] = useState();
    const [timeCompleted, setTimeCompleted] = useState();

    const [assetOnSiteId, setAssetOnSiteId] = useState();
    const [serviceId, setServiceId] = useState();
    const [assetId, setAssetId] = useState();
    const [siteId, setSiteId] = useState();
    const [companyId, setCompanyId] = useState();

    // information variables
    const { serviceCompletedID } = useParams();

    // back button functionality, goes back to the last page /asset.
    const navigate = useNavigate();

    const back = () => {
        navigate(-1);
    }
    const showAsset = () => {
        navigate(`/company/${companyId}/site/${siteId}/assets/${assetOnSiteId}`)
    }
    const showService = () => {
        navigate(`/asset/${assetId}/service/${serviceId}`);
    }

    const assignUser = () => {
        navigate(`/asset/${assetId}/service/${serviceId}`);
    }

    const completeService = () => {
        HTTPRequest.put(`${URL.BACKEND}/api/servicesCompleted/${serviceCompletedID}`, null, cookies.JWT)
            .then(() => {
                fetchData();
            }).catch(error => {console.error("Error completing service:", error)})
    }

    const formatLocalDateTime = (localDateTime) => {
        return format(new Date(localDateTime), 'dd.MM.yyyy HH:mm');
    };

    const fetchData = () => {
        HTTPRequest.get(`${URL.BACKEND}/api/servicesCompleted/${serviceCompletedID}`, cookies.JWT)
            .then(response => {
                console.log(response);
                setAssetName(response.data.assetOnSite.asset.name);
                setAssetOnSiteTag(response.data.assetOnSite.assetOnSiteTag);
                setAssetOnSiteId(response.data.assetOnSite.id)
                setAssetId(response.data.assetOnSite.asset.id)
                setServiceId(response.data.service.id)
                setSiteId(response.data.assetOnSite.site.id)
                setCompanyId(response.data.assetOnSite.site.company.id)
                setServiceUrl(response.data.service.serviceUrl);
                setTimeCompleted(response.data.timeCompleted !== null ? formatLocalDateTime(response.data.timeCompleted) : "Service Not Completed Yet");
            }).catch(error => {console.error('Error fetching data:', error)
            }
        );
    };

    useEffect(() => {
        fetchData();
    }, []);

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
                    <input className='inputField' style={{textAlign: "center"}} value={timeCompleted} disabled={true}></input>
                    <br></br>
                    <button onClick={completeService}>Complete Service</button>

                    {getAdminStatus() && <h3>Assign User This Service</h3>}
                    {getAdminStatus() && <button onClick={assignUser}>Assign User</button>}

                    <h3>Options</h3>
                    <button onClick={showAsset}>Show Asset On Site</button>
                    <button onClick={showService}>Show Service</button>

                </div>
                <div className='imageContainer'>
                <img alt="image" src={require("../../Pages/resources/CompanyLogo.png")} className="companyImage"></img>
                </div>
            </div>
            <div style={{width: "auto", alignItems: "center"}}>
            <ServiceCompletedComment />
            </div>
            <div className="buttonContainer">
                <div className="leftButtonContainer">
                    {isEditing && <button type="button" className="button">Delete</button>}
                </div>
                <div className="rightButtonContainer">
                    <button type="button" className="button" onClick={back} >Back</button>
                </div>
            </div>
        </div>
    )
}

export default ServiceCompleted;