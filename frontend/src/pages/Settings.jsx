import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Settings as SettingsIcon, Save, Trash2 } from 'lucide-react';

export default function Settings() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [name, setName] = useState(user?.name || '');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);

  if (!user) {
    navigate('/login');
    return null;
  }

  const handleUpdate = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await axios.put(`https://nexus-lib-1.onrender.com/api/users/${user.id}`, { name, password });
      alert("Credentials updated successfully!");
      setPassword('');
    } catch (err) {
      alert("Failed to update credentials.");
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async () => {
    if (window.confirm("Are you sure you want to permanently delete your account? This action cannot be undone.")) {
      try {
        await axios.delete(`https://nexus-lib-1.onrender.com/api/users/${user.id}`);
        alert("Account deleted.");
        logout();
      } catch (err) {
        alert("Failed to delete account.");
      }
    }
  };

  return (
    <div className="pt-24 pb-16 min-h-screen bg-gray-50 flex justify-center">
      <div className="max-w-2xl w-full px-4">
        <div className="bg-white rounded-3xl p-8 shadow-sm border border-gray-100">
          <div className="flex items-center gap-3 mb-8">
            <SettingsIcon className="w-8 h-8 text-blue-600" />
            <h1 className="text-3xl font-extrabold text-gray-900">Account Settings</h1>
          </div>

          <form onSubmit={handleUpdate} className="space-y-6 mb-10">
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">Full Name</label>
              <input 
                type="text" 
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="w-full bg-gray-50 border border-gray-200 rounded-xl px-4 py-3 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200 transition-all"
              />
            </div>
            
            <div>
              <label className="block text-sm font-semibold text-gray-700 mb-2">New Password (leave blank to keep current)</label>
              <input 
                type="password" 
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="w-full bg-gray-50 border border-gray-200 rounded-xl px-4 py-3 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200 transition-all"
                placeholder="••••••••"
              />
            </div>

            <button 
              type="submit"
              disabled={loading}
              className="w-full bg-blue-600 text-white font-bold py-4 rounded-xl flex items-center justify-center gap-2 hover:bg-blue-700 transition-colors disabled:opacity-50"
            >
              <Save className="w-5 h-5" /> Save Changes
            </button>
          </form>

          <div className="pt-8 border-t border-red-100">
            <h2 className="text-xl font-bold text-red-600 mb-2">Danger Zone</h2>
            <p className="text-gray-500 text-sm mb-4">Once you delete your account, there is no going back. Please be certain.</p>
            <button 
              onClick={handleDelete}
              className="bg-red-50 text-red-600 font-bold py-3 px-6 rounded-xl flex items-center gap-2 hover:bg-red-600 hover:text-white transition-colors border border-red-100"
            >
              <Trash2 className="w-5 h-5" /> Delete Account
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
