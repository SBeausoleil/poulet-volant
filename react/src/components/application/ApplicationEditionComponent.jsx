import React, { Component } from 'react';
import ApiService from '../../service/ApiService';
import Loader from 'react-loader-spinner';
import { STATUS_APPLIED, STATUS_CONVOKED, STATUS_ACCEPTED, STATUS_DECLINED } from '../../constants.js'

class ApplicationEditionComponent extends Component {

  constructor(props) {
    super(props);
    this.state = {
      application: null
    }
    this.handleChange = this.handleChange.bind(this);
  }

  componentDidMount() {
    ApiService.fetchApplication(this.props.applicationId)
      .then((response) => {
        this.setState({ application: response.data });
      });
  }

  async handleChange(e) {
    e.preventDefault();
    await ApiService.updateApplicationStatus(this.state.application.id, e.target.value)
      .then((response) => {
        this.setState({ application: response.data });
        if (this.props.onUpdate) {
          this.props.onUpdate(this.state.application);
        }
      })
      .catch(error => {
        console.log("Erreur lors de la modification de l'application : " + error);
      })
  }

  render() {
    if (this.state.application === null) {
      return (
        <div style={{ width: "100%", height: "100", display: "flex", justifyContent: "center", alignItems: "center" }}>
          <Loader type="ThreeDots" color="#2BAD60" height="100" width="100" />
        </div>
      )
    }
    let { status } = this.state.application;
    return (
      <form>
        <div className="btn-group" data-toggle="buttons">
          <button className={"btn " + (status === STATUS_APPLIED ? 'btn-primary' : 'btn-secondary')} value={STATUS_APPLIED} onClick={this.handleChange}>Appliqué</button>
          <button className={"btn " + (status === STATUS_CONVOKED ? 'btn-primary' : 'btn-secondary')} value={STATUS_CONVOKED} onClick={this.handleChange}>Convoqué</button>
          <button className={"btn " + (status === STATUS_ACCEPTED ? 'btn-success' : 'btn-secondary')} value={STATUS_ACCEPTED} onClick={this.handleChange}>Accepté</button>
          <button className={"btn " + (status === STATUS_DECLINED ? 'btn-danger' : 'btn-secondary')} value={STATUS_DECLINED} onClick={this.handleChange}>Décliné</button>
        </div>
      </form>
    )
  }
}

export default ApplicationEditionComponent;
