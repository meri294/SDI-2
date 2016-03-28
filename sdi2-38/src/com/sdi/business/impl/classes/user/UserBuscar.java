package com.sdi.business.impl.classes.user;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

public class UserBuscar {

	public User findById(Long id) {
		return Factories.persistence.createUserDao().findById(id);
	}

}
