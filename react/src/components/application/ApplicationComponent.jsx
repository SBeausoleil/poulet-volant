import React, { Component } from 'react';
import ApiService from '../../service/ApiService';
import { STATUS_APPLIED, STATUS_CONVOKED, STATUS_ACCEPTED, STATUS_DECLINED } from '../../constants.js'
import ApplicationEditionComponent from './ApplicationEditionComponent';

class ApplicationComponent extends Component {

	constructor(props) {
		super(props);
		this.state = {
			application: null
		};
		this.handleApply = this.handleApply.bind(this);
		this.updateApplicationState = this.updateApplicationState.bind(this);
		this.onUpdate = this.onUpdate.bind(this);
		this.handleChange = this.handleChange.bind(this);
	}

	componentDidMount() {
		this.updateApplicationState();
	}

	onUpdate(application) {
		this.setState({ application: application });
	}

	updateApplicationState() {
		ApiService.fetchMyApplication(this.props.offerId)
			.then(response => {
				this.setState({ application: response.data });
			});
	}

	handleApply(e) {
		ApiService.handleApplyApplication(this.props.offerId).then((response) => {
			    console.log(response);
				this.updateApplicationState();

			}).catch(error => {
				console.log("L'application a échoué : " + error);
			})
	}


	async handleChange(e) {
		e.preventDefault();
		await ApiService.updateApplicationStatus(this.state.application.id, e.target.value)
			.then(response => {
				this.setState({ application: response.data });
			})
			.catch(error => {
				console.log("Erreur lors de la modification de l'application.");
				console.log(error);
			})
	}
	render() {
		if (this.state.application === null) {
			return (
				<button id="apply" ref="apply" className="btn btn-primary" onClick={this.handleApply}>Appliquer</button>
			)
		}
        let { status, updatedAt } = this.state.application;

        switch (status) {
            case STATUS_APPLIED:
                status = "Appliqué";
                break;
            case STATUS_CONVOKED:
                status = "Convoqué";
                break;
            case STATUS_ACCEPTED:
                status = "Accepté";
                break;
            case STATUS_DECLINED:
                status = "Décliné";
                break;
        }

		return (
			<div className="card" style={{ width: "24rem" }}>
				<div className="card-body">
					<h4 className="card-title">{status}</h4>
					<h6 className="card-subtitle text-muted">Dernière mise à jour: {updatedAt}</h6>
					<ApplicationEditionComponent applicationId={this.state.application.id} onUpdate={this.onUpdate} />
				</div>
			</div>
		)
	}
}

export default ApplicationComponent;
