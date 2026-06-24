import { useState } from 'react';
import axios from 'axios';
import './StudentForm.css';

const StudentForm = () => {
    const branches = [
        "Civil Engineering", "Electrical Engineering", "Mechanical Engineering",
        "Industrial & Production Engineering", "Computer Science and Engineering",
        "Information Technology", "Electronics & Telecommunication",
        "Electronics & Instrumentation", "Biomedical Engineering"
    ];

    const [student, setStudent] = useState({
        name: '',
        enrollmentNumber: '',
        email: '',
        mobileNumber: '',
        branch: ''
    });

    const [errors, setErrors] = useState({});
    const [statusMessage, setStatusMessage] = useState({ text: '', type: '' });

    const validateField = (name, value) => {
        let error = '';
        switch (name) {
            case 'name':
                if (value.trim().length < 3) error = "Name must be at least 3 characters";
                break;
            case 'email':
                if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value)) error = "Enter a valid email address";
                break;
            case 'enrollmentNumber':
                if (value.length < 5) error = "Invalid Enrollment Number";
                break;
            case 'mobileNumber':
                if (!/^\d{10}$/.test(value)) error = "Enter a valid 10-digit number";
                break;
            case 'branch':
                if (!value) error = "Please select a branch";
                break;
            default:
                break;
        }
        return error;
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setStudent(prev => ({ ...prev, [name]: value }));
        
        // Clear status message when user starts typing again
        if (statusMessage.text) setStatusMessage({ text: '', type: '' });
        
        // Real-time validation
        const fieldError = validateField(name, value);
        setErrors(prev => ({ ...prev, [name]: fieldError }));
    };

const handleSubmit = async (e) => {
    e.preventDefault();

    let finalErrors = {};
    Object.keys(student).forEach(key => {
        const error = validateField(key, student[key]);
        if (error) finalErrors[key] = error;
    });

    if (Object.keys(finalErrors).length !== 0) {
        setErrors(finalErrors);
        return;
    }

    try {
        await axios.post('http://localhost:8080/api/students', student);

        setStatusMessage({ text: "Student Registered Successfully!", type: 'success' });
        setStudent({ name: '', enrollmentNumber: '', email: '', mobileNumber: '', branch: '' });
        setErrors({});
    } catch (err) {
        // Derive a user-friendly message from axios error
        let msg = 'An unexpected error occurred. Please try again.';
        if (err.response) {
            const data = err.response.data;
            if (typeof data === 'string') {
                msg = data;
            } else if (data && typeof data === 'object') {
                msg = data.message || Object.values(data).join(', ') || JSON.stringify(data);
            } else {
                msg = `Server responded with status ${err.response.status}`;
            }
        } else if (err.request) {
            msg = 'Unable to connect to the server. Please check if the backend is running.';
        } else {
            msg = err.message || msg;
        }

        setStatusMessage({ text: msg, type: 'error' });
    }
};

    return (
        <form className="student-form" onSubmit={handleSubmit}>
            <h2>Register Student</h2>

            {statusMessage.text && (
                <div className={`status-msg ${statusMessage.type}`}>
                    {statusMessage.text}
                </div>
            )}
            
            <div className="input-group">
                <input name="name" placeholder="Full Name" value={student.name} onChange={handleChange} required />
                {errors.name && <small className="error">{errors.name}</small>}
            </div>

            <div className="input-group">
                <input name="enrollmentNumber" placeholder="Enrollment Number" value={student.enrollmentNumber} onChange={handleChange} required />
                {errors.enrollmentNumber && <small className="error">{errors.enrollmentNumber}</small>}
            </div>

            <div className="input-group">
                <input name="email" placeholder="Email Address" value={student.email} onChange={handleChange} required />
                {errors.email && <small className="error">{errors.email}</small>}
            </div>

            <div className="input-group">
                <input name="mobileNumber" placeholder="Mobile Number" value={student.mobileNumber} onChange={handleChange} required />
                {errors.mobileNumber && <small className="error">{errors.mobileNumber}</small>}
            </div>

            <div className="input-group">
                <select name="branch" value={student.branch} onChange={handleChange} required>
                    <option value="">Select Branch</option>
                    {branches.map((b) => <option key={b} value={b}>{b}</option>)}
                </select>
                {errors.branch && <small className="error">{errors.branch}</small>}
            </div>

            <button type="submit">Submit Registration</button>
        </form>
    );
};

export default StudentForm;