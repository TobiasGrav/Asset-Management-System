import axios from 'axios';
import URL from './URL';

class HTTPRequest {
  static get(url, token) {
    const header = {headers: {Authorization: `Bearer ` + token}};
    return axios.get(url, header)
                .catch(error => {
                  console.error("get request for " + url + " failed", error);
                  //if(error.response.status == 403) {
                  //  window.location.href = `${URL.FRONTEND}/403`;
                  //} else if(error.response.status == 404) {
                  //  window.location.href = `${URL.FRONTEND}/404`;
                  //}
                });
  }

  static post(url, data, token) {
    const header = {headers: {Authorization: `Bearer ` + token}};
    return axios.post(url, data, header)
                .catch(error => { 
                  console.error("post request for " + url + " failed", error);
                  //if(error.response.status == 403) {
                  //  window.location.href = `${URL.FRONTEND}/403`;
                  //} else if(error.response.status == 404) {
                  //  window.location.href = `${URL.FRONTEND}/404`;
                  //}
               });
  }

  static put(url, data, token) {
    const header = {headers: {Authorization: `Bearer ` + token}};
    return axios.put(url, data, header)
                .catch(error => { 
                  console.error("put request for " + url + " failed", error);
                  //if(error.response.status == 403) {
                  //  window.location.href = `${URL.FRONTEND}/403`;
                  //} else if(error.response.status == 404) {
                  //  window.location.href = `${URL.FRONTEND}/404`;
                  //};
               });
  }

  static delete(url, token) {
    const header = {headers: {Authorization: `Bearer ` + token}};
    return axios.delete(url, header)
                .catch(error => { 
                  console.error("delete request for " + url + " failed", error);
                  //if(error.response.status == 403) {
                  //  window.location.href = `${URL.FRONTEND}/403`;
                  //} else if(error.response.status == 404) {
                  //  window.location.href = `${URL.FRONTEND}/404`;
                  //};
               });
  }

}

export default HTTPRequest;