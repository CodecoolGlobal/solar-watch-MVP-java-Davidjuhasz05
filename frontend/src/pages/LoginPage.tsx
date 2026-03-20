import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { postFetch } from "../service/apiService.ts";
import {useAuth} from "../components/AuthContext.tsx";
import LoginForm from "../components/Loginform.tsx";
import Error from "../components/Error.tsx";

export default function LoginPage() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [error, setError] = useState<Error | null>(null);

  async function handleLogin(credentials) {
    try {
      const response = await postFetch("/api/auth/login", credentials);
      const result: string = await response.json();
      const token = result.token;
      login(token);
      navigate("/solar-watch");
    } catch (e) {
      setError(`Network error: ${e.message}`);
    }
  }

  return (
    <div>
      {error && <Error errorMessage={error} />}
      <LoginForm onLogin={handleLogin} />
    </div>
  )
};
