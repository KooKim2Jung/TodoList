import axios from 'axios';
export default axios.create({
    baseURL: "http://localhost:8081",
    headers: {
        "Content-type":"application/json",
        'Access-Control-Allow-Origin': 'http://localhost:8081', // 서버 domain
        'X-AUTH-TOKEN': `${localStorage.getItem('X-AUTH-TOKEN')}`
    },

    withCredentials: true
});