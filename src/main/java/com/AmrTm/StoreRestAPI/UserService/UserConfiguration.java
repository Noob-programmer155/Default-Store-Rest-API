package com.AmrTm.StoreRestAPI.UserService;

import java.util.NavigableSet;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.AmrTm.StoreRestAPI.Entity.User;
import com.AmrTm.StoreRestAPI.ExceptionController.UsernameAlreadyExistException;
import com.AmrTm.StoreRestAPI.LogDataBase.LogData;

/**
 * User service configuration
 * @author Amar
 * */

@Service
public class UserConfiguration {
	@Autowired
	private LogData logData;
	
	private NavigableSet<User> users = new TreeSet<User>();
	private static Logger log = LogManager.getLogger(UserConfiguration.class);
	public void save(User user) {
		try {
			users.add(user);
			logData.saveLog("add user "+user.getCodeUser());
			log.info("add user"+user.getCodeUser()+" succesfully");
		}
		catch(IllegalArgumentException e) {
			log.error("User has been exist", new UsernameAlreadyExistException("User has been exist"));
		}
	}
	public void modifyCountIn(User user) {
		try {
			users.stream().filter(e -> e.getCodeUser() == user.getCodeUser()).forEach(e -> e.setCountInStore(e.getCountInStore()+1));
			log.info("modify user "+user.getCodeUser()+" with count in store: "+user.getCountInStore()+" succesfully");
		}
		catch(NullPointerException e) {
			log.error("modify user "+user.getCodeUser()+" with count in store: "+user.getCountInStore()+" didn`t succesfully",new UsernameNotFoundException("Username not found"));
		}
	}
	public void delete(User user) {
		try {
		if(users.contains(user)) {
			users.remove(user);
			logData.saveLog("remove user "+user.getCodeUser());
			log.info("remove user "+user.getCodeUser()+" succesfully");
		}}
		catch(NullPointerException e) {
			log.info("remove user "+user.getCodeUser()+" succesfully", new UsernameNotFoundException("Username not found"));
		}
	}
	public NavigableSet<User> getUsers() {
		return users;
	}
	public User getUserByIdCode(String codeId) throws NullPointerException{
		return users.stream().filter(p -> p.getCodeUser() == codeId).findFirst().get();
	}
}
