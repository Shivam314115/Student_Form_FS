import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/students';

class StudentService {
    getStudents() {
        return axios.get(API_BASE_URL);
    }
    createStudent(student) {
        return axios.post(API_BASE_URL, student);
    }
}

export default new StudentService();