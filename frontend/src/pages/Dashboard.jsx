import React, { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { BookOpen, Calendar, Clock, CheckCircle, X, BellRing } from 'lucide-react';
import axios from 'axios';

export default function Dashboard() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [transactions, setTransactions] = useState([]);
  const [notices, setNotices] = useState([]);
  const [loading, setLoading] = useState(true);

  // Modal State
  const [returnTransactionId, setReturnTransactionId] = useState(null);
  const [returnCondition, setReturnCondition] = useState('Excellent');
  const [isReturning, setIsReturning] = useState(false);

  useEffect(() => {
    if (!user) {
      navigate('/login');
      return;
    }

    fetchTransactions();
    fetchNotices();
  }, [user, navigate]);

  const fetchTransactions = async () => {
    try {
      const res = await axios.get(`http://localhost:8080/api/transactions/user/${user.id}`);
      setTransactions(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const fetchNotices = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/notices');
      setNotices(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const confirmReturn = async () => {
    setIsReturning(true);
    try {
      await axios.post(`http://localhost:8080/api/transactions/return/${returnTransactionId}?returnCondition=${encodeURIComponent(returnCondition)}`);
      setReturnTransactionId(null);
      fetchTransactions(); // refresh
    } catch (err) {
      alert("Failed to return book");
    } finally {
      setIsReturning(false);
    }
  };

  if (!user) return null;

  return (
    <div className="pt-24 pb-16 min-h-screen bg-gray-50">
      
      {/* Return Modal */}
      {returnTransactionId && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-gray-900/40 backdrop-blur-sm">
          <div className="bg-white rounded-3xl p-8 max-w-md w-full shadow-2xl relative animate-in fade-in zoom-in duration-200">
            <button onClick={() => setReturnTransactionId(null)} className="absolute top-6 right-6 text-gray-400 hover:text-gray-600">
              <X className="w-6 h-6" />
            </button>
            
            <h2 className="text-2xl font-bold text-gray-900 mb-2">Return Book</h2>
            <p className="text-gray-500 mb-6">Please log the condition of the book.</p>

            <div className="mb-6">
              <label className="block text-sm font-semibold text-gray-700 mb-2">Book Condition</label>
              <select 
                value={returnCondition}
                onChange={(e) => setReturnCondition(e.target.value)}
                className="w-full bg-gray-50 border border-gray-200 rounded-xl px-4 py-3 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200 transition-all"
              >
                <option>Excellent</option>
                <option>Good (Normal Wear)</option>
                <option>Fair (Some Damage)</option>
                <option>Poor (Needs Repair)</option>
                <option>Lost/Missing</option>
              </select>
            </div>

            <button 
              onClick={confirmReturn}
              disabled={isReturning}
              className="w-full bg-blue-600 text-white font-bold py-4 rounded-xl hover:bg-blue-700 transition-colors disabled:opacity-50"
            >
              {isReturning ? 'Processing...' : 'Confirm Return'}
            </button>
          </div>
        </div>
      )}

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="bg-white rounded-3xl p-8 shadow-sm border border-gray-100 mb-8 flex items-center justify-between">
          <div>
            <h1 className="text-3xl font-extrabold text-gray-900 mb-2">Welcome back, {user.name}!</h1>
            <p className="text-gray-500">Manage your {user.role === 'TEACHER' ? 'faculty' : 'student'} borrowing history and notices.</p>
          </div>
          <div className="bg-blue-50 text-blue-700 px-6 py-3 rounded-xl font-bold flex items-center gap-2">
            <BookOpen className="w-5 h-5" /> Active Borrows: {transactions.filter(t => t.status === 'ACTIVE').length}
          </div>
        </div>

        {notices.length > 0 && (
          <div className="mb-10">
            <h2 className="text-xl font-bold text-gray-900 mb-4 flex items-center gap-2">
              <BellRing className="w-5 h-5 text-amber-500" /> Library Notices
            </h2>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {notices.map(notice => (
                <div key={notice.id} className="bg-amber-50/50 border border-amber-100 rounded-2xl p-5 shadow-sm">
                  <h3 className="font-bold text-lg text-amber-900">{notice.title}</h3>
                  <p className="text-xs text-amber-700/70 mb-2 font-semibold">By {notice.author} • {new Date(notice.createdAt).toLocaleDateString()}</p>
                  <p className="text-amber-800/90 text-sm">{notice.content}</p>
                </div>
              ))}
            </div>
          </div>
        )}

        <h2 className="text-xl font-bold text-gray-900 mb-6">Your Borrowing History</h2>

        {loading ? (
          <div className="text-center py-12 text-gray-400 font-semibold animate-pulse">Loading records...</div>
        ) : transactions.length === 0 ? (
          <div className="text-center py-20 bg-white rounded-3xl border border-dashed border-gray-300">
            <BookOpen className="w-12 h-12 text-gray-300 mx-auto mb-4" />
            <p className="text-gray-500 font-medium">You haven't borrowed any books yet.</p>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {transactions.map((t) => (
              <div key={t.id} className="bg-white rounded-2xl p-6 shadow-sm border border-gray-100 hover:shadow-md transition-shadow relative overflow-hidden flex flex-col">
                {t.status === 'ACTIVE' && (
                  <div className="absolute top-0 right-0 w-16 h-16 bg-gradient-to-bl from-blue-500 to-transparent opacity-20 rounded-bl-full" />
                )}
                
                <div className="flex gap-4 mb-4">
                  {t.book.coverUrl ? (
                    <img src={t.book.coverUrl} onError={(e) => { e.target.onerror = null; e.target.src="https://images.unsplash.com/photo-1543002588-bfa74002ed7e?q=80&w=400&auto=format&fit=crop"; }} className="w-16 h-24 object-cover rounded-md shadow-sm" alt="cover"/>
                  ) : (
                    <div className="w-16 h-24 bg-gray-100 rounded-md flex items-center justify-center"><BookOpen className="text-gray-300"/></div>
                  )}
                  <div>
                    <h3 className="font-bold text-lg text-gray-900 line-clamp-2">{t.book.title}</h3>
                    <p className="text-gray-500 text-sm mb-1">{t.book.author}</p>
                    {t.borrowReason && <span className="inline-block bg-gray-100 text-gray-600 text-xs px-2 py-1 rounded font-semibold mt-1">Reason: {t.borrowReason}</span>}
                  </div>
                </div>
                
                <div className="space-y-2 mb-6 mt-auto">
                  <div className="flex items-center gap-2 text-sm text-gray-600">
                    <Calendar className="w-4 h-4 text-blue-500" />
                    <span>Borrowed: {new Date(t.issueDate).toLocaleDateString()}</span>
                  </div>
                  <div className="flex items-center gap-2 text-sm text-gray-600">
                    <Clock className="w-4 h-4 text-amber-500" />
                    <span>Due: {new Date(t.dueDate).toLocaleDateString()}</span>
                  </div>
                  {t.status === 'RETURNED' && t.returnCondition && (
                    <div className="flex items-center gap-2 text-sm text-gray-600 mt-2">
                      <span className="w-4 h-4 rounded-full bg-emerald-100 text-emerald-600 flex items-center justify-center text-[10px] font-bold">i</span>
                      <span>Returned Condition: {t.returnCondition}</span>
                    </div>
                  )}
                </div>

                {t.status === 'ACTIVE' ? (
                  <button 
                    onClick={() => setReturnTransactionId(t.id)}
                    className="w-full bg-blue-50 text-blue-600 font-bold py-2.5 rounded-xl hover:bg-blue-600 hover:text-white transition-colors mt-auto"
                  >
                    Return Book
                  </button>
                ) : (
                  <div className="w-full bg-emerald-50 text-emerald-600 font-bold py-2.5 rounded-xl flex items-center justify-center gap-2 mt-auto">
                    <CheckCircle className="w-4 h-4" /> Returned
                  </div>
                )}
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
