import React, { Component } from 'react';
import { Form, Input, Button } from 'antd';
import { Link } from 'react-router-dom';
import ApiService from '../../service/ApiService';

class UserProfil extends Component {

    render() {
        const InformationModification = Form.create()(ChangeForm)
        return (
            <div className="login-container" id="test">
                <h1 className="page-title">Modification de vos informations</h1>
                <div className="login-content">
                    <InformationModification />
                </div>
            </div>
        );
    }
}

class ChangeForm extends Component {

    constructor(props) {
        super(props);
        this.state = this.initState
    }

    get initState() {
        return {
            password: "",
            confirmPassword: "",
            email: "",
            confirmEmail: "",
            firstName: "",
            lastName: "",
            phoneNumber: "",
            userEmail: "",
            userType: ""
        };
    }

    componentDidMount() {

        ApiService.fetchInfoUser().then(response => {
            const [userFirstName, userLastName, userEmail, userPhoneNumber, userType] = response.data.split(',')
            this.setState({ firstName: userFirstName, lastName: userLastName, phoneNumber: userPhoneNumber, userEmail: userEmail, userType: userType })
        })
            .catch(error => {
                console.log(error)
            });
    }

    changeData = (event) => {
        let model = Object.create(this.state);
        model[event.target.name] = event.target.value
        this.setState(model);
    }

    changeInformationEmail = (event) => {
        event.preventDefault()
        if (this.state.email === "") {
            return;
        }
        if (this.state.email !== this.state.confirmEmail) {
            alert("Il y a un problème avec votre courriel.");
            return;
        }

        ApiService.changeUserMail(this.state).then(response => {
            if (response.data === "Modified") {
                window.location.reload();
            } else if (response.data === "Vide") {
                alert("Veuillez entrer des données!");
            } else if (response.data === "Exist") {
                alert("Le courriel est déjà utilisé.");
            } else {
                alert("Il y a eu une erreur. Veuillez réessayer.");
            }
            console.log(response)
        })
            .catch(error => {
                console.log(error)
            });
    }

    changeInformationMDP = (event) => {
        event.preventDefault()
        if (this.state.password === "") {
            return;
        }

        if (this.state.password !== this.state.confirmPassword) {
            alert("Il y a un problème avec votre mot de passe");
            return;
        }

        ApiService.changeUserMDP(this.state).then(response => {
            if (response.data === "Modified") {
                window.location.reload();
            } else if (response.data === "Vide") {
                alert("Veuillez entrer des données!");
            } else {
                alert("Il y a eu une erreur. Veuillez réessayer.");
            }
        })
            .catch(error => {
                console.log(error)
            });
    }

    changeInformationPersonnalData = (event) => {
        event.preventDefault()
        if (this.state.firstName === "" || this.state.lastName === "" || this.state.phoneNumber === "") {
            alert("Veuillez remplir tout les champs.");
            return;
        }

        ApiService.changeUserPersonnalData(this.state).then(response => {
            if (response.data === "Modified") {
                window.location.reload();
            } else if (response.data === "Vide") {
                alert("Veuillez entrer des données!");
            } else {
                alert("Il y a eu une erreur. Veuillez réessayer.");
            }
        })
            .catch(error => {
                console.log(error)
            });
    }

