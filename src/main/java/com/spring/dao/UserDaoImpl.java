package com.spring.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.spring.exceptions.DuplicatedNameException;
import com.spring.model.User;

@Repository
public class UserDaoImpl implements IUserDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() throws Exception {
		
		return (List<User>) entityManager.createQuery("from User").getResultList();
	}

	@Override
	public User findUserById(Long id) {
		
		String hql = "from User where id=:id";
		Query query = entityManager.createQuery(hql).setParameter("id", id);
		
		User user = (User) query.getSingleResult();
		
		return user;
	}
	
	@Override
	public User findUserByUsername(String username) throws Exception {
		
		String hql = "from User where username=:username";
		Query query = entityManager.createQuery(hql).setParameter("username", username);
		
		User user = (User) query.getSingleResult();
		
		return user;
	}

	@Override
	public User createNewUser(User commingUser) throws Exception {
		
		List<User> users = this.findAllUsers();
		
		for (User user:users) {
			// if the there is a user with the same username
			if (user.getUsername().equalsIgnoreCase(commingUser.getUsername())) {
				throw new DuplicatedNameException();
			}
		}
		entityManager.persist(commingUser);
		return commingUser;
	}

	@Override
	public User updateUser(User commingUser) throws Exception {

		entityManager.merge(commingUser);
		entityManager.flush();
		
		User updatedUser = this.findUserById(commingUser.getId());
		return updatedUser;
	}

	@Override
	public User deleteUser(Long id) throws Exception {
		
		User UserToDelete = this.findUserById(id);
		
		String hql = "delete from User where id=:id";
		Query query = entityManager.createQuery(hql).setParameter("id", id);
		
		query.executeUpdate();
		
		return UserToDelete;
	}
	
}
