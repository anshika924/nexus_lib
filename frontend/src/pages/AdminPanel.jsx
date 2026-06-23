import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Shield, Send, Trash2, BellRing } from 'lucide-react';

export default function AdminPanel() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [notices, setNotices] = useState([]);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    if (!user || (user.role !== 'ADMIN' && user.role !== 'TEACHER')) {
      navigate('/dashboard');
      return;
    }
    fetchNotices();
    fetchTransactions();
  }, [user, navigate]);

  const fetchNotices = async () => {
    try {
      const res = await axios.get('https://nexus-lib-1.onrender.com/api/notices');
      setNotices(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const fetchTransactions = async () => {
    try {
      const res = await axios.get('https://nexus-lib-1.onrender.com/api/transactions');
      setTransactions(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handlePostNotice = async (e) => {
    e.preventDefault();
    try {
      await axios.post('https://nexus-lib-1.onrender.com/api/notices', {
        title,
        content,
        author: user.name
      });
      setTitle('');
      setContent('');
      fetchNotices();
    } catch (err) {
      alert('Failed to post notice.');
    }
  };

  const handleDeleteNotice = async (id) => {
    try {
      await axios.delete(`https://nexus-lib-1.onrender.com/api/notices/${id}`);
      fetchNotices();
    } catch (err) {
      alert('Failed to delete notice.');
    }
  };

  if (!user) return null;

  return (
    <div className="pt-24 pb-16 min-h-screen bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="bg-white rounded-3xl p-8 shadow-sm border border-gray-100 mb-8 flex items-center gap-3">
          <Shield className="w-8 h-8 text-indigo-600" />
          <div>
            <h1 className="text-3xl font-extrabold text-gray-900">Management Panel</h1>
            <p className="text-gray-500">Manage library notices and operations</p>
          </div>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8 mb-12">
          {/* Post Notice Form */}
          <div className="bg-white rounded-3xl p-6 shadow-sm border border-gray-100">
            <h2 className="text-xl font-bold text-gray-900 mb-6 flex items-center gap-2">
              <BellRing className="w-5 h-5 text-amber-500" /> Post a Notice
            </h2>
            <form onSubmit={handlePostNotice} className="space-y-4">
              <div>
                <label className="block text-sm font-semibold text-gray-700 mb-1">Title</label>
                <input 
                  type="text" required value={title} onChange={(e) => setTitle(e.target.value)}
                  className="w-full bg-gray-50 border border-gray-200 rounded-xl px-4 py-2 outline-none focus:border-indigo-500"
                />
              </div>
              <div>
                <label className="block text-sm font-semibold text-gray-700 mb-1">Message</label>
                <textarea 
                  required value={content} onChange={(e) => setContent(e.target.value)} rows="4"
                  className="w-full bg-gray-50 border border-gray-200 rounded-xl px-4 py-2 outline-none focus:border-indigo-500 resize-none"
                />
              </div>
              <button type="submit" className="w-full bg-indigo-600 text-white font-bold py-3 rounded-xl flex items-center justify-center gap-2 hover:bg-indigo-700 transition-colors">
                <Send className="w-4 h-4" /> Broadcast Notice
              </button>
            </form>
          </div>

          {/* Existing Notices */}
          <div className="lg:col-span-2 space-y-4">
            <h2 className="text-xl font-bold text-gray-900 mb-4">Active Notices</h2>
            {notices.length === 0 && <p className="text-gray-500">No notices posted.</p>}
            {notices.map(notice => (
              <div key={notice.id} className="bg-white rounded-2xl p-6 shadow-sm border border-gray-100 flex justify-between gap-4">
                <div>
                  <h3 className="font-bold text-lg text-gray-900">{notice.title}</h3>
                  <p className="text-sm text-gray-500 mb-3">Posted by {notice.author} on {new Date(notice.createdAt).toLocaleDateString()}</p>
                  <p className="text-gray-700">{notice.content}</p>
                </div>
                <button onClick={() => handleDeleteNotice(notice.id)} className="text-gray-400 hover:text-red-600 transition-colors self-start shrink-0">
                  <Trash2 className="w-5 h-5" />
                </button>
              </div>
            ))}
          </div>
        </div>

        {/* Database Records Table */}
        <div className="bg-white rounded-3xl p-8 shadow-sm border border-gray-100">
          <h2 className="text-2xl font-bold text-gray-900 mb-6 border-b pb-4">Raw Database Records (Transactions)</h2>
          <div className="overflow-x-auto">
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="bg-gray-50 border-b border-gray-200 text-sm uppercase text-gray-500 font-bold tracking-wide">
                  <th className="p-4 rounded-tl-xl">ID</th>
                  <th className="p-4">User</th>
                  <th className="p-4">Book</th>
                  <th className="p-4">Type</th>
                  <th className="p-4">Amount</th>
                  <th className="p-4">Contact (Phone/Email)</th>
                  <th className="p-4 rounded-tr-xl">Date</th>
                </tr>
              </thead>
              <tbody className="text-sm">
                {transactions.length === 0 && (
                  <tr><td colSpan="7" className="p-8 text-center text-gray-500">No transactions recorded yet.</td></tr>
                )}
                {transactions.map(t => (
                  <tr key={t.id} className="border-b border-gray-100 hover:bg-gray-50 transition-colors">
                    <td className="p-4 font-mono text-gray-500">#{t.id}</td>
                    <td className="p-4 font-semibold text-gray-900">{t.user?.name}</td>
                    <td className="p-4 text-indigo-600 font-medium">{t.book?.title}</td>
                    <td className="p-4">
                      <span className={`px-2 py-1 rounded text-xs font-bold ${t.transactionType === 'PURCHASE' ? 'bg-amber-100 text-amber-800' : 'bg-blue-100 text-blue-800'}`}>
                        {t.transactionType || 'RENT'}
                      </span>
                    </td>
                    <td className="p-4 font-mono font-medium">₹{t.amount?.toFixed(2) || '1.00'}</td>
                    <td className="p-4 text-gray-600">
                      <div>{t.phone || '-'}</div>
                      <div className="text-xs text-gray-400">{t.email || '-'}</div>
                    </td>
                    <td className="p-4 text-gray-500 whitespace-nowrap">{new Date(t.issueDate).toLocaleString()}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}