    render() {
        const { getFieldDecorator } = this.props.form;
        return (
            <div>
                <div style={{ border: '2px solid white', 'borderRadius': '25px', 'paddingTop': '20px' }}>
                    <h3>Modification de l'adresse courriel</h3>
                    <Form onSubmit={this.changeInformationEmail} className="login-form">
                        <div className="form-group">
                            <Form.Item>
                                Ancienne adresse courriel:
                                {getFieldDecorator('AncienCourriel', {
                                    initialValue: this.state.userEmail,
                                    rules: [{ required: true, message: 'Veuillez entrer l\'ancien courriel.' }],
                                })(
                                    <Input readOnly type="text" className="form-control" placeholder="Entrez l'ancienne adresse courriel" />,
                                )}
                            </Form.Item>
                        </div>
                        <div className="form-group">
                            <Form.Item>
                                Nouvelle adresse courriel:
                                {getFieldDecorator('NouveauCourriel', {
                                    rules: [{ required: true, message: 'Veuillez entrer la nouvelle adresse courriel.' }],
                                })(
                                    <Input type="text" name="email" className="form-control" onChange={this.changeData} placeholder="Entrez la nouvelle adresse courriel" />,
                                )}
                            </Form.Item>
                        </div>
                        <div className="form-group">
                            <Form.Item>
                                Confirmer la nouvelle adresse courriel:
                                {getFieldDecorator('ConfirmerNouveauCourriel', {
                                    rules: [{ required: true, message: 'Veuillez confirmer la nouvelle adressse courriel.' }],
                                })(
                                    <Input type="text" name="confirmEmail" className="form-control" onChange={this.changeData} placeholder="Confirmer la nouvelle adresse courriel" />,
                                )}
                            </Form.Item>
                        </div>
                        <div className="form-group">
                            <Button className="btn btn-primary" htmlType="submit">Modifier</Button>
                        </div>
                    </Form >
                </div><br /><br /><br />

                <div style={{ border: '2px solid white', 'borderRadius': '25px', 'paddingTop': '20px' }}>
                    <h3>Modification du mot de passe</h3>
                    <Form onSubmit={this.changeInformationMDP} className="login-form">
                        <div className="form-group">
                            <Form.Item>
                                Nouveau mot de passe:
                                {getFieldDecorator('NouveauMotDePasse', {
                                    rules: [{ required: true, message: 'Veuillez entrer le nouveau mot de passe.' }],
                                })(
                                    <Input type="password" name="password" className="form-control" onChange={this.changeData} placeholder="Entrez le mot de passe" />,
                                )}
                            </Form.Item>
                        </div>
                        <div className="form-group">
                            <Form.Item>
                                Confirmer le nouveau mot de passe:
                                {getFieldDecorator('ConfirmerNouveauMotDePasse', {
                                    rules: [{ required: true, message: 'Veuillez confirmer le nouveau mot de passe.' }],
                                })(
                                    <Input type="password" name="confirmPassword" className="form-control" onChange={this.changeData} placeholder="Confirmer le mot de passe" />,
                                )}
                            </Form.Item>
                        </div>
                        <div className="form-group">
                            <Button className="btn btn-primary" htmlType="submit">Modifier</Button>
                        </div>
                    </Form >
                </div><br /><br /><br />

                <div style={{ border: '2px solid white', 'borderRadius': '25px', 'paddingTop': '20px' }}>
                    <h3>Modification de vos renseignements</h3>
                    <Form onSubmit={this.changeInformationPersonnalData} className="login-form">
                        <div className="form-group">
                            <Form.Item>
                                Prénom:
                                {getFieldDecorator('Prenom', {
                                    initialValue: this.state.firstName,
                                    rules: [{ required: true, message: 'Veuillez entrer votre prenom.' }],
                                })(
                                    <Input type="text" name="firstName" className="form-control" onChange={this.changeData} placeholder="Entrez votre prenom" />,
                                )}
                            </Form.Item>
                        </div>
                        <div className="form-group">
                            <Form.Item>
                                Nom de famille:
                                {getFieldDecorator('NomDeFamille', {
                                    initialValue: this.state.lastName,
                                    rules: [{ required: true, message: 'Veuillez entrer votre nom de famille.' }],
                                })(
                                    <Input type="text" name="lastName" className="form-control" onChange={this.changeData} placeholder="Entrez votre nom de famille" />,
                                )}
                            </Form.Item>
                        </div>
                        <div className="form-group">
                            <Form.Item>
                                Numéro de téléphone:
                                {getFieldDecorator('NumeroDeTelephone', {
                                    initialValue: this.state.phoneNumber,
                                    rules: [{ required: true, message: 'Veuillez entrer votre numero de telephone.' }],
                                })(
                                    <Input type="text" name="phoneNumber" className="form-control" onChange={this.changeData} placeholder="Entrez votre numero de telephone" />,
                                )}
                            </Form.Item>
                        </div>
                        <div className="form-group">
                            <Button className="btn btn-primary" htmlType="submit">Modifier</Button>
                        </div>
                    </Form >
                </div><br /><br /><br />

            </div>
        );
    }
}

export default UserProfil;