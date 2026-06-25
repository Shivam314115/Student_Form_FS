import { useState } from 'react';
//use state == react hook to mnanage state in functional component.
import axios from 'axios';
//using this instead of fetch. 
import './StudentForm.css';

const StudentForm = () => {

    // array of branches to show them as a List view 
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

    // student -> current state of the form data 
    //setStudent -> function to update the state of the form data


    //client side validation and error handling

    const [errors, setErrors] = useState({});
    //this tracks validation for each feild.
    const [statusMessage, setStatusMessage] = useState({ text: '', type: '' });
    // stores the starus message of form submission, registered or failed
    // type can be success or error to style the message accordingly


    //validation function -> takes name of the parameter and its value and returns error message if any validation fails.
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
    // this function handles changes, takes prev state and event object 
    const handleChange = (e) => {
        const { name, value } = e.target;
        setStudent(prev => ({ ...prev, [name]: value }));

        // Clearing  status message when user starts typing again
        if (statusMessage.text) setStatusMessage({ text: '', type: '' });

        // real time validation
        const fieldError = validateField(name, value);
        setErrors(prev => ({ ...prev, [name]: fieldError }));
    };


    //server side validation and submission, check for duplicate values entered that exixts in ou DB.
    //prevents default behaviour of page submission(page reloaded)
    //adds errors to final error for rendering
    //if there is any error: stop the func and render the error 
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

        //server side validation

        try {
            await axios.post('http://localhost:8080/api/students', student);

            setStatusMessage({ text: "Student Registered Successfully!", type: 'success' });
            setStudent({ name: '', enrollmentNumber: '', email: '', mobileNumber: '', branch: '' });
            setErrors({});
        } catch (err) {
            if (err.response) {
                const { status, data } = err.response;

                // Field-specific errors come as a plain object without a "message" key —
                // used for both 400 validation errors and 409 duplicate-field conflicts.
                if (data && typeof data === 'object' && !Array.isArray(data) && !data.message) {
                    setErrors(data);
                    setStatusMessage({ text: '', type: '' });
                    return;
                }

                // Anything else: generic conflict (DB race condition fallback), not-found, server error, etc.
                const message = typeof data === 'string' ? data : data?.message;
                setErrors({});
                setStatusMessage({ text: message || `Server responded with status ${status}`, type: 'error' });
            } else if (err.request) {
                setErrors({});
                setStatusMessage({ text: 'Unable to connect to the server. Please check if the backend is running.', type: 'error' });
            } else {
                setErrors({});
                setStatusMessage({ text: err.message || 'An unexpected error occurred.', type: 'error' });
            }
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