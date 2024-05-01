import React, { Component } from 'react'
import ApiService from "../../service/ApiService";

class GestionFormulaireInternshipManager extends Component {

    constructor(props) {
        super(props);
        this.state = {
            files: [],
            file: '',
            error: '',
            msg: ''
        }
        this.deleteFormulaire = this.deleteFormulaire.bind(this);
        this.reloadFileList = this.reloadFileList.bind(this);
        this.downloadFormulaire = this.downloadFormulaire.bind(this);
        this.notificationHandler = this.notificationHandler.bind(this);

    }

    componentDidMount() {
        this.reloadFileList();
    }

    reloadFileList() {
        ApiService.fetchFormulaire()
            .then((res) => {
                console.log(res.data.result);
                this.setState({ files: res.data.result })
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

    deleteFormulaire(id) {
        ApiService.deleteFormulaire(id).then((res) => {
            console.log(res);
        })
        this.notificationHandler('Vous avez supprimé un formulaire.');
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

    onFileChange = (event) => {
        this.setState({
            file: event.target.files[0]
        });
    }



    render() {
        return (
            <div>
                <div>
                    <h2>Gestion des formulaires</h2>
                </div>
                <br />
                <br />
                <div>
                    <h3 className="text-center">Liste des formulaires</h3>
                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <th>Nom du formulaire</th>
                                <th>Type du formulaire</th>
                                <th>Statut du formulaire</th>
                                <th>Genre de formulaire</th>
                                <th>Supprimer</th>
                                <th>Télécharger</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.files.map(
                                    file =>
                                        <tr key={file.id}>
                                            <td>{file.fileName}</td>
                                            <td>{file.formulaire_type}</td>
                                            <td>{file.statutFormulaire}</td>
                                            <td>{file.genreFormulaire}</td>
                                            <td>
                                                <button className="btn btn-success" onClick={() => this.deleteFormulaire(file.id)}> Supprimer formulaire </button>
                                            </td>
                                            <td>
                                                <button className="btn btn-success" onClick={() => this.downloadFormulaire(file.fileName)} style={{ marginLeft: '20px' }}> Télécharger formulaire </button>
                                            </td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
                <br />
                <br />
                <div>
                    <h3>Envoyer par email un formulaire</h3>
                </div>
                <br />
                <div>
                    <a href="mailto:example@gmail.com">Envoyer un email</a>
                </div>
            </div>
        );
    }
}
export default GestionFormulaireInternshipManager;