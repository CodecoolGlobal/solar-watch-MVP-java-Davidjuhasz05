import React from "react";
import { useAuth } from "./AuthContext.tsx";
import { Navigate } from "react-router-dom";

interface ProtectedRouteProps {
  children: React.ReactNode;
}

export default function ProtectedRoute({children} : ProtectedRouteProps) {
  const { isLoggedIn } = useAuth();

  if (!isLoggedIn) return <Navigate to="/login" />;

  return children;
}
