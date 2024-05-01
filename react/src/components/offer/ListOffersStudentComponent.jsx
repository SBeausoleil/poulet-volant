import React, { Component } from "react";
import { Link } from 'react-router-dom';
import Loader from 'react-loader-spinner';
import ApiService from "../../service/ApiService";

class ListOffersStudentComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            offers: null,
        }
        this.state = this.getDefaultState;
    }

    async componentDidMount() {
        await ApiService.fetchOffersStudent()
            .then(response => {
                this.setState({ offers: response.data });
                console.log(response.data);
                ;
            })
            .catch(err => {
                console.log(err);
                this.setState({ message: "Erreur" })
            });

        if (this.state.offers.length === 0)
            this.setState({ message: "Aucune offres pour le moment" })
    }

    render() {
        if (this.state.offers === null) {
            return (
                <div style={{width: "100%", height: "100", display: "flex", justifyContent: "center", alignItems: "center"}}>
                    <Loader type="ThreeDots" color="#2BAD60" height="100" width="100" />
                </div>
            )
        }
        if (this.state.offers.length === 0) {
            return (
                <div className="container">
                    Aucune offres pour le moment.
                </div>
            )
        }

        return (
            <div className="container">
                <table className="table">
                    <thead>
                        <tr>
                            <th>Titre</th>
                            <th>Entreprise</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.offers.map(
                                offer =>
                                    <tr key={offer.id}>
                                        <td><Link to={"/offer/" + offer.id}>{offer.title}</Link></td>
                                        <td>{offer.organization.name}</td>
                                    </tr>
                            )
                        }
                    </tbody>
                </table>
            </div>
        );
    }

    get getDefaultState() {
        return ({
            offers: [],
            message: ""
        });

    }

}
export default ListOffersStudentComponent;