import logo from './logo.svg';
import './App.css';
//import Login from './login/login'
import Table from './components/asset/AssetTable';
import Asset from './components/asset/Asset';
import Home from './pages/Home';
import Login from './pages/login';
import Company from './components/company/Company';
import CompanyCreate from './components/company/CompanyCreate';
import CompanyTable from './components/company/CompanyTable';
import CompanyUserTable from './components/company/CompanyUserTable';
import CompanyUserCreate from './components/company/CompanyUserCreate'
import CompanySiteTable from './components/company/CompanySiteTable';
import CompanySiteCreate from './components/company/CompanySiteCreate';
import SiteTable from './components/site/SiteTable';
import CustomerTable from './components/user/UserTable';
import Customer from './components/user/User';
import UserSiteTable from './components/user/UserSiteTable';
import Site from './components/site/Site';
import Welcome from './components/Welcome';
import AssetCreate from './components/asset/AssetCreate';
import SiteAssetTable from './components/site/SiteAssetTable';
import SiteAssetAddTable from './components/site/SiteAssetAddTable';
import SiteAssetAdd from './components/site/SiteAssetAdd'
import SiteAsset from './components/site/SiteAsset';
import SiteUserTable from './components/site/SiteUserTable';
import SiteUserAddTable from './components/site/SiteUserAddTable';
import AccessDenied from './components/AccessDenied';
import NotFound from './components/NotFound';
import { BrowserRouter as Router, Routes, Route, BrowserRouter } from 'react-router-dom';
import ServiceTable from "./components/asset/AssetServiceTable";
import Service from "./components/asset/AssetServiceCreate";
import AssetServiceCreate from "./components/asset/AssetServiceCreate";
import AssetService from "./components/asset/AssetService";
import ServiceCompletedTable from "./components/customerSupport/ServiceCompletedTable";
import ServiceCompleted from "./components/customerSupport/ServiceCompleted";
import AssetSparePartTable from "./components/asset/AssetSparePartTable";
import AssetSparePart from "./components/asset/AssetSparePart";
import AssetSparePartCreate from "./components/asset/AssetSparePartCreate";


function App() {
  return (
      <Router>
        <Routes>
          <Route path="/asset" element={<Home><Table /></Home>} />
          <Route path="/asset/create" element={<Home><AssetCreate /></Home>} />
          <Route path="/asset/:id" element={<Home><Asset /></Home>} />
          <Route path="/asset/:id/sparepart" element={<Home><AssetSparePartTable /></Home>} />
          <Route path="/asset/:id/sparepart/:sparePartID" element={<Home><AssetSparePart /></Home>} />
          <Route path="/asset/:id/sparepart/create" element={<Home><AssetSparePartCreate /></Home>} />
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
          <Route path="/company/:companyID/site/:siteID/assets/:assetID/history/:serviceCompletedID" element={<Home><ServiceCompleted /></Home>} />
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
          <Route path="/support/:serviceCompletedID" element={<Home><ServiceCompleted  /></Home>} />
          <Route path="/support/:serviceCompletedID/assign/:companyID" element={<Home><SiteUserAddTable assign={true} /></Home>} />

          <Route path="/403" element={<Home><AccessDenied /></Home>} />
          <Route path="/404" element={<Home><NotFound /></Home>} />
        </Routes>
      </Router>
  );
}

export default App;