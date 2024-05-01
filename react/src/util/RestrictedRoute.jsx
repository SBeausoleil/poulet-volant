import React from 'react';
import {
	Route,
	Redirect
} from "react-router-dom";


const RestrictedRoute = ({ component: Component, condition, user, ...rest }) => (
	<Route
		{...rest}
		render={props =>
			condition ? (
				<Component {...rest} {...Object.assign({}, props, user)} />
			) : (
					<Redirect
						to={{
							pathname: '/home',
							state: { from: props.location }
						}}
					/>
				)
		}
	/>
);

export default RestrictedRoute