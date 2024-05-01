import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import ApiService from '../../service/ApiService';

class UploadCVComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            file: '',
            error: '',
            msg: '',
            notifications: [],
        }
        this.uploadFile = this.uploadFile.bind(this);
        this.onFileChange = this.onFileChange.bind(this);
    }

    componentDidMount() {
        ApiService.fetchHasCv().then(response => {
            if (response.data) {
                this.props.history.push("/user/UserCV");
            }
        })
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


    uploadFile = (event) => {
        event.preventDefault();
        this.setState({ error: '', msg: '' });

        if (!this.state.file) {
            this.setState({ error: 'Veuillez téléverser un Curriculum Vitae.' })
            return;
        }

        const file_taille = 2000000;
        if (this.state.file.size >= file_taille) {
            this.setState({ error: 'Le fichier ne peut pas dépasser 2MB.' })
            return;
        }

        const type_fichier = ['application/pdf'];
        if (type_fichier.every(type => this.state.file.type !== type)) {
            this.setState({ error: 'Le format nest pas supporté' })
            return;
        }

        let data = new FormData();
        data.append('file', this.state.file);
        data.append('name', this.state.file.name);

        ApiService.uploadCv(data).then(
            response => {
                console.log(response);
                this.setState({ error: '', msg: 'Le Curriculum Vitae a été téléversé' });
                this.notificationHandler('Vous avez envoyé un curriculum vitae.');
                window.location.reload(false);
            }).catch(err => {
                if (err.response.status === 409)
                    this.setState({ error: "Erreur: Vous avez déjà téléversé un CV" });
                else
                    this.setState({ error: "Erreur: " + err.response.status });
            });


    }

    onFileChange = (event) => {
        this.setState({
            file: event.target.files[0]
        });
    }

    render() {
        return (
            <div className="App">
                <header className="page-header">
                    <h1 className="page-title">Téléverser un curriculum vitae</h1>
                </header>
                <br />
                <br />
                <div className="page-intro">
                    <h6 className="danger">{this.state.error}</h6>
                    <br />
                    <h6 className="success">{this.state.msg}</h6>
                    <br />
                    <h4>Étape 1</h4>
                    Choisir un curriculum vitae :   <input onChange={this.onFileChange} type="file" accept="application/pdf" />
                    <br />
                    <br />
                    <h4>Étape 2</h4>
                    <button onClick={this.uploadFile}>Téléverser le fichier</button>
                </div>
            </div>
        );
    }
}

export default UploadCVComponent;

