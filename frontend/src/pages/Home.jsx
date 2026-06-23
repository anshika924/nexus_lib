import React, { useState, useEffect, useRef } from 'react';
import { Search, Sparkles, TrendingUp, BookText, X } from 'lucide-react';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

export default function Home() {
  const [books, setBooks] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [suggestions, setSuggestions] = useState([]);
  const [showSuggestions, setShowSuggestions] = useState(false);
  const [activeCategory, setActiveCategory] = useState('All Categories');

  const categories = [
    'All Categories', 'Fiction', 'Mystery', 'Thriller', 'Science Fiction', 'Fantasy', 
    'Romance', 'Historical Fiction', 'Biography', 'Autobiography', 'Self-Help', 
    'Motivation', 'Business', 'Entrepreneurship', 'Finance', 'Marketing', 'Leadership', 
    'Programming', 'Computer Science', 'DBMS', 'Web Development', 'AI', 'Machine Learning', 
    'Deep Learning', 'Data Science', 'Cybersecurity', 'Mathematics', 'Physics', 'Chemistry', 
    'Biology', 'History', 'Geography', 'Philosophy', 'Psychology', 'Travel', 'General Knowledge'
  ];
  
  // Modal State
  const [selectedBook, setSelectedBook] = useState(null);
  const [borrowReason, setBorrowReason] = useState('Academic Research');
  const [isBorrowing, setIsBorrowing] = useState(false);
  
  const [isPaymentMode, setIsPaymentMode] = useState(false);
  const [userPhone, setUserPhone] = useState('');
  const [userEmail, setUserEmail] = useState('');
  const [purchaseType, setPurchaseType] = useState('rent');

  const { user } = useAuth();
  const navigate = useNavigate();
  const searchRef = useRef(null);

  useEffect(() => {
    fetchBooks('', activeCategory);
    
    // Close suggestions if clicked outside
    const handleClickOutside = (event) => {
      if (searchRef.current && !searchRef.current.contains(event.target)) {
        setShowSuggestions(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const fetchBooks = async (query, categoryParam) => {
    try {
      let url = `https://nexus-lib-1.onrender.com/api/books?`;
      if (query) url += `search=${encodeURIComponent(query)}&`;
      if (categoryParam && categoryParam !== 'All Categories') url += `category=${encodeURIComponent(categoryParam)}`;
      
      const response = await axios.get(url);
      setBooks(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  // Handle Search Input Change
  const handleSearchChange = async (e) => {
    const value = e.target.value;
    setSearchQuery(value);
    
    if (value.length > 1) {
      try {
        const response = await axios.get(`https://nexus-lib-1.onrender.com/api/books?search=${encodeURIComponent(value)}`);
        setSuggestions(response.data.slice(0, 10)); // Show up to 10 suggestions
        setShowSuggestions(true);
      } catch (err) {
        console.error(err);
      }
    } else {
      setSuggestions([]);
      setShowSuggestions(false);
    }
  };

  const executeSearch = (query) => {
    setSearchQuery(query);
    setShowSuggestions(false);
    fetchBooks(query, activeCategory);
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    executeSearch(searchQuery);
  };

  const handleBorrowClick = (book) => {
    if (!user) {
      navigate('/login');
      return;
    }
    setSelectedBook(book);
  };

  const confirmBorrow = async () => {
    setIsBorrowing(true);
    try {
      const amount = purchaseType === 'rent' ? 1.00 : 100.00;
      const finalEmail = userEmail || (user?.email || '');
      await axios.post(`https://nexus-lib-1.onrender.com/api/transactions/issue?bookId=${selectedBook.id}&userId=${user.id}&borrowReason=${encodeURIComponent(borrowReason)}&phone=${encodeURIComponent(userPhone)}&email=${encodeURIComponent(finalEmail)}&transactionType=${purchaseType.toUpperCase()}&amount=${amount}`);
      alert("Successfully processed transaction!");
      setIsBorrowing(false);
      setSelectedBook(null);
      setIsPaymentMode(false);
      fetchBooks(searchQuery, activeCategory); // refresh availability
    } catch (err) {
      alert(err.response?.data || "Failed to borrow book");
    } finally {
      setIsBorrowing(false);
    }
  };

  return (
    <div className="pt-20 pb-16 min-h-screen bg-gray-50">
      
      {/* Borrow Modal */}
      {selectedBook && (
        <div className="fixed inset-0 z-[100] flex items-center justify-center p-4 bg-gray-900/60 backdrop-blur-sm">
          <div className="bg-white rounded-3xl p-8 max-w-md w-full shadow-2xl relative animate-in fade-in zoom-in duration-200">
            <button onClick={() => { setSelectedBook(null); setIsPaymentMode(false); }} className="absolute top-6 right-6 text-gray-400 hover:text-gray-600">
              <X className="w-6 h-6" />
            </button>
            
            {!isPaymentMode ? (
              <>
                <h2 className="text-2xl font-bold text-gray-900 mb-2">Checkout Details</h2>
                <p className="text-gray-500 mb-6">Review the pricing and enter your details.</p>
                
                <div className="bg-blue-50 border border-blue-100 p-4 rounded-xl mb-6 flex gap-4 items-start">
                  {selectedBook.coverUrl ? (
                    <img src={selectedBook.coverUrl} onError={(e) => { e.target.onerror = null; e.target.src="https://images.unsplash.com/photo-1543002588-bfa74002ed7e?q=80&w=400&auto=format&fit=crop"; }} className="w-16 h-24 object-cover rounded shadow-sm" alt="cover"/>
                  ) : (
                    <div className="w-16 h-24 bg-blue-100 rounded flex items-center justify-center"><BookText className="w-6 h-6 text-blue-300"/></div>
                  )}
                  <div className="flex-1">
                    <p className="font-bold text-gray-900 line-clamp-1">{selectedBook.title}</p>
                    <p className="text-sm text-gray-600 mb-2">by {selectedBook.author}</p>
                    
                    <div className="space-y-2 mt-3">
                      <div 
                        onClick={() => setPurchaseType('rent')}
                        className={`cursor-pointer flex justify-between items-center p-3 rounded-xl border-2 transition-all ${purchaseType === 'rent' ? 'border-blue-600 bg-blue-50/50 shadow-sm' : 'border-gray-100 hover:border-blue-200 bg-white'}`}
                      >
                        <div className="flex items-center gap-3">
                          <div className={`w-4 h-4 rounded-full border-2 flex items-center justify-center ${purchaseType === 'rent' ? 'border-blue-600' : 'border-gray-300'}`}>
                            {purchaseType === 'rent' && <div className="w-2 h-2 rounded-full bg-blue-600" />}
                          </div>
                          <span className={`font-medium ${purchaseType === 'rent' ? 'text-blue-900' : 'text-gray-600'}`}>Rent (1 day)</span>
                        </div>
                        <span className="font-bold text-gray-900">₹1</span>
                      </div>
                      
                      <div 
                        onClick={() => setPurchaseType('purchase')}
                        className={`cursor-pointer flex justify-between items-center p-3 rounded-xl border-2 transition-all ${purchaseType === 'purchase' ? 'border-blue-600 bg-blue-50/50 shadow-sm' : 'border-gray-100 hover:border-blue-200 bg-white'}`}
                      >
                        <div className="flex items-center gap-3">
                          <div className={`w-4 h-4 rounded-full border-2 flex items-center justify-center ${purchaseType === 'purchase' ? 'border-blue-600' : 'border-gray-300'}`}>
                            {purchaseType === 'purchase' && <div className="w-2 h-2 rounded-full bg-blue-600" />}
                          </div>
                          <span className={`font-medium ${purchaseType === 'purchase' ? 'text-blue-900' : 'text-gray-600'}`}>Purchase</span>
                        </div>
                        <span className="font-bold text-gray-900">₹100</span>
                      </div>
                    </div>
                  </div>
                </div>

                <div className="space-y-4 mb-6">
                  <div>
                    <label className="block text-sm font-semibold text-gray-700 mb-1">Phone Number</label>
                    <input 
                      type="tel" 
                      value={userPhone}
                      onChange={(e) => setUserPhone(e.target.value)}
                      placeholder="+91 98765 43210"
                      className="w-full bg-gray-50 border border-gray-200 rounded-xl px-4 py-3 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200 transition-all" 
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-semibold text-gray-700 mb-1">Email Address</label>
                    <input 
                      type="email" 
                      value={userEmail || (user?.email || '')}
                      onChange={(e) => setUserEmail(e.target.value)}
                      placeholder="you@example.com"
                      className="w-full bg-gray-50 border border-gray-200 rounded-xl px-4 py-3 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-200 transition-all" 
                    />
                  </div>
                </div>

                <button 
                  onClick={() => setIsPaymentMode(true)}
                  className="w-full bg-gray-900 text-white font-bold py-4 rounded-xl hover:bg-blue-600 transition-colors shadow-md flex justify-center items-center gap-2"
                >
                  Pay ₹{purchaseType === 'rent' ? '1' : '100'} to {purchaseType === 'rent' ? 'Rent' : 'Purchase'}
                </button>
              </>
            ) : (
              <div className="text-center py-4 animate-in fade-in slide-in-from-right-4 duration-300">
                <h2 className="text-2xl font-bold text-gray-900 mb-2">Scan to Pay</h2>
                <p className="text-gray-500 mb-8">Scan this QR code with any UPI app.</p>
                
                <div className="bg-gray-50 border border-gray-200 p-6 rounded-3xl inline-block mb-8 shadow-inner">
                  <img src={`https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=upi://pay?pa=library@upi&pn=NexusLib&am=${purchaseType === 'rent' ? '1.00' : '100.00'}&cu=INR&tn=${purchaseType === 'rent' ? 'Rent' : 'Purchase'}%20${encodeURIComponent(selectedBook.title)}`} alt="Payment QR Code" className="w-48 h-48 rounded-lg mx-auto" />
                </div>
                
                <div className="flex gap-3">
                  <button 
                    onClick={() => setIsPaymentMode(false)}
                    className="flex-1 bg-white border border-gray-200 text-gray-700 font-bold py-4 rounded-xl hover:bg-gray-50 transition-colors"
                  >
                    Back
                  </button>
                  <button 
                    onClick={confirmBorrow}
                    disabled={isBorrowing}
                    className="flex-[2] bg-green-600 text-white font-bold py-4 rounded-xl hover:bg-green-700 transition-colors shadow-md disabled:opacity-50 flex items-center justify-center gap-2"
                  >
                    {isBorrowing ? 'Processing...' : 'Payment Completed'}
                  </button>
                </div>
              </div>
            )}
          </div>
        </div>
      )}

      {/* Hero Section */}
      <div className="relative overflow-hidden bg-white">
        <div className="absolute inset-0 bg-gradient-to-br from-blue-50 to-indigo-50/50" />
        <div className="absolute inset-y-0 right-0 w-1/2 bg-gradient-to-l from-blue-100/40 to-transparent" />
        
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-50 pt-24 pb-32">
          <div className="max-w-3xl">
            <div className="inline-flex items-center gap-2 px-4 py-2 rounded-full bg-blue-100/80 text-blue-800 font-semibold text-sm mb-6 shadow-sm border border-blue-200">
              <Sparkles className="w-4 h-4 text-blue-600" />
              <span>Next-Generation Library Intelligence</span>
            </div>
            
            <h1 className="text-6xl font-extrabold text-gray-900 tracking-tight leading-[1.1] mb-8">
              Discover your next <br/>
              <span className="text-transparent bg-clip-text bg-gradient-to-r from-blue-600 to-indigo-600">
                great adventure.
              </span>
            </h1>
            
            <p className="text-xl text-gray-600 mb-10 leading-relaxed max-w-2xl">
              Access millions of books, audiobooks, and research papers instantly. Smart recommendations tailored just for you.
            </p>
            
            {/* Intelligent Search Bar */}
            <div className="relative max-w-2xl" ref={searchRef}>
              <form onSubmit={handleSearchSubmit} className="flex items-center bg-white p-2 rounded-2xl shadow-xl shadow-blue-900/5 border border-gray-100 relative z-20">
                <div className="pl-4 pr-2 text-gray-400">
                  <Search className="w-6 h-6" />
                </div>
                <input 
                  type="text" 
                  value={searchQuery}
                  onChange={handleSearchChange}
                  onFocus={() => { if(suggestions.length > 0) setShowSuggestions(true); }}
                  placeholder="Search by title, author, or category..." 
                  className="w-full py-4 px-2 text-lg outline-none text-gray-700 bg-transparent placeholder-gray-400"
                  autoComplete="off"
                />
                <button type="submit" className="bg-gray-900 text-white px-8 py-4 rounded-xl font-bold hover:bg-blue-600 transition-colors shadow-md">
                  Search
                </button>
              </form>

              {/* Suggestions Dropdown */}
              {showSuggestions && suggestions.length > 0 && (
                <div className="absolute top-full left-0 right-0 mt-2 bg-white rounded-2xl shadow-2xl border border-gray-100 overflow-y-auto max-h-96 z-40">
                  {suggestions.map(s => (
                    <div 
                      key={s.id} 
                      onClick={() => executeSearch(s.title)}
                      className="px-6 py-4 hover:bg-blue-50 cursor-pointer flex items-center gap-4 border-b border-gray-50 last:border-0"
                    >
                      <Search className="w-4 h-4 text-gray-300" />
                      <div>
                        <p className="font-bold text-gray-900">{s.title}</p>
                        <p className="text-sm text-gray-500">{s.author} • {s.category}</p>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        </div>
      </div>

      {/* Dynamic Catalog Section */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pt-8 relative z-50">
        
        {/* Category Filter Pills */}
        <div className="flex overflow-x-auto pb-4 mb-6 hide-scrollbar gap-3">
          {categories.map(cat => (
            <button
              key={cat}
              onClick={() => { setActiveCategory(cat); fetchBooks(searchQuery, cat); }}
              className={`whitespace-nowrap px-6 py-2.5 rounded-full font-bold text-sm transition-all shadow-sm ${activeCategory === cat ? 'bg-blue-600 text-white shadow-blue-500/30' : 'bg-white text-gray-600 border border-gray-200 hover:bg-gray-50'}`}
            >
              {cat}
            </button>
          ))}
        </div>

        <div className="flex items-center justify-between mb-8">
          <h2 className="text-2xl font-bold text-gray-900 flex items-center gap-2">
            <TrendingUp className="w-6 h-6 text-blue-600" /> Catalog Results
          </h2>
        </div>

        {books.length === 0 ? (
          <div className="text-center py-20 bg-white rounded-3xl border border-dashed border-gray-300">
            <BookText className="w-12 h-12 text-gray-300 mx-auto mb-4" />
            <p className="text-gray-500 font-medium">No books found for your search.</p>
          </div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
            {books.map((book) => (
              <div key={book.id} className="bg-white rounded-2xl p-4 shadow-[0_8px_30px_rgb(0,0,0,0.04)] border border-gray-100 hover:-translate-y-2 hover:shadow-[0_20px_40px_rgb(0,0,0,0.08)] transition-all duration-300 group cursor-pointer flex flex-col">
                <div className="relative aspect-[3/4] mb-4 overflow-hidden rounded-xl bg-gray-100 flex items-center justify-center">
                  <div className="absolute inset-0 bg-gray-900/10 group-hover:bg-transparent transition-colors z-10" />
                  {book.coverUrl ? (
                    <img src={book.coverUrl} onError={(e) => { e.target.onerror = null; e.target.src="https://images.unsplash.com/photo-1543002588-bfa74002ed7e?q=80&w=400&auto=format&fit=crop"; }} alt={book.title} className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" />
                  ) : (
                    <BookText className="w-16 h-16 text-gray-300 group-hover:text-blue-200 transition-colors" />
                  )}
                  <div className="absolute top-3 left-3 bg-white/90 backdrop-blur text-xs font-bold px-3 py-1 rounded-full z-20 shadow-sm text-gray-800">
                    {book.category}
                  </div>
                </div>
                <h3 className="font-bold text-lg text-gray-900 line-clamp-1 group-hover:text-blue-600 transition-colors mb-1">{book.title}</h3>
                <p className="text-gray-500 text-sm mb-4">{book.author}</p>
                
                <div className="flex items-center justify-between mt-auto pt-4 border-t border-gray-50">
                  <div className="text-sm">
                    <span className="font-bold text-gray-900">{book.availableCopies}</span>
                    <span className="text-gray-500"> / {book.totalCopies} left</span>
                  </div>
                  <button 
                    onClick={() => handleBorrowClick(book)}
                    disabled={book.availableCopies === 0}
                    className={`px-4 py-2 rounded-lg text-sm font-bold transition-colors ${book.availableCopies > 0 ? 'bg-blue-50 text-blue-600 hover:bg-blue-600 hover:text-white' : 'bg-gray-100 text-gray-400 cursor-not-allowed'}`}
                  >
                    {book.availableCopies > 0 ? 'Borrow' : 'Out of Stock'}
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
