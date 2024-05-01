import React, { Component } from 'react';
import { Form, Input, Button } from 'antd';
import TextArea from 'antd/lib/input/TextArea';
import ApiService from '../../service/ApiService.js';

class CreateOrganizationComponent extends Component {

	componentDidMount() {
		ApiService.hasOneOrganization().then(
			response => {
				console.log(response)
			}
		).catch(
			error => {
				console.log(error)
			}
		)
	}

	render() {
		const OrganizationForm = Form.create()(CreateOrganizationForm);
		return (
			<div className="container">
				<h1>Enregistrement d'une organisation</h1>
				<OrganizationForm history={this.props.history} />
			</div>
		)
	}
}

class CreateOrganizationForm extends Component {

	handleSubmit = (event) => {
		event.preventDefault();
		this.props.form.validateFields((err, values) => {
			if (!err) {
				ApiService.addOrganization(values.name, values.description).then(response => {
					console.log(response);
					this.props.history.push("/home");
					this.notificationHandler('Vous avez créé avec succès une nouvelle organisation.');
				}).catch(error => {
					console.log(error)
				});
			}
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

	render() {
		const { getFieldDecorator } = this.props.form;
		return (
			<Form onSubmit={this.handleSubmit}>
				<div className="form-group">
					<Form.Item>
						{getFieldDecorator('name', {
							rules: [{ required: true, message: 'Veuillez entrez le nom de votre organisation.' }],
						})(
							<Input type="text" className="form-control" placeholder="Nom" />,
						)}
					</Form.Item>
				</div>
				<div className="form-group">
					<Form.Item>
						{getFieldDecorator('description', {
							rules: [{ required: true, message: 'Veuillez entrez une description de votre organisation.' }],
						})(
							<TextArea className="form-control" placeholder="Description" />,
						)}
					</Form.Item>
				</div>
				<div className="form-group">
					<Button className="btn btn-primary" htmlType="submit">Enregistrer</Button>
				</div>
			</Form>
		)
	}
}

export default CreateOrganizationComponent;