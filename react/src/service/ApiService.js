import axios from 'axios';
import { API_BASE_URL } from '../constants';
import { TYPE_GS } from '../constants';


const OFFER_API_BASE_URL = API_BASE_URL + '/offer';
const NOTIFICATION_API_BASE_URL = API_BASE_URL + '/notifications';
const FILE_API_BASE_URL = API_BASE_URL + '/cvfiles';
const APPLICATION_API_BASE_URL = API_BASE_URL + '/api/application';
const FORMULAIRE_API_BASE_URL = API_BASE_URL + '/formulaires';
const USER_API_BASE_URL = API_BASE_URL + '/api/user';
const LOGIN_API_BASE_URL = API_BASE_URL + '/api/auth';
const ORGANIZATION_API_BASE_URL = API_BASE_URL + '/api/organization';

class ApiService {

    changeUserPersonnalData(data){
        return axios.post(USER_API_BASE_URL + '/changePersonnalData', data);
    }

    changeUserMDP(data){
       return axios.post(USER_API_BASE_URL + '/changeMDP', data);
    }

    changeUserMail(data){
       return axios.post(USER_API_BASE_URL + '/changeMail', data);
    }

    fetchInfoUser(){
        return axios.get(USER_API_BASE_URL + '/informations');
    }

    addOrganization(name, description){
        return axios.post(ORGANIZATION_API_BASE_URL + '/store', {name, description});
    }

    hasOneOrganization(){
        return axios.get(ORGANIZATION_API_BASE_URL + '/has-one');
    }

    fetchOffersStudent(){
        return axios.get(OFFER_API_BASE_URL + '/offers/restricted');
    }

    fetchAllStudents(){
        return axios.get(USER_API_BASE_URL + '/findAllStudents');
    }

    storeImplOffer(data){
        return axios.post(ORGANIZATION_API_BASE_URL + '/storeImpl', data);
    }

    submitLogin(email, password){
        return axios.post(LOGIN_API_BASE_URL + '/signin', {email, password})
    }

    handleApplyApplication(offerId){
        return axios.post(APPLICATION_API_BASE_URL + '/offer/' + offerId);
    }

    downloadFormulaire(fileName){
        return axios.get(FORMULAIRE_API_BASE_URL + '/downloadFormulaire/' + fileName, {responseType: 'blob'});
    }

    deleteFormulaire(id){
        return axios.delete(FORMULAIRE_API_BASE_URL + '/deleteFormulaire/' + id);
    }

    uploadFormulaire(data){
        return axios.post(FORMULAIRE_API_BASE_URL + '/uploadFormulaire', data);
    }

    createUser(users) {
        return axios.post(USER_API_BASE_URL + '/store', users);
    }

    fetchFormulaire() {
        return axios.get(FORMULAIRE_API_BASE_URL + '/listeFormulaire');
    }

    fetchUsersForNotification(){
        return axios.get(NOTIFICATION_API_BASE_URL + '/listUsers');
    }

    deleteNotification(id){
        return axios.delete(NOTIFICATION_API_BASE_URL + '/' + id);
    }

    addNotification(notifications) {
        return axios.post(NOTIFICATION_API_BASE_URL + '/store', notifications);
    }

    addNotificationDestinataire(requestBody) {
        return axios.post(NOTIFICATION_API_BASE_URL + '/storeDestinataire', requestBody);
    }

    fetchNotification() {
        return axios.get(NOTIFICATION_API_BASE_URL + '/list');
    }

    fetchOffers() {
        return axios.get(OFFER_API_BASE_URL + '/offers');
    }

    fetchMyOffers() {
        return axios.get(OFFER_API_BASE_URL + '/offers/mine');
    }

    fetchOfferById(offerId) {
        return axios.get(OFFER_API_BASE_URL + '/' + offerId);
    }

    deleteOffer(offerId) {
        return axios.delete(OFFER_API_BASE_URL + '/' + offerId);
    }

    addOffer(offer, type) {
        return axios.post(OFFER_API_BASE_URL + ((type === TYPE_GS) ? "/store/internship-manager" : "/store"), offer);
    }

    editOffer(id, offer) {
        return axios.put(OFFER_API_BASE_URL + '/update/' + id, offer);
    }

    fetchDownloadById(fileId) {
        return axios.get(FILE_API_BASE_URL + '/' + fileId);
    }

    fetchDownloads() {
        return axios.get(FILE_API_BASE_URL);
    }

    fetchMyCv() {
        return axios.get(FILE_API_BASE_URL + "/studentCV");
    }

    fetchHasCv() {
        return axios.get(FILE_API_BASE_URL + "/has_cv");
    }

    download(fileId) {
        return axios.get(FILE_API_BASE_URL + '/download/' + fileId);
    }

    uploadCv(data){
        return axios.post(FILE_API_BASE_URL + '/upload/cv', data);
    }

    downloadCv(fileName) {
        return axios.get(FILE_API_BASE_URL + '/download/' + fileName, { responseType: 'blob' });
    }

    deleteDownload(id) {
        return axios.delete(FILE_API_BASE_URL + '/delete/' + id);
    }

    fetchApplication(applicationId) {
        return axios.get(APPLICATION_API_BASE_URL + '/' + applicationId + '/simplified');
    }

    fetchMyApplication(offerId) {
        return axios.get(APPLICATION_API_BASE_URL + '/offer/' + offerId + '/simplified/mine');
    }

    fetchAllApplications() {
        return axios.get(APPLICATION_API_BASE_URL + '/all');
    }

    fetchApplicationsForOffer(offerId) {
        return axios.get(APPLICATION_API_BASE_URL + '/offer/' + offerId + '/simplified');
    }

    updateApplicationStatus(applicationId, status) {
        return axios.put(APPLICATION_API_BASE_URL + '/' + applicationId + '/update', { status: status });
    }

    deleteDownloadCVUser(id){
        return axios.delete(FILE_API_BASE_URL + '/delete/' + id)
    }

}

export default new ApiService();
