package com.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.IUserDao;
import com.spring.exceptions.InvalidNameException;
import com.spring.model.User;

@Transactional
@Service("UserService")
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserDao userDao;

	@Override
	public List<User> findAllUsers() throws Exception {
		return userDao.findAllUsers();
	}

	@Override
	public User findUserById(Long id) throws Exception {
		return userDao.findUserById(id);
	}
	
	@Override
	public User findUserByUsername(String username) throws Exception {
		return userDao.findUserByUsername(username);
	}

	@Override
	public User createNewUser(User commingUser) throws Exception {
		
		String username = commingUser.getUsername();
		byte age = commingUser.getAge();
		
		// -- user validation
		
		if(!username.matches("[a-zA-Z]+") || username.length() > 25) {
			throw new InvalidNameException();
		}
		
		if (age > 110 || age < 10) {
			throw new Exception();
		}
		
		// send the User to the Dao
		
		return userDao.createNewUser(commingUser);
	}
	
	@Override
	public User updateUser(User commingUser, User oldUser) throws Exception {
		
		String username = commingUser.getUsername();
		byte age = commingUser.getAge();
		
		// -- user validation
		
		if(!username.matches("[a-zA-Z]+") || username.length() > 25) {
			throw new InvalidNameException();
		}
		
		if (age > 110 || age < 10) {
			throw new Exception();
		}
		
		// update validation
		
		// the username can't be changed
		if ( !(commingUser.getUsername().equalsIgnoreCase( oldUser.getUsername() )) ) {
			throw new Exception();
		}
		
		return userDao.updateUser(commingUser);
	}

	@Override
	public User deleteUser(Long id) throws Exception {
		return userDao.deleteUser(id);
	}

}
