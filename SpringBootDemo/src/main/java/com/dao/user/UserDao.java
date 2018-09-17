package com.dao.user;


import com.entity.User;


/**
 * 用户实体
 * @author brandon
 *
 */
//@Mapper
public interface UserDao {
	/**
	 * 添加
	 */
	int addUser(User user);

	User findById();
}
