package com.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.user.UserDao;
import com.entity.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public String find() { 
		userDao.findById();
		return "hello";
	}
	@Override
	public boolean addUser(User user){
		return userDao.addUser(user) == 1 ;
	}

}