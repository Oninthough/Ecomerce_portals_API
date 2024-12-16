import React from 'react';
import './SignUpPage.css'; // Ensure the CSS path is correct based on your structure

const SignUpPage: React.FC = () => {
  return (
    <div className="signup-container">
      <div className="login-box">
        <h1>Welcome to TransUnion</h1>
        <p>Data-driven decisions. Better business outcomes.</p>
        <form>
          <input type="text" placeholder="Email Address / Username" required />
          <button type="submit">Sign In</button>
        </form>
        <p className="small-print">©2024 TransUnion LLC. All rights reserved | Privacy | Personal Information We Collect</p>
      </div>
    </div>
  );
};

export default SignUpPage;



.signup-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background: #f0f0f0;
}

.login-box {
  background: white;
  padding: 40px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 300px;
  text-align: center;
}

.login-box h1, .login-box p {
  margin-bottom: 20px;
}

.login-box input {
  width: 100%;
  padding: 10px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.login-box button {
  width: 100%;
  padding: 10px;
  background: #0078d4;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.small-print {
  font-size: 10px;
  color: #666;
}
