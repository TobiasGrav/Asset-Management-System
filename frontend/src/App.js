import logo from './logo.svg';
import './App.css';
//import Login from './login/login'
import Table from './components/table';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';


function App() {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<Table />} />
          {/* Define other routes here */}
        </Routes>
      </Router>
  );
}

export default App;
