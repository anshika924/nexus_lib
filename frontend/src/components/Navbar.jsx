import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { BookOpen, LogOut, LogIn } from 'lucide-react';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const location = useLocation();
  const { user, logout } = useAuth();
  const isActive = (path) => location.pathname === path;

  return (
    <nav className="fixed w-full z-50 top-0 transition-all duration-300 bg-white/70 backdrop-blur-lg border-b border-gray-200/50 shadow-sm">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-20">
          <Link to="/" className="flex items-center gap-3 group">
            <div className="bg-gradient-to-br from-blue-600 to-indigo-600 p-2 rounded-xl shadow-lg shadow-blue-500/30 group-hover:scale-105 transition-transform">
              <BookOpen className="text-white w-6 h-6" />
            </div>
            <span className="font-extrabold text-2xl tracking-tight bg-clip-text text-transparent bg-gradient-to-r from-gray-900 to-gray-600">
              NexusLib
            </span>
          </Link>
          
          <div className="hidden md:flex items-center space-x-8">
            <Link to="/" className={`font-semibold transition-colors ${isActive('/') ? 'text-blue-600' : 'text-gray-600 hover:text-blue-600'}`}>
              Discovery
            </Link>
            <Link to="/dashboard" className={`font-semibold transition-colors ${isActive('/dashboard') ? 'text-blue-600' : 'text-gray-600 hover:text-blue-600'}`}>
              Dashboard
            </Link>
            
            {user ? (
              <div className="flex items-center gap-4">
                {(user.role === 'ADMIN' || user.role === 'TEACHER') && (
                  <Link to="/admin" className={`font-semibold transition-colors ${isActive('/admin') ? 'text-blue-600' : 'text-gray-600 hover:text-blue-600'}`}>
                    Management
                  </Link>
                )}
                <Link to="/settings" className={`font-semibold transition-colors ${isActive('/settings') ? 'text-blue-600' : 'text-gray-600 hover:text-blue-600'}`}>
                  Settings
                </Link>
                <span className="font-bold text-gray-900 border-l border-gray-300 pl-4">Hi, {user.name}</span>
                <button onClick={logout} className="flex items-center gap-2 bg-gray-100 text-gray-700 px-6 py-2.5 rounded-full font-semibold hover:bg-red-50 hover:text-red-600 transition-all duration-200">
                  <LogOut className="w-4 h-4" /> Logout
                </button>
              </div>
            ) : (
              <Link to="/login" className="flex items-center gap-2 bg-gray-900 text-white px-6 py-2.5 rounded-full font-semibold hover:bg-gray-800 hover:shadow-lg hover:-translate-y-0.5 transition-all duration-200">
                <LogIn className="w-4 h-4" /> Sign In
              </Link>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
}
