import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';

// 1. Global styles (Colors, Typography, Resets)
import './index.css';

// 2. Component styles (Form-specific styling)
import './components/StudentForm.css';

// 3. Application Entry Point
import App from './App.jsx';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>,
);