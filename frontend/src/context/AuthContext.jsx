import React, { createContext, useState, useContext, useEffect } from 'react';
import axios from 'axios';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Check local storage for existing session
    const storedUser = localStorage.getItem('library_user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
    setLoading(false);
  }, []);

  const login = async (email, password) => {
    try {
      const response = await axios.post(`http://localhost:8080/api/auth/login`, { email, password });
      
      if (response.status === 200) {
        setUser(response.data);
        localStorage.setItem('library_user', JSON.stringify(response.data));
        return { success: true };
      }
    } catch (err) {
      console.error(err);
      return { success: false, error: 'Invalid credentials or server error' };
    }
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('library_user');
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, loading }}>
      {!loading && children}
    </AuthContext.Provider>
  );
};
