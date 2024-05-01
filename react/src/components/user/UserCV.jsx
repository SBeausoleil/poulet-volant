import React, { Component } from 'react'
import ApiService from "../../service/ApiService";
import axios from 'axios';

class ListCVComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            files: [],
            notifications: [],
        }
        this.deleteDownload = this.deleteDownload.bind(this);
        this.reloadFileList = this.reloadFileList.bind(this);
        this.download = this.download.bind(this);
    }

    componentDidMount() {
        this.reloadFileList();
    }

    reloadFileList() {
        ApiService.fetchMyCv()
            .then((res) => {
                console.log(res.data.result);
                if (res.data.result)
                    this.setState({ files: [res.data.result] })
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

    deleteDownload(id) {
        ApiService.deleteDownload(id).then((res) => {
            this.props.history.push("/cv");
        })
    }

    deleteNotification(id) {
        axios.delete('http://localhost:1111/notifications/' + id).then((res) => {
            console.log(res);
            this.forceUpdate();
        })
    }

    download = (fileName) => {
        ApiService.downloadCv(fileName).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.download = fileName;
            link.click();
        });
    }



    render() {
        return (
            <div>
                <div>
                    <h2 className="text-center">Mon Curriculum Vitae</h2>
                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <th>Nom de fichier</th>
                                <th>Type de fichier</th>
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
                                            <td>{file.fileType}</td>
                                            <td>
                                                <button className="btn btn-success" onClick={() => this.deleteDownload(file.id)}>Supprimer</button>
                                            </td>
                                            <td>
                                                <button className="btn btn-success" onClick={() => this.download(file.fileName)} style={{ marginLeft: '20px' }}>Télécharger</button>
                                            </td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}
export default ListCVComponent;