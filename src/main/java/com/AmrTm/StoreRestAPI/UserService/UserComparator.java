package com.AmrTm.StoreRestAPI.UserService;

import java.util.Comparator;

import com.AmrTm.StoreRestAPI.Entity.User;

public class UserComparator implements Comparator<User>{
	@Override
	public int compare(User o1, User o2) {	
		return o1.getUsername().compareTo(o2.getUsername());
	}
}
