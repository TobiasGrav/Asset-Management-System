import logo from './logo.svg';
import './App.css';
//import Login from './login/login'
import Table from './components/table';
import Asset from './components/Asset';
import Home from './Pages/Home'
import Login from './Pages/login'
import { BrowserRouter as Router, Routes, Route, BrowserRouter } from 'react-router-dom';


function App() {
  return (
      <Router>
        <Routes>
          <Route path="/asset" element={<Home />} />
          <Route path="/asset/:id" element={<Asset />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </Router>
  );
}

export default App;
