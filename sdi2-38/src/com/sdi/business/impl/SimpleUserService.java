package com.sdi.business.impl;

import com.sdi.business.UserService;
import com.sdi.business.impl.classes.user.UserBuscar;
import com.sdi.model.User;

public class SimpleUserService implements UserService {

	@Override
	public User findById(Long id) {
		return new UserBuscar().findById(id);
	}

}
