import React, { Component } from 'react'
import ApiService from "../../service/ApiService";

class EditOfferComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            id: props.match.params.offerId,
            title: '',
            description: '',
        }
        this.loadOffer = this.loadOffer.bind(this);
        console.log("ID = " + this.state.id)
    }

    componentDidMount() {
        this.loadOffer();
    }

    loadOffer() {
        ApiService.fetchOfferById(this.state.id)
            .then((res) => {
                let offer = res.data;
                this.setState({
                    id: offer.id,
                    title: offer.title,
                    description: offer.description
                })
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
        var offer = {
            title: this.state.title,
            description: this.state.description
        };

        ApiService.editOffer(this.state.id, offer)
            .then(res => {
                console.log(res);
                this.setState({ message: 'Offre modifiée avec succès.' });
                this.props.history.push('/offers');
                this.notificationHandler('Vous avez modifié une offre avec succès.');
            }).catch(error => {
                console.log(error);
            })
    }

    onChange = (e) =>
        this.setState({ [e.target.name]: e.target.value });

    render() {
        return (
            <div>
                <h2 className="text-center">Enregistrer une offre</h2>
                <form>
                    <div className="form-group">
                        <input type="text" placeholder="Titre de l'offre" className="form-control" name="title" value={this.state.title} onChange={this.onChange} />
                    </div>
                    <div className="form-group">
                        <textarea placeholder="Description de l'offre" className="form-control" name="description" value={this.state.description} onChange={this.onChange} />
                    </div>
                    <div className="form-group">
                        <button type="submit" className="btn btn-success" onClick={this.handleSubmit}>Enregistrer</button>
                    </div>
                </form>
            </div>
        );
    }
}

export default EditOfferComponent;