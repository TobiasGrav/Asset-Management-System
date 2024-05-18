import React from 'react'

import { Helmet } from 'react-helmet'

import Table from '../components/asset/AssetTable'
import Asset from '../components/asset/Asset'
import { jwtDecode } from 'jwt-decode'

import URL from '../tools/URL';

import { useState, useEffect } from 'react';

import './Home.css'
import { Route, Router, Routes, useNavigate } from 'react-router'
import { useCookies } from 'react-cookie'
import HTTPRequest from '../tools/HTTPRequest'
import {getAdminStatus, getManagerStatus, getTechnicianStatus} from "../tools/globals";

const Main = ({children}) => {

  const [cookies, setCookie, deleteCookie] = useCookies();

  const navigate = useNavigate();

  const asset = () => {
    navigate("/asset/");
  };

  const company = () => {
    navigate("/company/");
  };

  const site = () => {
    navigate("/site/");
  };

  const customer = () => {
    navigate("/user/");
  };

  const customerSupport = () => {
    navigate("/support/")
  };

  const profile = () => {
    navigate('/profile');
  };

  const logout = () => {
    deleteCookie('JWT', {path: '/'});
    navigate('/login');
    location.reload();
  };

  return (
    <div className="body">
      <Helmet>
        <title>MNGSYS</title>
        <meta property="og:title" content="MNGSYS" />
      </Helmet>
      <div className="navbarContainer">
        <div className="logoContainer">
          <img alt="image" src={require('./resources/assetmanagementsystem.png')} className="logoImage"/>
          <span className="logoText">Asset Management Database</span>
        </div>
        <div className="utilityContainer">
          <img alt="image" src={require('./resources/bell.png')} className="utilityIcon"/>
          <div class='dropdown'><img alt="image" src={require('./resources/user.png')} className="utilityIcon" on/>
            <div class='profileDropdown'>
              <a class='dropdownText' onClick={profile}>My profile</a>
              <a class='dropdownText' onClick={logout}>Logout</a>
            </div>
          </div>
        </div>
      </div>
      <div className="mainContainer">
        <div className="leftContainer">
          <span className="navText">
            <span>Overview</span>
            <br></br>
          </span>
          <div className="navbarButtonContainer">
            <button type="button" className="navButton" onClick={asset}>Asset</button>
            { getAdminStatus() && <button type="button" className="navButton" onClick={company}>Company</button>}
            <button type="button" className="navButton" onClick={site}>Site</button>
            { (getAdminStatus() || getManagerStatus()) && <button type="button" className="navButton" onClick={customer}>User</button>}
            { (getAdminStatus() || getTechnicianStatus()) && <button type="button" className="navButton" onClick={customerSupport}>Customer support</button>}
          </div>
        </div>
        <div className="rightContainer">
          {children}
        </div>
      </div>
      <div className="bottomContainer">
        <span className="asset-overview-text103">This is a product created for a bachelors at NTNU Ålesund</span>
      </div>
    </div>
  )
}

export default Main