import React, { Component } from 'react'
import ApiService from "../../service/ApiService";
import ChooseOrganizationComponent from './ChooseOrganizationComponent';
import { TYPE_GS } from '../../constants';

class AddOfferComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            title: '',
            description: '',
            country: '',
            province: '',
            streetAddress: '',
            city: '',
            postalCode: '',
            message: null,

            nameOrganization: '',
            descriptionOrganization: '',
            namePerson: '',
            phonePerson: '',
            emailPerson: '',
            idOrganization: ''
        }
        this.handleSubmit = this.handleSubmit.bind(this);
        this.notificationHandler = this.notificationHandler.bind(this);
    }

    async createOrganisation(e) {
        e.preventDefault();

        let organization = {
            emailPersonne: this.state.emailPerson,
            phoneNumberPersonne: this.state.phonePerson,
            namePersonne: this.state.namePerson,
            nameOrganization: this.state.nameOrganization,
            descriptionOrganization: this.state.descriptionOrganization
        }

        await ApiService.storeImplOffer(organization).then((response) => {
            console.log(response)
           // this.notificationHandler('Vous avez créé avec succès une nouvelle organisation.');
        }).catch(error => {
            console.log(error)
        });

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

    handleSubmit = (e) => {
        e.preventDefault();
        let offer = {
            title: this.state.title,
            description: this.state.description,
            streetAddress: this.state.streetAddress,
            city: this.state.city,
            province: this.state.province,
            postalCode: this.state.postalCode,
            country: this.state.country
        };
        if (this.props.user.type === TYPE_GS) {
            offer.idOrganization = this.state.idOrganization
        }

        ApiService.addOffer(offer, this.props.user.type)
            .then(res => {
                console.log(res.data);
                this.setState({ message: 'Offre ajoutée avec succès.' });
                this.props.history.push('/offers');
               // this.notificationHandler('Vous avez créé avec succès une nouvelle offre.');
            });
    }

    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }

    setIdOrganization = (id) => {
        this.setState({ idOrganization: id })
    }

    render() {
        return (
            <div>
                <h2 className="text-center">Enregistrer une offre de stage</h2>

                {(this.props.user.type === TYPE_GS)
                    ? <ChooseOrganizationComponent
                        onChange={this.onChange}
                        createOrganisation={this.createOrganisation.bind(this)}
                        setIdOrganization={this.setIdOrganization}
                    />
                    : <></>
                }

                <form>
                    <div className="form-group">
                        <textarea placeholder="Titre de l'offre" className="form-control" name="title" value={this.state.title} onChange={this.onChange} />
                    </div>
                    <div className="form-group">
                        <textarea placeholder="Description de l'offre" className="form-control" name="description" value={this.state.description} onChange={this.onChange} />
                    </div>
                    <div className="form-group">
                        <input type="text" placeholder="Adresse" className="form-control" name="streetAddress" value={this.state.streetAddress} onChange={this.onChange} />
                    </div>
                    <div className="form-group">
                        <input type="text" placeholder="Ville" className="form-control" name="city" value={this.state.city} onChange={this.onChange} />
                    </div>
                    <div className="form-group">
                        <input type="text" placeholder="Province" className="form-control" name="province" value={this.state.province} onChange={this.onChange} />
                    </div>
                    <div className="form-group">
                        <input type="text" placeholder="Code postal" className="form-control" name="postalCode" value={this.state.postalCode} onChange={this.onChange} />
                    </div>
                    <div className="form-group">
                        <input type="text" placeholder="Pays" className="form-control" name="country" value={this.state.country} onChange={this.onChange} />
                    </div>
                    <br /><br />

                    <div className="form-group">
                        <button type="submit" className="btn btn-success" onClick={this.handleSubmit}>Enregistrer</button>
                    </div>
                </form>
            </div>
        );
    }
}
export default AddOfferComponent;