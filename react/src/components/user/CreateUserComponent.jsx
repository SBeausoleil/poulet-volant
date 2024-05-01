import React, { Component } from 'react';
import { TYPE_STUDENT, TYPE_EMPLOYER } from '../../constants';
import { Form, Input, Button, Select } from 'antd';
import ApiService from '../../service/ApiService';
const { } = Select;

class CreateUserComponent extends Component {

    render() {
        const RegistrationForm = Form.create()(RegisterForm)
        return (
            <div className="container">
                <h1>Enregistrement d'un utilisateur</h1>
                <RegistrationForm history={this.props.history} />
            </div>
        )
    }
}

class RegisterForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            confirmDirty: false,
            users: [],
            email: '',
            firstName: '',
            lastName: '',
            password: '',
            phoneNumber: '',
            userType: TYPE_STUDENT,
            message: null
        }
    }

    handleSubmit = (event) => {
        event.preventDefault();
        this.props.form.validateFields((err, values) => {
            let users = {
                email: this.state.email,
                firstName: this.state.firstName,
                lastName: this.state.lastName,
                password: this.state.password,
                phoneNumber: this.state.phoneNumber,
                userType: this.state.userType
            };
            ApiService.createUser(users).then(response => {
                console.log(response);
                this.props.history.push("/login");
            }).catch(error => {
                console.log(error)
                if (error.response.status === 400) {
                    this.setState({ message: 'Votre adresse courriel est déjà utilisé dans le site.' });
                } else {
                    this.setState({ message: "Désolé! Une erreur s'est produit, lors de la connection." });
                }
            });
        });
    }

    handleConfirmBlur = e => {
        const { value } = e.target;
        this.setState({ confirmDirty: this.state.confirmDirty || !!value });
    };

    validateToPassword = (rule, value, callback) => {
        const { form } = this.props;
        if (value && value !== form.getFieldValue('password')) {
            callback('Les mots de passe que vous avez entrés sont inexistants !');
        } else {
            callback();
        }
    };

    validateToConfirmation = (rule, value, callback) => {
        const { form } = this.props;
        if (value && this.state.confirmDirty) {
            form.validateFields(['confirm'], { force: true });
        }
        callback();
    };

    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }

    render() {
        const { getFieldDecorator } = this.props.form;
        let msg = null;
        if (this.state.message !== null)
            msg = (<p className="alert alert-danger">{this.state.message}</p>)

    return(
<>
    { msg }
    < Form onSubmit = { this.handleSubmit } >
        <div className="form-group">
            <Form.Item>
                {getFieldDecorator('firstName', {
                    rules: [{ required: true, message: 'Veuillez entrez votre prénom.' }],
                })(
                    <Input className="form-control" type="text" placeholder="Prénom" name="firstName" value={this.state.firstName} onChange={this.onChange} />,
                )}
            </Form.Item>
        </div>
        <div className="form-group">
            <Form.Item>
                {getFieldDecorator('lastName', {
                    rules: [{ required: true, message: 'Veuillez entrez votre nom.' }],
                })(
                    <Input className="form-control" type="text" placeholder="Nom" name="lastName" value={this.state.lastName} onChange={this.onChange} />,
                )}
            </Form.Item>
        </div>
        <div className="form-group">
            <Form.Item>
                {getFieldDecorator('email', {
                    rules: [{ required: true, type: 'email', message: 'Veuillez entrez votre adresse courriel.' }],
                })(
                    <Input type="text" className="form-control" placeholder="Adresse courriel" name="email" value={this.state.email} onChange={this.onChange} />,
                )}
            </Form.Item>
        </div>
        <div className="form-group">
            <Form.Item>
                {getFieldDecorator('phoneNumber', {
                    rules: [{ required: true, message: 'Veuillez entrez votre numéro de téléphone.' }],
                })(
                    <Input type="text" className="form-control" placeholder="Numéro de téléphone" name="phoneNumber" value={this.state.phoneNumber} onChange={this.onChange} />,
                )}
            </Form.Item>
        </div>
        <div className="form-group">
            <Form.Item hasFeedback>
                {getFieldDecorator('password', {
                    rules: [{ required: true, message: 'Veuillez entrez votre mot de passe.', validator: this.validateToConfirmation }],
                })(
                    <Input type="password" className="form-control" placeholder="Mot de passe" name="password" value={this.state.password} onChange={this.onChange} />,
                )}
            </Form.Item>
        </div>
        <div className="form-group">
            <Form.Item hasFeedback>
                {getFieldDecorator('confirm', {
                    rules: [{ required: true, message: 'Veuillez confirmer votre mot de passe.', validator: this.validateToPassword }],
                })(
                    <Input type="password" className="form-control" placeholder="Confirmer mot de passe" onBlur={this.handleConfirmBlur} />,
                )}
            </Form.Item>
        </div>
        <div className="form-group">
            <Form.Item label="Vous êtes:">
                {getFieldDecorator('userType', {
                    rules: [{ required: true, message: "Veuillez choisir votre type d'utilisateur." }],
                })(
                    <select name="userType" className="custom-select" value={this.state.userType} onChange={this.onChange} required>
                        <option value={TYPE_STUDENT} selected>Étudiant</option>
                        <option value={TYPE_EMPLOYER}>Employeur</option>
                    </select>
                )}
            </Form.Item>
        </div>
        <div className="form-group">
            <Button className="btn btn-primary" htmlType="submit">S'enregistrer</Button>
        </div>
    </Form >
</>
		)
	}
}



export default CreateUserComponent;
