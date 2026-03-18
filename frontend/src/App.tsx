import { Route, Routes } from "react-router-dom";
import Layout from "./components/Layout.tsx";
import ProtectedRoute from "./components/ProtectedRoute.tsx";
import LoginPage from "./pages/LoginPage.tsx";
import RegistrationPage from "./pages/RegistrationPage.tsx";
import SolarWatchPage from "./pages/SolarWatchPage.tsx";

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout/>}>
        <Route path="/login" element={<LoginPage/>}/>
        <Route path="/register" element={<RegistrationPage/>}/>
        <Route path="/solar-watch" element={<ProtectedRoute><SolarWatchPage/></ProtectedRoute>}/>
      </Route>
    </Routes>
  )
}
