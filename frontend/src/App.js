import logo from './logo.svg';
import './App.css';
//import Login from './login/login'
import Table from './components/Asset/AssetTable';
import Asset from './components/Asset/Asset';
import Home from './Pages/Home';
import Login from './Pages/login';
import Company from './components/Company/Company';
import CompanyTable from './components/Company/CompanyTable';
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
import { BrowserRouter as Router, Routes, Route, BrowserRouter } from 'react-router-dom';


function App() {
  return (
      <Router>
        <Routes>
          <Route path="/asset" element={<Home><Table /></Home>} />
          <Route path="/asset/:id" element={<Home><Asset /></Home>} />
          <Route path="/asset/create" element={<Home><AssetCreate /></Home>} />
          <Route path="/company" element={<Home><CompanyTable /></Home>} />
          <Route path="/company/:id" element={<Home><Company /></Home>} />

          <Route path="/site" element={<Home><SiteTable /></Home>} />
          <Route path="/site/:id" element={<Home><Site /></Home>} />
          <Route path="/site/:siteID/assets" element={<Home><SiteAssetTable /></Home>} />
          <Route path="/site/:siteID/assets/add" element={<Home><SiteAssetAddTable /></Home>} />
          <Route path="/site/:siteID/assets/add/:assetID" element={<Home><SiteAssetAdd /></Home>} />
          <Route path="/site/:siteID/assets/:assetID" element={<Home><SiteAsset /></Home>} />
          <Route path="/site/:siteID/users" element={<Home><SiteUserTable /></Home>} />
          <Route path="/site/:siteID/users/:userID" element={<Home><SiteAssetTable /></Home>} />

          <Route path="/user" element={<Home><CustomerTable /></Home>} />
          <Route path="/user/:id" element={<Home><Customer /></Home>} />
          <Route path="/user/create" element={<Home><AssetCreate /></Home>} />
          <Route path="/user/:userID/sites" element={<Home><UserSiteTable /></Home>} />
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Home><Welcome /></Home>} />
        </Routes>
      </Router>
  );
}

export default App;