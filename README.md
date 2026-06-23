<div align="center">
  <img src="https://img.icons8.com/color/120/000000/books.png" alt="NexusLib Logo" width="120" />
  
  # 📚 NexusLib - Next-Gen Library Intelligence
  
  **A beautiful, full-stack Library Management System built with React, Spring Boot, and Tailwind CSS.**
  
  [![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](#)
  [![Vite](https://img.shields.io/badge/Vite-B73BFE?style=for-the-badge&logo=vite&logoColor=FFD62E)](#)
  [![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](#)
  [![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)](#)
  [![Deployed on Vercel](https://img.shields.io/badge/Deployed%20on-Vercel-000000?style=for-the-badge&logo=vercel&logoColor=white)](#)
  [![Deployed on Render](https://img.shields.io/badge/Deployed%20on-Render-46E3B7?style=for-the-badge&logo=render&logoColor=white)](#)
</div>

<br />

## ✨ Features

- 📖 **Vast Digital Catalog**: Instantly browse through hundreds of books sorted across 20+ distinct categories ranging from Fiction and AI to Computer Science.
- 🎨 **Breathtaking UI/UX**: Designed meticulously with glassmorphism, fluid micro-animations, and dynamic gradient backgrounds utilizing Tailwind CSS.
- 🛒 **Dynamic Checkout System**: Seamless option to **Rent (1 day for ₹1)** or **Purchase (₹100)** with interactive UPI QR Code scanner integration.
- 🔐 **Secure Role-Based Authentication**: Custom authentication system with specialized features and dashboards for **Students**, **Teachers**, and **Admins**.
- 📊 **Administrative Control Panel**: Secure backend interface for Admins to view RAW Database records, oversee transactions, and broadcast platform-wide notices.
- ⚡ **Zero-Config Cloud Database**: Powered by an embedded, auto-seeding H2 Database connected via Hibernate/JPA.

## 🛠️ Technology Stack

### Frontend (Client)
- **Framework**: React 18 + Vite
- **Styling**: Tailwind CSS
- **Routing**: React Router DOM
- **Icons**: Lucide React
- **HTTP Client**: Axios

### Backend (Server)
- **Framework**: Java 21 + Spring Boot 3
- **Data Access**: Spring Data JPA / Hibernate
- **Database**: H2 In-Memory Database (Zero-config deployment)
- **Security**: Spring Security + CORS

## 🚀 Live Demo

The platform is fully deployed and accessible over the internet!

- **Frontend**: [NexusLib on Vercel](https://nexus-lib-nstm.vercel.app)
- **Backend API**: [Spring Boot on Render](https://nexus-lib-1.onrender.com)

> *Note: Since the backend is hosted on Render's free tier, the database may take ~50 seconds to "wake up" upon your first visit. All data automatically resets via seeders upon boot.*

## 💻 Local Development

Want to run this locally on your own machine? It takes just 2 minutes.

### 1. Start the Backend
The backend utilizes an auto-seeding H2 database, so no external database installation is required!
```bash
cd backend
./mvnw clean spring-boot:run
```
*The backend will boot up on `http://localhost:8080`.*

### 2. Start the Frontend
In a new terminal window:
```bash
cd frontend
npm install
npm run dev
```
*The frontend will boot up on `http://localhost:5173`.*

## 📜 Database Overview (Auto-Seeded)
The platform automatically injects 200 high-quality books (with matching real-world cover photos) into the system upon every boot.

Transactions are strictly recorded and mapped, tracking:
- `Transaction Type`: RENT or PURCHASE
- `Amount`: Dynamic based on selection
- `Contact Info`: Phone & Email mapping

## 🤝 Contribution
Feel free to fork this repository, submit Pull Requests, and open Issues if you find any bugs or want to request a feature!

---
<div align="center">
  <i>Built with ❤️ for Modern Web Architecture.</i>
</div>
