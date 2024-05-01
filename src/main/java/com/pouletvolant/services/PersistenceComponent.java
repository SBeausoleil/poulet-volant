package com.pouletvolant.services;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersistenceComponent {

	@Autowired
	private EntityManagerFactory managerFactory;

	private SessionFactory sessionFactory;

	public SessionFactory sessionFactory() {
		if (sessionFactory == null) {
			if (managerFactory.unwrap(SessionFactory.class) == null) {
				throw new NullPointerException("factory is not a hibernate factory");
			}
			sessionFactory = managerFactory.unwrap(SessionFactory.class);
		}
		return sessionFactory;
	}

}
