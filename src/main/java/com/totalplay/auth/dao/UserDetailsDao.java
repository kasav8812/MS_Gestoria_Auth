package com.totalplay.auth.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.totalplay.auth.model.UserDetailsModel;

@Mapper
public interface UserDetailsDao {

	UserDetailsModel getUserByUsername(@Param("username") String username);
	UserDetailsModel getUserInfo(@Param("username") String username);
	List<String> getRoles(@Param("id") Integer id);
}
