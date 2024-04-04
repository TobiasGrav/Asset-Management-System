import axios from 'axios';

class HTTPRequest {

  static get(url, token) {
      const header = {headers: {Authorization: `Bearer ` + token}};
      return axios.get(url, header)
                  .then(res => {
                     return res;
                  })
                  .catch(error => { 
                   console.error("get request for " + url + " failed", error) 
                 });
      }

}

export default HTTPRequest;