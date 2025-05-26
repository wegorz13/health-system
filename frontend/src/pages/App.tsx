import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Searcher from "../components/Searcher";
import DoctorPage from "./DoctorPage";

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Searcher />} />
                <Route path="/doctor/:id" element={<DoctorPage />} />
            </Routes>
        </Router>
    );
}
