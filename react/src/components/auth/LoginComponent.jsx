import React, { Component } from 'react';
import { ACCESS_TOKEN } from '../../constants';
import { Form, Input, Button } from 'antd';
import ApiService from '../../service/ApiService';

class LoginComponent extends Component {

    render() {
        const AntWrappedLoginForm = Form.create()(LoginForm)
        return (
            <div className="login-container">
                <h1 className="page-title">Connection</h1>
                <div className="login-content">
                    <AntWrappedLoginForm onLogin={this.props.onLogin} />
                </div>
            </div>
        );
    }
}

class LoginForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            message: null
        }
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                ApiService.submitLogin(values.email, values.password).then(response => {
                    localStorage.setItem(ACCESS_TOKEN, response.data.accessToken);
                    this.props.onLogin();
                }).catch(error => {
                    if (error.response.status === 401) {
                        this.setState({ message: "Votre nom dutilisateur ou mot de passe est incorrect. Veuillez réessayer." });
                    } else if (error.response.status === 400) {
                        this.setState({ message: "Vous devez entrer une adresse courriel valide." });
                    } else {
                        this.setState({ message: "Désolé, une erreur s'est produite." });
                    }
                });
            }
        });
    }

    render() {
        const { getFieldDecorator } = this.props.form;
        let msg = '';
        if (this.state.message !== null)
            msg = <p className="alert alert-danger">{this.state.message}</p>;
        return (
            <>
                {msg}
                <Form onSubmit={this.handleSubmit} className="login-form">
                    <div className="form-group">
                        <Form.Item>
                            {getFieldDecorator('email', {
                                rules: [{ required: true, message: 'Veuillez entrer votre adresse courriel.' }],
                            })(
                                <Input type="text" className="form-control" placeholder="Adresse courriel" />,
                            )}
                        </Form.Item>
                    </div>
                    <div className="form-group">
                        <Form.Item>
                            {getFieldDecorator('password', {
                                rules: [{ required: true, message: 'Veuillez entrer votre mot de passe.' }],
                            })(
                                <Input className="form-control" type="password" placeholder="Mot de passe" />,
                            )}
                        </Form.Item>
                    </div>
                    <div className="form-group">
                        <Button className="btn btn-primary" htmlType="submit">Se connecter</Button>
                    </div>
                </Form >
            </>
        );
    }
}

export default LoginComponent