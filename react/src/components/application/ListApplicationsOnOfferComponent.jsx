import React, { Component } from 'react';
import ApiService from '../../service/ApiService';
import Loader from 'react-loader-spinner';
import ApplicationEditionComponent from '../application/ApplicationEditionComponent';

class ListApplicationOnOfferComponent extends Component {

	constructor(props) {
		super(props);
		this.state = {
			applications: null
		}
		this.onApplicationUpdate = this.onApplicationUpdate.bind(this);
	}

	componentDidMount() {
		ApiService.fetchApplicationsForOffer(this.props.offerId)
			.then(response => {
				this.setState({ applications: response.data });
			});
	}

	onApplicationUpdate(application) {
		for (let i = 0; i < this.state.applications.length; i++) {
			if (this.state.applications[i].id === application.id) {
				this.state.applications[i] = application;
				this.forceUpdate();
			}
		}
	}

	render() {
		if (this.state.applications === null) {
			return (
				<div style={{width: "100%", height: "100", display: "flex", justifyContent: "center", alignItems: "center"}}>
                    <Loader type="ThreeDots" color="#2BAD60" height="100" width="100" />
                </div>
			)
		}

		else {
			return (
				<>
				<h2>Applications</h2>
				<table className="table">
					<thead>
						<tr>
							<th>Étudiant</th>
							<th>Statut</th>
							<th>Mise à jour</th>
						</tr>
					</thead>
					<tbody>
						{this.state.applications.map((value, index) => {
							return (
								<tr key={value.id}>
									<td>{value.studentName}</td>
									<td><ApplicationEditionComponent applicationId={value.id} onUpdate={this.onApplicationUpdate}/></td>
									<td>{value.updatedAt}</td>
								</tr>
							)
						})}
					</tbody>
				</table>
				</>
			)
		}
	}
}

export default ListApplicationOnOfferComponent;