import { API_BASE_URL, ACCESS_TOKEN, USER_STORAGE } from '../constants';
import axios from 'axios';

axios.interceptors.request.use(config => {
	if (localStorage.getItem(ACCESS_TOKEN)) {
		config.headers.authorization = 'Bearer ' + localStorage.getItem(ACCESS_TOKEN);
	}
	return config;
}, error => {
	return Promise.reject(error);
});

axios.interceptors.response.use(response => {
    parseUserUpdateResponse(response, true);
    return response;
});

/**
 * Parse the response of the server which MAY contain data of the current user.
 * 
 * @param {any} response
 * @param {boolean} strip if true, remove the field containing the user from the response after successful parsing.
 */
export function parseUserUpdateResponse(response, strip) {
    if (response.headers && response.headers.user_update) {
        sessionStorage.setItem(USER_STORAGE, response.headers.user_update);
        let now = new Date();
        console.log("updated the user " + now.toLocaleTimeString() + ':' + now.getMilliseconds());
        console.log(response.headers.user_update);
        if (strip) {
            delete response.headers.user_update;
        }
    }
}

export function getCurrentUser() {
	if (!isAuthenticated()) {
		return Promise.reject("Aucun Token daccès.");
	}

    return axios.get(API_BASE_URL + '/api/user/me');
}

export function isAuthenticated() {
	return localStorage.getItem(ACCESS_TOKEN) !== null;
}