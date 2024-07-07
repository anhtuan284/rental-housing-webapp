import axios from "axios";
import Cookies from "js-cookie";

export const BASE_URL = 'http://42.114.84.109:8080/RentApp';
export const CLIENT_URL = 'http://42.114.84.109:5173';


export const endpoints = {
    // AUTHENTICATION & USER
    'login': '/api/login/',
    'login-google': '/api/google-login/',
    'register': '/api/users/',
    'current-user': '/api/current-user/',
    'profile': (id?: string) => `/api/profile/${id}`,
    'follow': '/api/Follow',
    'unfollow': '/api/UnFollow',
    'check-follow': '/api/CheckFollow/',
    'check-user-by-email': '/api/CheckUserByEmail/',
    'update-profile': (userId?: string) => `/api/profile/${userId}/update`,
    'get-notifications': '/api/Notification/getNotificationOfUser',

    // POSTS
    'create-post-landlord': '/api/post/landlordUpPost',
    'create-post-renter': '/api/post/renterUpPost',
    'get-renter-post': '/api/post/PostOfRenter/',
    'get-landlord-post': '/api/post/PostOfLandlord/',
    'get-post-details': (postId?: string) => `/api/post/${postId}`,
    'get-comments': (postId?: string) => `/api/comment/${postId}`,
    'add-comment': '/api/comment/add',
    'edit-comment': '/api/comment/update',
    'delete-comment': (commentId?: string) => `/api/comment/${commentId}/delete`,
    'get-post-near-pin': '/api/posts/near-you/', 
    'report-post': `/api/report/add`
    
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