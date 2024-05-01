import React, { Component } from 'react';
import {
    Link
} from 'react-router-dom';
import { Layout } from 'antd';
import { canSeeAllOffers, canRestrictOffer, canSeeStudentOffers, canUploadCv, canSeeListOfApplications, canOwnOffers, canSeeGestionFormulaireEmployer, canSeeGestionFormulaireInternshipManager, canSeeListOfCV, canSeeGestionFormulaireEmployerStudent, canCreateFormulaireInternshipManager, canSendNotification } from '../util/policy';
import { Navbar, Nav } from 'react-bootstrap';
import ApiService from '../service/ApiService';

const Header = Layout.Header;

class AppHeader extends Component {

    constructor(props) {
        super(props);
        this.state = {
            notifications: []
        }
        this.handleMenuClick = this.handleMenuClick.bind(this);
        this.deleteNotification = this.deleteNotification.bind(this);
        this.reloadNotificationList = this.reloadNotificationList.bind(this);
    }

    handleMenuClick({ key }) {
        if (key === "logout") {
            this.props.onLogout();
        }
    }

    componentDidMount() {
        this.reloadNotificationList();
    }

    reloadNotificationList() {
        ApiService.fetchNotification()
            .then((res) => {
                console.log(res.data.result);
                this.setState({ notifications: res.data.result })
            });
    }

    deleteNotification(id) {
        ApiService.deleteNotification(id).then((res) => {
            console.log(res);
            let i = 0;
            for (i = 0; i < this.state.notifications.length; i++) {
                if (id === this.state.notifications[i].id) {
                    break;
                }
            }
            this.state.notifications.splice(i, 1);
            this.forceUpdate();
        })
    }

    render() {
        let menuItems;

        if (this.props.isAuthenticated) {
            menuItems = {
                home: { url: "/home", text: "Maison" },
                profil: { url: "/user/UserProfil", text: "Modification du profil" },
                notification: { url: "/notification", text: "Notifier un utilisateur", condition: canSendNotification(this.props.currentUser) },
                cv: { url: "/cv", text: "CV", condition: canUploadCv(this.props.currentUser) },
                list_cv: { url: "/listcv", text: "Liste des CV", condition: canSeeListOfCV(this.props.currentUser) },
                offers: { url: "/offers", text: "Offres", condition: canSeeAllOffers(this.props.currentUser) },
                offers_employer: { url: "/offers", text: "Mes Offres", condition: canOwnOffers(this.props.currentUser) },
                formulaire_send: { url: "/formulaire", text: "Envoyer un formulaire", condition: canSeeGestionFormulaireEmployerStudent(this.props.currentUser) },
                formulaire_create: { url: "/createFormulaire", text: "Créer contrat de stage", condition: canCreateFormulaireInternshipManager(this.props.currentUser) },
                formulaire_gestion: { url: "/listFormulaire", text: "Gestion des formulaires", condition: canSeeGestionFormulaireInternshipManager(this.props.currentUser) },

                offer_restrict: { url: "/offer/restrict", text: "Restreindre offres", condition: canRestrictOffer(this.props.currentUser) },
                offer_listing: { url: "/offer/student-offers", text: "Offres", condition: canSeeStudentOffers(this.props.currentUser) },
                applications_listing: { url: "/applications/all", text: "Applications", condition: canSeeListOfApplications(this.props.currentUser) },

                logout: { url: "/logout", text: "Déconnection", onClick: this.props.onLogout }
            };
        } else {
            menuItems = {
                login: { url: "/login", text: "Connection" },
                signup: { url: "/signup", text: "Enregistrement" },
            };
        }

        return (
            <>
                <Header className="app-header">
                    <Navbar bg="dark" expand="lg" className="navbar-dark">
                        <Navbar.Brand>Poulet Volant</Navbar.Brand>
                        <Navbar.Toggle aria-controls="header-navbar" />
                        <Navbar.Collapse id="header-navbar">
                            <Nav className="mr-auto">
                                {
                                    Object.entries(menuItems).map(([key, item]) => {
                                        if (!item.hasOwnProperty("condition") || item.condition) {
                                            return (
                                                <Nav.Item key={key} onClick={item.onClick}>
                                                    <Link to={item.url} className="nav-link">{item.text}</Link>
                                                </Nav.Item>
                                            )
                                        }
                                    })
                                }
                            </Nav>
                        </Navbar.Collapse>
                    </Navbar>
                </Header>
                <div>
                    {
                        this.state.notifications.map(
                            notification =>
                                <ul id={"notification_" + notification.id} key={notification.id}>
                                    <div>
                                        <button type="button" class="close" aria-label="Close" onClick={() => this.deleteNotification(notification.id)}>
                                            <span aria-hidden="true" style={{ color: 'red' }}>{notification.texte} &times;</span>
                                        </button>
                                    </div>
                                    <br />
                                </ul>
                        )
                    }
                </div>
            </>
        );
    }
} export default AppHeader;