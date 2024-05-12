// globals.js
import {jwtDecode} from "jwt-decode";

let isAdmin = false;
let isManager = false;
let isTechnician = false;

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

const setTechnicianStatus = (status) => {
    isTechnician = status;
}

const getTechnicianStatus = () => {
    return isTechnician;
}

// Function to extract roles from JWT token and set admin, manager and technician status
const setRolesFromJWT = (token) => {
    const decodedToken = jwtDecode(token);
    if (decodedToken?.roles) {
        decodedToken.roles.forEach(role => {
            if (role.authority === 'ADMIN') {
                setAdminStatus(true);
            } else if (role.authority === 'MANAGER') {
                setManagerStatus(true);
            } else if (role.authority === 'TECHNICIAN'){
                setTechnicianStatus(true);
            }
        });
    }
};

export { setAdminStatus, getAdminStatus, setManagerStatus, getManagerStatus, setTechnicianStatus, getTechnicianStatus, setRolesFromJWT };