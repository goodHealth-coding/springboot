package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.UsersDao;
import com.example.demo.entity.Good;
import com.example.demo.entity.Indents;
import com.example.demo.entity.ShopCart;
import com.example.demo.entity.Users;

@Service
public class UsersService {
	@Autowired
    private UsersDao usersDao;
    public Users dologin(Users users) {
        return usersDao.login(users.getUsername(), users.getPassword());
    }
    public void insertUser(String username, String password, String addr, String phone, String email, String role) {
    	usersDao.insertUser(username, password, addr, phone, email, role);
    }
    public List<Indents> showAllIndents(){
    	return usersDao.findAllIndents();
    }
    public void insertIndent(String goodname, int amount, double totalcost, String state, String addr) {
    	usersDao.insertIndent(goodname, amount, totalcost, state, addr);
    }
    public int deleteIndent(int id) {
    	return usersDao.deleteIndent(id);
    }
    public List<Good> showAllGoods(){
    	return usersDao.findAllGoods();
    }
    public void insertGood(String goodname, double price) {
    	usersDao.insertGood(goodname, price);
    }
    public int deleteGood(int id) {
    	return usersDao.deleteGood(id);
    }
    public void insertCart(int userid, String goodname, double price) {
    	usersDao.insertCart(userid, goodname, price);
    }
    public List<ShopCart> showCart(int userid){
    	return usersDao.findCart(userid);
    }
    public String getAddr(int id) {
    	return usersDao.getAddr(id);
    }
    public int deleteCartGood(int userid, String goodname) {
    	return usersDao.deleteCartGood(userid, goodname);
    }
}
