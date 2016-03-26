package com.sdi.persistence;

import com.sdi.model.User;
import com.sdi.persistence.util.GenericDAO;

public interface UserDao extends GenericDAO<User, Long> {

	User findByLogin(String login);

}
