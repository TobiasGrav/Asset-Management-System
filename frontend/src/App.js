import logo from './logo.svg';
import './App.css';
//import Login from './login/login'
import Table from './components/Asset/AssetTable';
import Asset from './components/Asset/Asset';
import Home from './Pages/Home';
import Login from './Pages/login';
import Company from './components/Company/Company';
import CompanyCreate from './components/Company/CompanyCreate';
import CompanyTable from './components/Company/CompanyTable';
import CompanyUserTable from './components/Company/CompanyUserTable';
import CompanyUserCreate from './components/Company/CompanyUserCreate'
import CompanySiteTable from './components/Company/CompanySiteTable';
import CompanySiteCreate from './components/Company/CompanySiteCreate';
import SiteTable from './components/Site/SiteTable';
import CustomerTable from './components/User/UserTable';
import Customer from './components/User/User';
import UserSiteTable from './components/User/UserSiteTable';
import Site from './components/Site/Site';
import Welcome from './components/Welcome';
import AssetCreate from './components/Asset/AssetCreate';
import SiteAssetTable from './components/Site/SiteAssetTable';
import SiteAssetAddTable from './components/Site/SiteAssetAddTable';
import SiteAssetAdd from './components/Site/SiteAssetAdd'
import SiteAsset from './components/Site/SiteAsset';
import SiteUserTable from './components/Site/SiteUserTable';
import SiteUserAddTable from './components/Site/SiteUserAddTable';
import AccessDenied from './components/AccessDenied';
import NotFound from './components/NotFound';
import { BrowserRouter as Router, Routes, Route, BrowserRouter } from 'react-router-dom';
import ServiceTable from "./components/Asset/AssetServiceTable";
import Service from "./components/Asset/AssetServiceCreate";
import AssetServiceCreate from "./components/Asset/AssetServiceCreate";
import AssetService from "./components/Asset/AssetService";
import ServiceCompletedTable from "./components/CustomerSupport/ServiceCompletedTable";
import ServiceCompleted from "./components/CustomerSupport/ServiceCompleted";


function App() {
  return (
      <Router>
        <Routes>
          <Route path="/asset" element={<Home><Table /></Home>} />
          <Route path="/asset/create" element={<Home><AssetCreate /></Home>} />
          <Route path="/asset/:id" element={<Home><Asset /></Home>} />
          <Route path="/asset/:id/service" element={<Home><ServiceTable displayAllServices={true}/></Home>} />
          <Route path="/asset/:id/service/:serviceID" element={<Home><AssetService /></Home>} />
          <Route path="/asset/:id/service/create" element={<Home><AssetServiceCreate /></Home>} />

          <Route path="/company" element={<Home><CompanyTable /></Home>} />
          <Route path="/company/:companyID" element={<Home><Company /></Home>} />
          <Route path="/company/create" element={<Home><CompanyCreate /></Home>} />
          <Route path="/company/:companyID/users" element={<Home><CompanyUserTable /></Home>} />
          <Route path="/company/:companyID/users/create" element={<Home><CompanyUserCreate /></Home>} />
          <Route path="/company/:companyID/sites" element={<Home><CompanySiteTable /></Home>} />
          <Route path="/company/:companyID/sites/create" element={<Home><CompanySiteCreate /></Home>} />

          <Route path="/site" element={<Home><SiteTable /></Home>} />
          <Route path="/company/:companyID/site/:id" element={<Home><Site /></Home>} />
          <Route path="/company/:companyID/site/:siteID/assets" element={<Home><SiteAssetTable /></Home>} />
          <Route path="/company/:companyID/site/:siteID/assets/add" element={<Home><SiteAssetAddTable /></Home>} />
          <Route path="/company/:companyID/site/:siteID/assets/add/:assetID" element={<Home><SiteAssetAdd /></Home>} />
          <Route path="/company/:companyID/site/:siteID/assets/:assetID" element={<Home><SiteAsset /></Home>} />
          <Route path="/company/:companyID/site/:siteID/assets/:assetID/history" element={<Home><ServiceCompletedTable serviceHistory={true} /></Home>} />
          <Route path="/company/:companyID/site/:siteID/assets/:assetID/history/:serviceCompletedID" element={<Home><ServiceCompleted user={true} /></Home>} />
          <Route path="/company/:companyID/site/:siteID/assets/:id/service" element={<Home><ServiceTable displayAllServices={false} /></Home>} />
          <Route path="/company/:companyID/site/:siteID/users" element={<Home><SiteUserTable /></Home>} />
          <Route path="/company/:companyID/site/:siteID/users/:userID" element={<Home><Customer /></Home>} />
          <Route path="/company/:companyID/site/:siteID/users/add" element={<Home><SiteUserAddTable assign={false}/></Home>} />

          <Route path="/user" element={<Home><CustomerTable /></Home>} />
          <Route path="/user/:userID" element={<Home><Customer /></Home>} />
          <Route path="/user/create" element={<Home><CompanyUserCreate /></Home>} />
          <Route path="/user/:userID/sites" element={<Home><UserSiteTable /></Home>} />
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Home><Welcome /></Home>} />

          <Route path="/support" element={<Home><ServiceCompletedTable serviceHistory={false}/></Home>} />
          <Route path="/support/:serviceCompletedID" element={<Home><ServiceCompleted  user={false}/></Home>} />
          <Route path="/support/:serviceCompletedID/assign/:companyID" element={<Home><SiteUserAddTable assign={true} /></Home>} />

          <Route path="/403" element={<Home><AccessDenied /></Home>} />
          <Route path="/404" element={<Home><NotFound /></Home>} />
        </Routes>
      </Router>
  );
}

export default App;