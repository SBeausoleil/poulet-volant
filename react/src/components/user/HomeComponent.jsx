import React, { Component } from 'react';
import { getCurrentUser } from '../../util/auth';

class HomeComponent extends Component {

	constructor(props) {
		super(props);
		this.state = {
			user: null
		}
		this.loadUser = this.loadUser.bind(this);
	}

	componentDidMount() {
		this.loadUser();
	}

	loadUser() {
		getCurrentUser().then(
			response => {
				console.log("Réception de l'utilisateur");
				this.setState({ user: response.data });
			}
		).catch(
			error => {
				console.log(error);
			}
		);
	}

	render() {
		let firstName = null;
		let lastName = null;
		if (this.state.user) {
			firstName = this.state.user.firstName;
			lastName = this.state.user.lastName;
		}
		return (
			<div>
				<h1>Bienvenue {firstName} {lastName} !</h1>
			</div>
		);
	}
}

export default HomeComponent