import React, { Component } from 'react'
import ApiService from "../../service/ApiService";

class NotificationForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            destinataire: null,
            message: null,
            users: [],
            texte: null
        }
        this.notificationHandler = this.notificationHandler.bind(this);
        this.reloadUsersList = this.reloadUsersList.bind(this);
    }

    componentDidMount() {
        this.reloadUsersList();
    }

    reloadUsersList() {
        ApiService.fetchUsersForNotification()
            .then((res) => {
                this.setState({ users: res.data.result })
            });
    }

    notificationHandler(e) {
        e.preventDefault();
        let requestBody = {
            texte: this.state.texte,
            destinataire: this.state.destinataire
        }
        ApiService.addNotificationDestinataire(requestBody)
            .then(res => {
                console.log(res.data);
                this.setState({ message: 'Vous avez ajouté une notification. (' + this.state.texte + ')' });
            });
    }

    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }

    onChangeEmail = (e) => {
        this.setState({ destinataire: e.target.value });
    }

    render() {
        let optionItems = this.state.users.map((destinataire) =>
            <option key={destinataire.id} value={destinataire.email}>{destinataire.name}</option>
        );

        let msg = null;
        if (this.state.message !== null) {
            msg = (<p className="alert alert-success">{this.state.message}</p>)
        }

        return (
            <div>
                <div>
                    {msg}
                    <h2 className="text-center">Envoyer une notification</h2>
                    <form>
                        <div>
                            <label>Destinataire de la notification :</label>
                            <br />
                            <br />
                            <select className="custom-select" onChange={this.onChangeEmail}>
                                {optionItems}
                            </select>
                        </div>
                        <br />
                        <br />
                        <label>Message de la notification :</label>
                        <br />
                        <br />
                        <div className="form-group">
                            <input type="text" placeholder="Message" className="form-control" name="texte" value={this.state.texte} onChange={this.onChange} />
                        </div>
                        <div className="form-group">
                            <button type="submit" className="btn btn-success" onClick={this.notificationHandler}>Envoyer notification</button>
                        </div>
                    </form>
                </div>
            </div>
        );
    }
}
export default NotificationForm;