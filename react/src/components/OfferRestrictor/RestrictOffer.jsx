import React, { Component } from "react";
import Axios from "axios";
import IntershipStudents from "../IntershipStudents/IntershipStudents";
import InternshipOffers from "../IntershipOffers/IntershipOffers";

class RestrictOffer extends Component {

    constructor(props) {
        super(props);
        this.state = this.getDefaultState;
        this.child = React.createRef();
    }

    render() {
        let self = this;

        return (
            <div>
                <h3 id="actualMessage" style={{ textAlign: "center" }}>{this.state.message}</h3>
                    <IntershipStudents ref={self.child}></IntershipStudents>
                    <form className="restrictOfferForm" method="post" onSubmit={(event) => { event.preventDefault(); self.update(self.state); }}>
                        <fieldset>
                            <caption className="captionModified" >Restreindre Offre</caption>

                            <input id="studentId" name="studentId" type="text" placeholder="0" required="true"
                                onChange={this.changeStudentId}
                                readOnly="true"
                                hidden="true"
                            />

                            <input id="offerId" name="offerId" type="text" placeholder="0" required="true"
                                onChange={this.changeOfferId}
                                readOnly="true"
                                hidden="true"
                            />
                            <button type="submit" value="addRestriction" className="btn btn-primary">Envoyer</button>
                        </fieldset>
                    </form>
                    <InternshipOffers></InternshipOffers>
            </div>
        )
    }

    get getDefaultState() {
        return { studentId: 0, offerId: 0, message: "", error: 0, studentOffers: [{ id: "", description: "" }] };
    }

    changeOfferId = (event) => {
        this.setState({ offerId: event.target.value });
    }

    changeStudentId = (event) => {
        this.setState({ studentId: event.target.value });
    }

    update(info) {
        let content = ({
            studentId: document.getElementById("studentId").value,
            offerId: document.getElementById("offerId").value
        });
        let regex = (/[\D]/);
        if (regex.test(this.state.offerId)) {
            this.setState({ message: "Erreur dans l'identifiant de l'offre" })
            return;
        }
        else if (regex.test(this.state.studentId)) {
            this.setState({ message: "Erreur dans l'identifiant de l'étudiant" })
            return;
        }
        else if (content.offerId == "" || content.studentId == "") {
            this.setState({ message: "Veuillez sélectionner un étudiant et un stage" })
            return;
        }
        else {
            Axios({
                method: "post",
                url: "/api/restrictOffer",
                data: content,
                header: "application/json"
            }).then(response => {
                this.setState({ message: "Restriction effectué" })
            }).catch(err => {
                if (err.message === "Request failed with status code 403")
                    this.setState({ message: "Offre non trouvée", error: 1 });
                else if (err.message === "Request failed with status code 404")
                    this.setState({ message: "Etudiant non trouvé", error: 1 });
            })
            this.child.current.updateRestrictedOffers(content.studentId, content.offerId);
        }
    }
}
export default RestrictOffer;