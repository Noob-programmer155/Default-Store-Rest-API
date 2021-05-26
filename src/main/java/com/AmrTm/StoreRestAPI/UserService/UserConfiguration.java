package com.AmrTm.StoreRestAPI.UserService;

import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.AmrTm.StoreRestAPI.Entity.Item;
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
	
	private NavigableSet<User> users = new TreeSet<User>(new UserComparator());
	private static Logger log = LogManager.getLogger(UserConfiguration.class);
	public void save(User user) throws UsernameAlreadyExistException {
		try {
			users.add(user);
			logData.saveLog("add user "+user.getCodeUser());
			log.info("add user "+user.getCodeUser()+" succesfully");
		}
		catch(IllegalArgumentException e) {
			log.error("User has been exist", new UsernameAlreadyExistException("User already exist"));
			throw new UsernameAlreadyExistException("User already exist");
		}
	}
	public void modifyCountIn(User user) {
		try {
		if(users.contains(user)) {
			users.stream().filter(e -> e.getCodeUser().equals(user.getCodeUser())).forEach(e -> e.setCountInStore(e.getCountInStore()+1));
			log.info("modify user "+user.getCodeUser()+" with count in store: "+user.getCountInStore()+" succesfully");
		}}
		catch(NoSuchElementException | NullPointerException e) {
			log.error("modify user "+user.getCodeUser()+" with count in store: "+user.getCountInStore()+" didn`t succesfully",new UsernameNotFoundException("User not found"));
			throw new UsernameNotFoundException("User not found");
		}
	}
	public void modifyCountIn(User user, Item item) {
		try {
			users.stream().filter(e -> e.getCodeUser().equals(user.getCodeUser())).forEach(e -> e.setItemPurchaseInStore(item));
			log.info("modify user "+user.getCodeUser()+" with item purchased in store: "+item.getId()+" succesfully");
		}
		catch(NoSuchElementException | NullPointerException e) {
			log.error("modify user "+user.getCodeUser()+" with item purchased in store: "+item.getId()+" didn`t succesfully",new UsernameNotFoundException("User not found"));
			throw new UsernameNotFoundException("User not found");
		}
	}
	public void delete(User user) {
		try {
		if(users.contains(user)) {
			users.remove(user);
			logData.saveLog("remove user "+user.getCodeUser());
			log.info("remove user "+user.getCodeUser()+" succesfully");
		}}
		catch(NoSuchElementException | NullPointerException e) {
			log.info("remove user "+user.getCodeUser()+" didn`t succesfully", new UsernameNotFoundException("User not found"));
			throw new UsernameNotFoundException("User not found");
		}
	}
	public NavigableSet<User> getUsers() {
		return users;
	}
	public User getUserByIdCode(String codeId) throws NullPointerException{
		try {
			return users.stream().filter(p -> p.getCodeUser().equals(codeId)).findFirst().get();}
		catch(NoSuchElementException | NullPointerException ex) {
			throw new NullPointerException("User not found");
		}
	}
}
