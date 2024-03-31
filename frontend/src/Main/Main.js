import React from 'react'

import { Helmet } from 'react-helmet'

import './Main.css'

const Main = (props) => {
  return (
    <div className="body">
      <Helmet>
        <title>MNGSYS</title>
        <meta property="og:title" content="MNGSYS" />
      </Helmet>
      <div className="navbarContainer">
        <div className="logoContainer">
          <img
            alt="image"
            src={require('./resources/assetmanagementsystem.png')}
            className="logoImage"
          />
          <span className="logoText">Asset management database</span>
        </div>
        <div className="bookmarkContainer">
          
        </div>
        <div className="utilityContainer">
          <img
            alt="image"
            src={require('./resources/bell.png')}
            className="utilityIcon"
          />
          <img
            alt="image"
            src={require('./resources/frame.png')}
            className="utilityIcon"
          />
        </div>
      </div>
      <div className="mainContainer">
        <div className="leftContainer">
          <span className="navText">
            <span>Overview</span>
            <br></br>
          </span>
          <button
            type="button"
            className="navButton"
          >
            Asset
          </button>
          <button
            type="button"
            className="navButton"
          >
            Customer
          </button>
          <button
            type="button"
            className="navButton"
          >
            Customer support
          </button>
        </div>
        <div className="rightContainer">
        <div class="table-responsive">
	<table id="kt_datatable_zero_configuration" class="table table-row-bordered gy-5">
		<thead>
			<tr class="fw-semibold fs-6 text-muted">
				<th>Name</th>
				<th>Position</th>
				<th>Office</th>
				<th>Age</th>
				<th>Start date</th>
				<th>Salary</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>Tiger Nixon</td>
				<td>System Architect</td>
				<td>Edinburgh</td>
				<td>61</td>
				<td>2011/04/25</td>
				<td>$320,800</td>
			</tr>
			<tr>
				<td>Garrett Winters</td>
				<td>Accountant</td>
				<td>Tokyo</td>
				<td>63</td>
				<td>2011/07/25</td>
				<td>$170,750</td>
			</tr>
			<tr>
				<td>Ashton Cox</td>
				<td>Junior Technical Author</td>
				<td>San Francisco</td>
				<td>66</td>
				<td>2009/01/12</td>
				<td>$86,000</td>
			</tr>
		</tbody>
		<tfoot>
			<tr>
				<th>Name</th>
				<th>Position</th>
				<th>Office</th>
				<th>Age</th>
				<th>Start date</th>
				<th>Salary</th>
			</tr>
		</tfoot>
	</table>
</div>
        </div>
      </div>
      <div className="bottomContainer">
        <span className="asset-overview-text103">
          This is a product created for a bachelors at NTNU Ålesund
        </span>
      </div>
    </div>
  )
}

export default Main
