import React, { Component } from 'react';
import axios from 'axios';
import { API_BASE_URL, USER_STORAGE } from '../../constants.js'
import ApplicationComponent from '../application/ApplicationComponent';
import { canApply, canSeeApplicationsOnOffer } from '../../util/policy.jsx';
import ListApplicationOnOfferComponent from '../application/ListApplicationsOnOfferComponent.jsx';

class DisplayOfferComponent extends Component {

	constructor(props) {
		super(props);
		this.state = {
			offer: null,
			organization: null,
			holder: null,
			address: null
		}
		this.user = JSON.parse(sessionStorage.getItem(USER_STORAGE));
	}

	componentDidMount() {
		axios.get(API_BASE_URL + "/offer/" + this.props.offerId)
			.then(response => {
				this.setState({
					offer: response.data,
					organization: response.data.organization,
					holder: response.data.organization.holder,
					address: response.data.address
				});
			}).catch(error => {
				console.log(error);
			});
	}

    render() {
        if (this.state.offer === null) {
            return (
                <div>
                    <div className="loader"></div>
                </div>
                )
        }

		let { offer, organization, holder, address } = this.state;

		let bottomSection;
		if (canApply(this.user)) {
			bottomSection = (
				<>
					<p>Des questions? Contactez {holder.name} par <a href={"mailto:" + holder.email}>courriel</a>.</p>
					<ApplicationComponent offerId={this.props.offerId} />
				</>
			);
		} else if (canSeeApplicationsOnOffer(this.user)) {
			bottomSection = <ListApplicationOnOfferComponent offerId={this.props.offerId}/>
		}

		return (
			<>
				<div className="container">
					<h1>{offer.title}</h1>
					<h2>{organization.name}</h2>
					<p>{address.streetAddress}, {address.city}, {address.province}, {address.postalCode}, {address.country}</p>
					<p className="text-justify">{offer.description}</p>
					<h6>Description de l'organisation:</h6>
					<p className="text-justify">{organization.description}</p>
					{bottomSection}
				</div>
			</>
		)
	}
}

export default DisplayOfferComponent;
