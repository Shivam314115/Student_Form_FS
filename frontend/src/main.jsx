import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';

// main apple typography and layout styles and other global styles
import './index.css';
import './components/StudentForm.css';
// Application Entry Point
import App from './App.jsx';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>,
);


