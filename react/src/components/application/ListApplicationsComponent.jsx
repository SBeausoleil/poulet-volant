import React, { Component } from 'react';
import ApiService from '../../service/ApiService';
import Loader from 'react-loader-spinner';
import ApplicationEditionComponent from './ApplicationEditionComponent';

class ListApplicationComponent extends Component {

	constructor(props) {
		super(props);
		this.state = {
			applications: null
		}
		this.onApplicationUpdate = this.onApplicationUpdate.bind(this);
	}

	componentDidMount() {
		ApiService.fetchAllApplications()
			.then((response) => {
				this.setState({ applications: response.data });
			});
	}

	onApplicationUpdate(application) {
		let hasChanged = false;
		for (let i = 0; i < this.state.applications.length; i++) {
			if (this.state.applications[i].id === application.id) {
				this.state.applications[i] = application;
				hasChanged = true;
			}
		}
		if (hasChanged) {
			this.forceUpdate();
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

		return (
			<div className="container">
				<h1>Applications</h1>
				<table className="table">
					<thead>
						<tr>
							<th>Étudiant</th>
							<th>Offre</th>
							<th>Statut</th>
							<th>Mise à jour</th>
						</tr>
					</thead>
					<tbody>
						{this.state.applications.map((value, index) => {
							return (
								<tr key={value.id}>
									<td>{value.studentName}</td>
									<td>{value.offerTitle}</td>
									<td><ApplicationEditionComponent applicationId={value.id} onUpdate={this.onApplicationUpdate} /></td>
									<td>{value.updatedAt}</td>
								</tr>
							)
						})}
					</tbody>
				</table>
			</div>
		)
	}
}

export default ListApplicationComponent;