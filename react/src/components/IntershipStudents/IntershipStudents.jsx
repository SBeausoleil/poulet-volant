import React, { Component } from "react";
import axios from "axios";
import { API_BASE_URL } from '../../constants';
import ApiService from "../../service/ApiService";

class IntershipStudents extends Component {

    constructor(props) {
        super(props);
        this.state = this.getDefaultState;
    }

    async componentDidMount() {
        await ApiService.fetchAllStudents().then(response => {
                this.setState({ students: response.data });
                console.log(response.data);
            })
            .catch(err => {
                this.setState({ message: "Erreur, lors de l'affichage de tout les étudiants." })
            });

        if (this.state.students.length === 0)
            this.setState({ message: "Aucune offre pour le moment" })
    }

    render() {
        const { students } = this.state;
        const offers = this.state.studentOffers;
        return (
            <div>
                <div className="internshipStudents">
                    <table className="table table-striped" key={"internshipStudents"}>
                        <caption className="captionTop">Les étudiants en stage</caption>

                        <thead>
                            <tr>
                                <th>Nom</th>
                                <th>Prénom</th>
                                <th></th>
                            </tr>
                        </thead>
                        {students.map(student =>
                            <tbody className="student_row" key={"body" + student.id}>
                                <tr>
                                    <td >{student.firstName}</td>
                                    <td >{student.lastName}</td>
                                    <td>
                                        <input onClick={this.update} value={student.id} type="radio" className="form-check-input" id="materialUnchecked" name="materialExampleRadios" unchecked="true" />
                                        <label className="form-check-label" for="materialUnchecked"></label>
                                    </td>
                                </tr>
                            </tbody>
                        )
                        }
                    </table>
                </div>
                <div className="internshipStudentsOffers">
                    <table class="table table-striped" key={"restrictedStudentOffers"}>
                        <caption className="captionTop">Offres accessibles de l'étudiant</caption>
                        <thead>
                            <tr>
                                <th>Organisation</th>
                                <th>Titre</th>
                            </tr>
                        </thead>
                        {offers.map(offer =>
                            <tbody className="offer_row" key={"body" + offer.id + 1}>
                                <tr>
                                    <td>{offer.organization.name}</td>
                                    <td>{offer.title}</td>
                                </tr>
                            </tbody>
                        )
                        }
                    </table>

                </div>
            </div>
        );
    }

    update = (event) => {
        var inputValue = document.getElementById("studentId");
        inputValue.value = event.target.value;
        this.retrieveOffers(inputValue.value);
    }

    async retrieveOffers(studentId) {
        const id = this.state.actualStudentId;
        const response = await axios({ url: API_BASE_URL + "/offer/offers/" + studentId + "/allowedOffers", header: "application/json", method: "get" });
        this.setState({ studentOffers: response.data });
        console.log(response.data);
    }

    get getDefaultState() {
        return {
            message: "",
            students: [],
            actualStudentId: 0,
            studentOffers: [],
        };
    }

    async updateRestrictedOffers(studentId, offerId) {
        const response = await axios({
            url: API_BASE_URL + "/offer/offers/" + studentId + "/allowedOffers", header: "application/json", method: "get" });
        this.setState({ studentOffers: [] })
        response.data.forEach(offer => { if (offer.id != offerId) { this.state.studentOffers.push(offer) } })
        this.forceUpdate();
    }
}
export default IntershipStudents;