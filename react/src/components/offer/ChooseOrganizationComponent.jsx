import React, { Component } from 'react';
import Select from 'react-select';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import axios from "axios";

class ChooseOrganizationComponent extends Component {
    state = {
        organizations: [],
        open: false
    }

    componentDidMount() {
        this.getOrganizations();
    }

    getOrganizations = () => {
        axios({
            method: "GET",
            url: "/api/organization/getAll",
            header: "application/json"
        }).then(response => {
            this.setState({ organizations: response.data })
        }).catch(error => {
            console.log(error)
        });
    }

    async onSubmit(event) {
        await this.props.createOrganisation(event)
        this.getOrganizations();
    }

    onOrganizationChange = (select) => {
        this.props.setIdOrganization(select.value)
    }

    render() {
        let options = [{ label: "", value: "" }, ...(this.state.organizations.map(file => ({ label: file.name, value: file.id })))];
        return (
            <div style={{ paddingBottom: '30px' }}>
                <h6>Choisissez une organisation</h6>
                <Select name="organizations" defaultValue={options[0]} options={options} onChange={this.onOrganizationChange} />

                <h6>ou créez l'organisation</h6>

                <Button variant="warning" onClick={() => this.setState({ open: true })}>
                    Créer un organisation
                </Button>

                <Modal
                    show={this.state.open}
                    onHide={() => this.setState({ open: false })}
                    dialogClassName="modal-90w"
                    size="lg"
                    aria-labelledby="example-custom-modal-styling-title"
                >
                    <Modal.Header closeButton>
                        <Modal.Title id="example-custom-modal-styling-title">
                            Créer un organisation
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form onSubmit={this.onSubmit.bind(this)}>
                            <Form.Group>
                                <Form.Label>Nom de l'organisation</Form.Label>
                                <Form.Control name="nameOrganization" onChange={this.props.onChange} placeholder="Nom de l'organisation" required />
                            </Form.Group>

                            <Form.Group>
                                <Form.Label>Description de l'organisation</Form.Label>
                                <Form.Control name="descriptionOrganization" onChange={this.props.onChange} placeholder="Description de l'organisation" required />
                            </Form.Group>

                            <hr id="ligne" />
                            <h5 id="modal">Information de la personne ressource</h5>

                            <Form.Group>
                                <Form.Label>Nom de la personne ressource</Form.Label>
                                <Form.Control name="namePerson" onChange={this.props.onChange} placeholder="Nom de la personne ressource" required />
                            </Form.Group>

                            <Form.Group>
                                <Form.Label>Numéro de téléphone de la personne ressource</Form.Label>
                                <Form.Control name="phonePerson" onChange={this.props.onChange} placeholder="Numéro de téléphone de la personne ressource" required />
                            </Form.Group>

                            <Form.Group>
                                <Form.Label>Courriel de la personne ressource</Form.Label>
                                <Form.Control name="emailPerson" onChange={this.props.onChange} placeholder="Courriel de la personne ressource" required />
                            </Form.Group>

                            <Button variant="primary" type="submit" onClick={() => this.setState({ open: false })}>
                                Enregistrer
                            </Button>
                        </Form>
                    </Modal.Body>
                </Modal>

            </div>

        )
    }
}

export default ChooseOrganizationComponent;
