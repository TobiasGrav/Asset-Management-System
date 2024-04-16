import axios from 'axios';

class HTTPRequest {

  static get(url, token) {
    const header = {headers: {Authorization: `Bearer ` + token}};
    return axios.get(url, header)
                .catch(error => {
                  console.error("get request for " + url + " failed", error);
                });
  }

  static post(url, data, token) {
    const header = {headers: {Authorization: `Bearer ` + token}};
    return axios.post(url, data, header)
                .catch(error => { 
                  console.error("post request for " + url + " failed", error) 
               });
  }

  static put(url, data, token) {
    const header = {headers: {Authorization: `Bearer ` + token}};
    return axios.put(url, data, header)
                .catch(error => { 
                 console.error("put request for " + url + " failed", error) 
               });
  }

  static delete(url, token) {
    const header = {headers: {Authorization: `Bearer ` + token}};
    return axios.delete(url, header)
                .catch(error => { 
                 console.error("delete request for " + url + " failed", error) 
               });
  }

}

export default HTTPRequest;