import React, { Component } from 'react';
import {
    Route,
    withRouter,
    Switch
} from 'react-router-dom';
import RestrictedRoute from './util/RestrictedRoute';
import { getCurrentUser } from './util/auth';
import {
    ACCESS_TOKEN,
    USER_STORAGE,
} from './constants';
import GestionFormulaireInternshipManager from './components/formulaire/GestionFormulaireInternshipManager';
import NotificationForm from './components/notification/NotificationForm';
import GestionFormulaireEmployerStudent from './components/formulaire/GestionFormulaireEmployerStudent';
import AppHeader from './components/AppHeader';
import CreationFormulaireInternshipManager from './components/formulaire/CreationFormulaireInternshipManager';
import LoginComponent from './components/auth/LoginComponent';
import HomeComponent from './components/user/HomeComponent';
import ListOfferComponent from "./components/offer/ListOfferComponent";
import AddOfferComponent from "./components/offer/AddOfferComponent";
import EditOfferComponent from "./components/offer/EditOfferComponent";
import ListCVComponent from "./components/cv/ListCVComponent"
import UploadCVComponent from "./components/cv/UploadCVComponent"
import CreateUser from './components/user/CreateUserComponent';
import UserProfil from './components/user/UserProfil';
import UserCV from './components/user/UserCV';
import RestrictOffer from './components/OfferRestrictor/RestrictOffer';
import ListOffersStudentComponent from './components/offer/ListOffersStudentComponent';
import CreateOrganizationComponent from './components/organization/CreateOrganizationComponent';
import DisplayOfferComponent from './components/offer/DisplayOfferComponent';
import { Layout } from 'antd';
import { canCreateOffer, canSendNotification, canCreateFormulaireInternshipManager, canSeeListOfCV, canEditOffer, canRestrictOffer, canSeeStudentOffers, canCreateOrganization, canSeeListOfApplications, canUploadCv, canSeeAllOffers, canOwnOffers, canSeeGestionFormulaireInternshipManager, canSeeGestionFormulaireEmployerStudent } from './util/policy';
import ListApplicationComponent from './components/application/ListApplicationsComponent';

const { Content } = Layout;

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            currentUser: null,
            isAuthenticated: false,
        }
        this.loadCurrentUser = this.loadCurrentUser.bind(this);
        this.handleLogin = this.handleLogin.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
        this.onRouteChanged = this.onRouteChanged.bind(this);
    }

    loadCurrentUser() {
        getCurrentUser()
            .then(response => {
                sessionStorage.setItem(USER_STORAGE, JSON.stringify(response.data));
                if (response.data.mandatoryRedirects.length > 0 && response.data.mandatoryRedirects[0].url !== this.props.location.pathname) {
                    this.props.history.push(response.data.mandatoryRedirects[0].url);
                }
                this.setState({
                    currentUser: response.data,
                    isAuthenticated: true,
                });
            });
    }

    handleLogin() {
        this.loadCurrentUser();
        this.props.history.push("/home");
    }

    handleLogout(redirectTo = "/") {
        localStorage.removeItem(ACCESS_TOKEN);
        sessionStorage.removeItem(USER_STORAGE);
        this.setState({
            currentUser: null,
            isAuthenticated: false
        });

        this.props.history.push(redirectTo);
    }

    componentDidMount() {
        this.loadCurrentUser();
    }

    componentDidUpdate(previousProps) {
        if (this.props.location !== previousProps.location) {
            this.onRouteChanged();
        }
    }

    onRouteChanged() {
        if (this.state.isAuthenticated) {
            this.state.currentUser = JSON.parse(sessionStorage.getItem(USER_STORAGE));
            let redirects = this.state.currentUser.mandatoryRedirects;
            let now = new Date();
            if (redirects.length > 0 && redirects[0].url !== this.props.location.pathname) {
                console.log("Redirecting " + now.toLocaleTimeString() + ':' + now.getMilliseconds())
                this.props.history.push(redirects[0].url);
            }
        }
    }

    render() {
        let header = (
            <AppHeader isAuthenticated={this.state.isAuthenticated}
                currentUser={this.state.currentUser}
                onLogout={this.handleLogout} />
        );

        if (!this.state.currentUser) {
            return (
                <Layout className="app-container">
                    {header}

                    <Content className="app-content">
                        <div className="container">
                            <Switch>
                                <Route path="/login"
                                    render={(props) => <LoginComponent onLogin={this.handleLogin} {...props} />}></Route>
                                <Route path="/signup" component={CreateUser}></Route>
                            </Switch>
                        </div>
                    </Content>
                </Layout>
            )
        }

        return (
            <Layout className="app-container">
                {header}

                <Content className="app-content">
                    <div className="container">
                        <Switch>
                            <Route path="/home" component={HomeComponent}></Route>
                            <Route path="/user/UserProfil" exact component={UserProfil}></Route>
                            <RestrictedRoute path="/formulaire" exact component={GestionFormulaireEmployerStudent} condition={canSeeGestionFormulaireEmployerStudent(this.state.currentUser)}></RestrictedRoute>
                            <RestrictedRoute path="/listFormulaire" exact component={GestionFormulaireInternshipManager} condition={canSeeGestionFormulaireInternshipManager(this.state.currentUser)}></RestrictedRoute>
                            <RestrictedRoute path="/createFormulaire" exact component={CreationFormulaireInternshipManager} condition={canCreateFormulaireInternshipManager(this.state.currentUser)}></RestrictedRoute>
                            <RestrictedRoute path="/notification" exact component={NotificationForm} condition={canSendNotification(this.state.currentUser)}></RestrictedRoute>
                            <Route path="/user/UserCV" component={UserCV}></Route>
                            <RestrictedRoute path="/cv" exact component={UploadCVComponent} condition={canUploadCv(this.state.currentUser)}></RestrictedRoute>
                            <RestrictedRoute path="/listcv" component={ListCVComponent} condition={canSeeListOfCV(this.state.currentUser)}></RestrictedRoute>
                            <RestrictedRoute path="/offer/create" component={AddOfferComponent} condition={canCreateOffer(this.state.currentUser)} user={{ user: this.state.currentUser }}/>
                            <RestrictedRoute path="/offer/edit/:offerId" component={EditOfferComponent} condition={canEditOffer(this.state.currentUser)} />
                            <RestrictedRoute path="/offer/restrict" component={RestrictOffer} condition={canRestrictOffer(this.state.currentUser)} />
                            <RestrictedRoute path="/offer/student-offers" component={ListOffersStudentComponent} condition={canSeeStudentOffers(this.state.currentUser)}></RestrictedRoute>
                            <RestrictedRoute path="/organization/create" exact component={CreateOrganizationComponent} condition={canCreateOrganization(this.state.currentUser)} />
                            <RestrictedRoute path="/offers" exact component={ListOfferComponent} user={this.state.currentUser} condition={canSeeAllOffers(this.state.currentUser) || canOwnOffers(this.state.currentUser)} />
                            <Route path="/offer/:offerId" render={(props) => <DisplayOfferComponent {...props} offerId={props.match.params.offerId} />} />
                            <RestrictedRoute path="/applications/all" exact component={ListApplicationComponent} condition={canSeeListOfApplications(this.state.currentUser)} />
                        </Switch>
                    </div>
                </Content>
            </Layout>
        );
    }
}

export default withRouter(App);
