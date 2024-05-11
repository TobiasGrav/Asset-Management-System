// globals.js
import {jwtDecode} from "jwt-decode";

let isAdmin = false;
let isManager = false;

const setAdminStatus = (status) => {
    isAdmin = status;
};

const getAdminStatus = () => {
    return isAdmin;
};

const setManagerStatus = (status) => {
    isManager = status;
}

const getManagerStatus = () => {
    return isManager;
}

// Function to extract roles from JWT token and set admin/manager status
const setRolesFromJWT = (token) => {
    const decodedToken = jwtDecode(token);
    if (token == null){
        window.location("/")
    }
    if (decodedToken?.roles) {
        decodedToken.roles.forEach(role => {
            if (role.authority === 'ADMIN') {
                setAdminStatus(true);
            } else if (role.authority === 'MANAGER') {
                setManagerStatus(true);
            }
        });
    }
};

export { setAdminStatus, getAdminStatus, setManagerStatus, getManagerStatus, setRolesFromJWT };