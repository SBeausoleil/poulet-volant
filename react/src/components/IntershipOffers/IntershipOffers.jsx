import React, { Component } from "react";
import ApiService from "../../service/ApiService";

class InternshipOffers extends Component {

    constructor(props) {
        super(props);
        this.state = this.getDefaultState;
    }

    render() {
        const { offers } = this.state;
        return (
            <div className="internshipOffers" >
                <table class="table table-striped" key={"internshipStudents"}>
                <caption className="captionTop">Ensemble des offres disponibles</caption>

                    <thead >
                        <tr>
                            <th>Organisation</th>
                            <th>Titre</th>
                            <th></th>
                        </tr>
                    </thead>
                    {offers.map(offer =>
                        <tbody className="offer_row" key={"body" + offer.id}>
                            <tr>
                                <td>{offer.organization.name}</td>
                                <td>{offer.title}</td>
                                <td>
                                    <input onClick={this.update} value={offer.id} type="radio" class="form-check-input" id="materialUnchecked" name="materialExampleRadiosOffers" unchecked="true" />
                                    <label class="form-check-label" for="materialUnchecked"></label>
                                </td>
                            </tr>
                        </tbody>
                    )
                    }
                </table>
                <h1>{this.state.message}</h1>
            </div>
        )
    }

    update = (event) => {
        var actualOfferId = document.getElementById("offerId");
        actualOfferId.value = event.target.value;
    }

    async componentDidMount() {
        console.log("componentDidMount INTERNSHIP OFFERS")
        await ApiService.fetchOffers().then(response => {
                console.log("io response")
                this.setState({ offers: response.data });
                console.log(response.data);
            })
            .catch(err => {
                console.log("io err")
                this.setState({ message: "Erreur, lors de l'affichage de toutes les offres." })
            });

        if (this.state.offers.length === 0)
            this.setState({ message: "Aucune offre pour le moment" })
    }

    get getDefaultState() {
        return {
            message: "",
            offers: []
        }
    }

}
export default InternshipOffers;