import React, { Component } from 'react'
import ApiService from "../../service/ApiService";

class CreationFormulaireInternshipManager extends Component {

    constructor(props) {
        super(props);
        this.state = {
            file: '',
            error: '',
            msg: ''
        }
        this.downloadFormulaire = this.downloadFormulaire.bind(this);
        this.notificationHandler = this.notificationHandler.bind(this);
        this.uploadFile = this.uploadFile.bind(this);

    }

    notificationHandler(message) {
        let notifications = {
            texte: message
        };
        ApiService.addNotification(notifications)
            .then(res => {
                console.log(res.data);
            });
    }

    downloadFormulaire = (fileName) => {
        ApiService.downloadFormulaire(fileName).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', fileName);
            link.click();
            window.URL.revokeObjectURL(url);
        });
        this.notificationHandler('Vous avez téléchargé un formulaire.');
    }

    uploadFile = (event) => {
        event.preventDefault();
        this.setState({ error: '', msg: '' });

        if (!this.state.file) {
            this.setState({ error: 'Veuillez téléverser un formulaire.' })
            return;
        }

        const file_taille = 2000000;
        if (this.state.file.size >= file_taille) {
            this.setState({ error: 'Le fichier ne peut pas dépasser 2MB.' })
            return;
        }

        const type_fichier = ['application/pdf'];
        if (type_fichier.every(type => this.state.file.type !== type)) {
            this.setState({ error: 'Le format du fichier nest pas supporté' })
            return;
        }

        let data = new FormData();
        data.append('file', this.state.file);
        data.append('name', this.state.file.name);

        ApiService.uploadFormulaire(data).then((response) => {
            console.log(response);
            this.setState({ error: '', msg: 'Le formulaire a été téléversé' });
            this.notificationHandler('Vous avez téléversé un formulaire.');
            window.location.reload(false);
        }).catch(err => {
            this.setState({ error: err });
        });
    }

    onFileChange = (event) => {
        this.setState({
            file: event.target.files[0]
        });
    }



    render() {
        return (
            <div>
                <div>
                    <div className="App">
                        <header className="page-header">
                            <h2 className="page-title">Créer un contrat de stage</h2>
                        </header>
                        <div>
                            <h4>Étape 1</h4>
                        </div>
                        <div>
                            <a href="/formulaire/contrat_stage.pdf" target="_blank" download>Télécharger un contrat de stage vide</a>
                        </div>
                        <br />
                        <div>
                            <h4>Étape 2</h4>
                        </div>
                        <div className="page-intro">
                            <h4 className="danger">{this.state.error}</h4>
                            <h4 className="success">{this.state.msg}</h4>
                            Choisir un contrat d'embauche :   <input onChange={this.onFileChange} type="file" />
                            <br />
                            <br />
                            <div>
                                <h4>Étape 3</h4>
                            </div>
                            <button onClick={this.uploadFile}>Téléverser le contrat d'embauche</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
export default CreationFormulaireInternshipManager;