import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AppBar from "./components/Appbar";
import Student from "./components/Student";
import Course from "./components/Course";
import ChatRoom from "./components/ChatRoom";
import './index.css';
import './App.css';

function App() {
    return (
      <Router>
        <div className="App">
          <AppBar />
          <Routes>
            <Route path="/" element={<Student />} />
            <Route path="/courses" element={<Course />} />
            <Route path="/chat" element={<ChatRoom />} />
          </Routes>
        </div>
      </Router>
    );
  }
  

export default App;