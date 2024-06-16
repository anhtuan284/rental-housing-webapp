import axios from "axios";

const BASE_URL = 'http://localhost:8080/RentApp';

export const endpoints = {
    'login': '/api/login/',
    'register': '/api/users/',
    'current-user': '/api/current-user/',
    
}

export const authApi = (accessToken: String) => axios.create({
    baseURL: "http://localhost:8080/RentApp",
    headers: {
        "Authorization": `${accessToken}`
    }
})

export default axios.create({
    baseURL: BASE_URL
});