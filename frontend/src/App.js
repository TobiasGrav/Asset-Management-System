import logo from './logo.svg';
import './App.css';
//import Login from './login/login'
import Table from './components/AssetTable';
import Asset from './components/Asset';
import Home from './Pages/Home';
import Login from './Pages/login';
import Company from './components/Company';
import CompanyTable from './components/CompanyTable';
import SiteTable from './components/SiteTable';
import CustomerTable from './components/CustomerTable';
import Customer from './components/Customer';
import Site from './components/Site';
import Welcome from './components/Welcome';
import AssetCreate from './components/AssetCreate';
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
          <Route path="/customer" element={<Home><CustomerTable /></Home>} />
          <Route path="/customer/:id" element={<Home><Customer /></Home>} />
          <Route path="/customer/create" element={<Home><AssetCreate /></Home>} />
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Home><Welcome /></Home>} />
        </Routes>
      </Router>
  );
}

export default App;