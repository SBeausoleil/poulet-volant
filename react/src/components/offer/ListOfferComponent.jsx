import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import ApiService from "../../service/ApiService";
import { USER_STORAGE } from '../../constants';
import { canSeeAllOffers, canOwnOffers } from '../../util/policy';

class ListOfferComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            user: JSON.parse(sessionStorage.getItem(USER_STORAGE)),
            offers: []
        }
        this.deleteOffer = this.deleteOffer.bind(this);
        this.editOffer = this.editOffer.bind(this);
        this.addOffer = this.createOffer.bind(this);
        this.reloadOfferList = this.reloadOfferList.bind(this);
    }

    componentDidMount() {
        this.reloadOfferList();
    }

    reloadOfferList() {
        if (canSeeAllOffers(this.state.user)) {
            ApiService.fetchOffers().then((res) => {
                this.setState({ offers: res.data })
            });
        } else if (canOwnOffers(this.state.user)) {
            ApiService.fetchMyOffers()
                .then((res) => {
                    this.setState({ offers: res.data })
                });
        }
    }

    notificationHandler(message) {
        let notifications = {
            texte: message
        };
        ApiService.addNotification(notifications)
            .then(res => {
                console.log(res.data);
                window.location.reload(false);
            });
    }

    deleteOffer(offerId) {
        ApiService.deleteOffer(offerId)
            .then(res => {
                console.log(res.data);
                this.setState({ message: 'Offer deleted successfully.' });
                this.setState({ offers: this.state.offers.filter(offer => offer.id !== offerId) });
                this.notificationHandler('Vous avez supprimé une offre avec succès.')
            });
    }

    editOffer(id) {
        this.props.history.push('/offer/edit/' + id);
    }

    createOffer() {
        this.props.history.push('/offer/create');
    }

    render() {
        return (
            <div>
                <h2 className="text-center">Liste des offres</h2>
                <button className="btn btn-danger" style={{ width: '100px' }} onClick={() => this.createOffer()}>Creer offre</button>
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <th className="hidden">Id</th>
                            <th>Titre</th>
                            <th>Organisation</th>
                            <th>Description</th>
                            <th>Supprimer</th>
                            <th>Modifier</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.offers.map(
                                offer =>
                                    <tr key={offer.id}>
                                        <td>{offer.id}</td>
                                        <td><Link to={'/offer/' + offer.id}>{offer.title}</Link></td>
                                        <td>{offer.organization.name}</td>
                                        <td>{offer.description}</td>
                                        <td>
                                            <button className="btn btn-success" onClick={() => this.deleteOffer(offer.id)}>Supprimer</button>
                                        </td>
                                        <td>
                                            <button className="btn btn-success" onClick={() => this.editOffer(offer.id)} style={{ marginLeft: '20px' }}>Modifier</button>
                                        </td>
                                    </tr>
                            )
                        }
                    </tbody>
                </table>

            </div>
        );
    }

}

export default ListOfferComponent;