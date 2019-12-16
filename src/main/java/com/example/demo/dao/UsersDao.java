package com.example.demo.dao;

import com.example.demo.entity.Good;
import com.example.demo.entity.Indents;
import com.example.demo.entity.ShopCart;
import com.example.demo.entity.Users;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
@Component
public interface UsersDao {
	@Select("select * from user where username=#{username} and password=#{password}")
	public Users login(@Param("username") String username,
			@Param("password") String password);
	
	@Insert("insert into user(username,password,addr,phone,email,role) values(#{username},#{password},#{addr},#{phone},#{email},#{role})")
	public void insertUser(@Param("username") String username,@Param("password") String password,@Param("addr") String addr,
			@Param("phone") String phone,@Param("email") String email,@Param("role") String role);
	
	@Select("select * from indent")
	public List<Indents> findAllIndents();
	
	@Insert("insert into indent(goodname,amount,totalcost,state,addr) values(#{goodname},#{amount},#{totalcost},#{state},#{addr})")
	public void insertIndent(@Param("goodname") String goodname,@Param("amount") int amount,
			@Param("totalcost") double totalcost,@Param("state") String state,@Param("addr") String addr);
	
	@Delete("delete from indent where id=#{id}")
	public int deleteIndent(@Param("id") int id);
	
	@Select("select * from good")
	public List<Good> findAllGoods();
	
	@Insert("insert into good(goodname,price) values(#{goodname},#{price})")
	public void insertGood(@Param("goodname") String goodname,@Param("price") double price);
	
	@Delete("delete from good where id=#{id}")
	public int deleteGood(@Param("id") int id);
	
	@Insert("insert into cart(userid,goodname,price) values(#{userid},#{goodname},#{price})")
	public void insertCart(@Param("userid") int userid,@Param("goodname") String goodname,@Param("price") double price);
	
	@Select("select * from cart where userid=#{userid}")
	public List<ShopCart> findCart(@Param("userid") int userid);
	
	@Select("select addr from user where id=#{id}")
	public String getAddr(@Param("id") int id);
	
	@Delete("delete from cart where userid=#{userid} and goodname=#{goodname}")
	public int deleteCartGood(@Param("userid") int userid,@Param("goodname") String goodname);
	
}
