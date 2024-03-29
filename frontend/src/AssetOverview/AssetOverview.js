import React from 'react'

import { Helmet } from 'react-helmet'

import './AssetOverview.css'

const AssetOverview = (props) => {
  return (
    <div className="asset-overview-container">
      <Helmet>
        <title>MNGSYS</title>
        <meta property="og:title" content="MNGSYS" />
      </Helmet>
      <div className="asset-overview-navbar navbar">
        <div className="asset-overview-container1">
          <img
            alt="image"
            src="/Icon/assetmanagementsystem-200h.png"
            className="asset-overview-image"
          />
          <span className="asset-overview-text">Asset management database</span>
        </div>
        <div className="asset-overview-container2">
          <div className="asset-overview-container3 bookmark">
            <span className="asset-overview-text001">
              <span>Asset</span>
              <br></br>
            </span>
            <img
              alt="image"
              src="/Icon/close%20button-200h.png"
              className="asset-overview-image1"
            />
          </div>
          <div className="asset-overview-container4 bookmark">
            <span className="asset-overview-text004">
              <span>Customer</span>
              <br></br>
            </span>
            <img
              alt="image"
              src="/Icon/close%20button-200h.png"
              className="asset-overview-image2"
            />
          </div>
          <div className="asset-overview-container5 bookmark">
            <span className="asset-overview-text007">
              <span>Metabo komp...</span>
              <br></br>
            </span>
            <img
              alt="image"
              src="/Icon/close%20button-200h.png"
              className="asset-overview-image3"
            />
          </div>
        </div>
        <div className="asset-overview-container6">
          <img
            alt="image"
            src="/Icon/bellsimple-200h.png"
            className="asset-overview-image4"
          />
          <img
            alt="image"
            src="/Icon/frame%20624723-200h.png"
            className="asset-overview-image5"
          />
        </div>
      </div>
      <div className="asset-overview-main main">
        <div className="asset-overview-container7">
          <div className="asset-overview-left-container leftContainer">
            <span className="asset-overview-text010">
              <span>Overview</span>
              <br></br>
            </span>
            <button
              type="button"
              className="asset-overview-button button navbutton"
            >
              Asset
            </button>
            <button
              type="button"
              className="asset-overview-button1 button navbutton"
            >
              Customer
            </button>
            <button
              type="button"
              className="asset-overview-button2 button navbutton"
            >
              Customer support
            </button>
          </div>
          <div className="asset-overview-right-container rightContainer">
            <h1 className="asset-overview-text013">
              <span>Asset Overview</span>
              <br></br>
            </h1>
            <input
              type="text"
              placeholder="Search"
              className="asset-overview-textinput input"
            />
            <ul className="asset-overview-ul list">
              <li className="asset-overview-li">
                <span className="asset-overview-text016 listText">Name</span>
                <span className="asset-overview-text017 listText">
                  <span>ID</span>
                  <br></br>
                </span>
                <span className="asset-overview-text020 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li01 list-item">
                <span className="asset-overview-text023 listText">
                  Metabo kompressor Basic 250-24 W
                </span>
                <span className="asset-overview-text024 listText">
                  73243913
                </span>
                <span className="asset-overview-text025 listText">
                  <span>Aktiv</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li02 list-item">
                <span className="asset-overview-text028 listText">
                  12V FLO-2202 vannpumpe 3,8L 2,4bar
                </span>
                <span className="asset-overview-text029 listText">
                  52636900
                </span>
                <span className="asset-overview-text030 listText">
                  <span>Aktiv</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li03 list-item">
                <span className="asset-overview-text033 listText">
                  OceanTech Marine GPS System
                </span>
                <span className="asset-overview-text034 listText">
                  86311684
                </span>
                <span className="asset-overview-text035 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li04 list-item">
                <span className="asset-overview-text038 listText">
                  Seafarer Boat Fenders Set
                </span>
                <span className="asset-overview-text039 listText">
                  62023052
                </span>
                <span className="asset-overview-text040 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li05 list-item">
                <span className="asset-overview-text043 listText">
                  AquaGlow Waterproof LED Navigation Lights
                </span>
                <span className="asset-overview-text044 listText">
                  38467754
                </span>
                <span className="asset-overview-text045 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li06 list-item">
                <span className="asset-overview-text048 listText">
                  AnchorMaster Anchor Winch
                </span>
                <span className="asset-overview-text049 listText">
                  40331490
                </span>
                <span className="asset-overview-text050 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li07 list-item">
                <span className="asset-overview-text053 listText">
                  Horizon Marine Binoculars
                </span>
                <span className="asset-overview-text054 listText">
                  89295350
                </span>
                <span className="asset-overview-text055 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li08 list-item">
                <span className="asset-overview-text058 listText">
                  SeaCare Boat Maintenance Kit
                </span>
                <span className="asset-overview-text059 listText">
                  06181151
                </span>
                <span className="asset-overview-text060 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li09 list-item">
                <span className="asset-overview-text063 listText">
                  WaveLink Marine VHF Radio
                </span>
                <span className="asset-overview-text064 listText">
                  95512141
                </span>
                <span className="asset-overview-text065 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li10 list-item">
                <span className="asset-overview-text068 listText">
                  SafeHarbor Life Jackets Set
                </span>
                <span className="asset-overview-text069 listText">
                  17196248
                </span>
                <span className="asset-overview-text070 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li11 list-item">
                <span className="asset-overview-text073 listText">
                  AquaReach Boat Hook
                </span>
                <span className="asset-overview-text074 listText">
                  58877054
                </span>
                <span className="asset-overview-text075 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li12 list-item">
                <span className="asset-overview-text078 listText">
                  SOS Emergency Flares Kit
                </span>
                <span className="asset-overview-text079 listText">
                  18949419
                </span>
                <span className="asset-overview-text080 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li13 list-item">
                <span className="asset-overview-text083 listText">
                  SeaVenture Marine First Aid Kit
                </span>
                <span className="asset-overview-text084 listText">
                  77841083
                </span>
                <span className="asset-overview-text085 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li14 list-item">
                <span className="asset-overview-text088 listText">
                  AquaTech Bilge Pump
                </span>
                <span className="asset-overview-text089 listText">
                  01119636
                </span>
                <span className="asset-overview-text090 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li15 list-item">
                <span className="asset-overview-text093 listText">
                  CoastalCruise Boat Cover
                </span>
                <span className="asset-overview-text094 listText">
                  34693236
                </span>
                <span className="asset-overview-text095 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
              <li className="asset-overview-li16 list-item">
                <span className="asset-overview-text098 listText">
                  HarborMaster Dock Lines Set
                </span>
                <span className="asset-overview-text099 listText">
                  83487553
                </span>
                <span className="asset-overview-text100 listText">
                  <span>Status</span>
                  <br></br>
                </span>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div className="asset-overview-container8 bottomContainer">
        <span className="asset-overview-text103">
          This is a product created for a bachelors at NTNU Ålesund
        </span>
      </div>
    </div>
  )
}

export default AssetOverview
