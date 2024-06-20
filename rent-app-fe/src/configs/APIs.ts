import axios from "axios";
import Cookies from "js-cookie";

const BASE_URL = 'http://localhost:8080/RentApp';

export const endpoints = {
    'login': '/api/login/',
    'register': '/api/users/',
    'current-user': '/api/current-user/',
    'profile': (id?: string) => `/api/profile/${id}`,
    'follow': '/api/Follow',
    'unfollow': '/api/UnFollow',
    'check-follow': '/api/CheckFollow/',
    'check-user-by-email': '/api/CheckUserByEmail/',
    'create-post': '/api/posts/',
    'get-renter-post': '/api/PostOfRenter/',
    'get-landlord-post': '/api/PostOfLandlord/',
    
}

export const authApi = (accessToken: string = Cookies.get("access_token") || "") => axios.create({
    baseURL: BASE_URL,
    headers: {
        "Authorization": `${accessToken}`
    }
})

export default axios.create({
    baseURL: BASE_URL
});