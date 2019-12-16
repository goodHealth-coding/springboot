package com.example.demo.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.service.UsersService;


import com.example.demo.entity.Good;
import com.example.demo.entity.Indents;
import com.example.demo.entity.ShopCart;
import com.example.demo.entity.Users;

@Controller
public class InitController {
	
    
	@Autowired
    private UsersService userservice;
	public int cusid = 0;

    @RequestMapping(value={"/tologin.action"}, method={RequestMethod.GET, RequestMethod.POST})
    public String index() {
        return "login";
    }
    
    @RequestMapping(value={"/register.action"}, method={RequestMethod.GET, RequestMethod.POST})
    public String register() {
    	return "register";
    }
    
    @RequestMapping(value={"/successRegister"}, method={RequestMethod.GET, RequestMethod.POST})
    public String register1(HttpServletRequest request) {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	String addr = request.getParameter("addr");
    	String phone = request.getParameter("phone");
    	String email = request.getParameter("email");
    	String role = "customer";
    	userservice.insertUser(username, password, addr, phone, email, role);
    	return "login";
    }
    
    @RequestMapping(value={"adminHome"}, method={RequestMethod.GET, RequestMethod.POST})
    public String adFun1(Model model, HttpServletRequest request) {
    	List<Indents> ilist = userservice.showAllIndents();
    	model.addAttribute("ilist",ilist);
        return "administrator";
    }
    
    @RequestMapping(value={"to.addIndent"}, method={RequestMethod.GET, RequestMethod.POST})
    public String adFun2() {
    	return "addIndent";
    }
    
    @RequestMapping(value={"addIndent"}, method={RequestMethod.GET, RequestMethod.POST})
    public String adFun3(Model model, HttpServletRequest request) {
    	String goodname = request.getParameter("aname");
    	String amount = request.getParameter("aamount");
    	String total = request.getParameter("atotal");
    	String state = request.getParameter("astate");
    	String addr = request.getParameter("aaddr");
    	if(goodname != null && amount != null && total != null && state != null && addr != null) {
    		int amount1 = Integer.parseInt(amount);
    		double total1 = Double.parseDouble(total);
    		userservice.insertIndent(goodname, amount1, total1, state, addr);
    		return "redirect:/adminHome";
    	}else {
    		return "redirect:/to.addIntent";
    	}
    }
    
    @RequestMapping(value={"/deIndent"}, method={RequestMethod.GET, RequestMethod.POST})
    public String adFun4(String id) {
    	int id1 = Integer.parseInt(id);
    	userservice.deleteIndent(id1);
    	return "deleteTip";
    }
    
    @RequestMapping(value={"/goods"}, method={RequestMethod.GET, RequestMethod.POST})
    public String adFun5(Model model) {
    	List<Good> glist = userservice.showAllGoods();
    	model.addAttribute("glist",glist);
    	return "good";
    }
    
    @RequestMapping(value={"to.addGood"}, method={RequestMethod.GET, RequestMethod.POST})
    public String adFun6() {
    	return "addGood";
    }
    
    @RequestMapping(value={"addGood"}, method={RequestMethod.GET, RequestMethod.POST})
    public String adFun7(Model model, HttpServletRequest request) {
    	String goodname = request.getParameter("aname");
    	String price = request.getParameter("aprice");
    	if(goodname != null && price != null) {
    		double price1 = Double.parseDouble(price);
    		userservice.insertGood(goodname, price1);
    		return "redirect:/goods";
    	}else {
    		return "redirect:/to.addGood";
    	}
    }
    
    @RequestMapping(value={"/deGood"}, method={RequestMethod.GET, RequestMethod.POST})
    public String adFun8(String id) {
    	int id1 = Integer.parseInt(id);
    	userservice.deleteGood(id1);
    	return "deleteTip";
    }
    
    @RequestMapping(value={"/addCart"}, method={RequestMethod.GET, RequestMethod.POST})
    public String cusFun1(String goodname, String price) {
    	int userid = cusid;
    	double price1 = Double.parseDouble(price);
    	userservice.insertCart(userid, goodname, price1);
    	return "addTip";
    }
    
    @RequestMapping(value={"/shopCart"}, method={RequestMethod.GET, RequestMethod.POST})
    public String cusFun2(Model model) {
    	List<ShopCart> clist = userservice.showCart(cusid);
    	if(clist != null) {
    		model.addAttribute("clist",clist);
        	return "cart";
    	}else {
    		return "mall";
    	}
    }
    
    @RequestMapping(value={"/payGoodofCart"}, method={RequestMethod.GET, RequestMethod.POST})
    public String pay(String goodname, String price, String amount, HttpServletRequest request) {
    	int amount1 = Integer.parseInt(amount);
    	System.out.print(goodname);
    	double price1 = Double.parseDouble(price);
    	double total = price1 * amount1;
    	String state = "待发货";
    	String addr = userservice.getAddr(cusid);
    	userservice.insertIndent(goodname, amount1, total, state, addr);
    	userservice.deleteCartGood(cusid, goodname);
    	return "redirect:/shopCart";
    }
    
    @RequestMapping(value={"/deGoodofCart"}, method={RequestMethod.GET, RequestMethod.POST})
    public String deleteGoodofCart(String goodname, String price) {
    	userservice.deleteCartGood(cusid, goodname);
    	return "redirect:/shopCart";
    }
    
    @RequestMapping(value={"/login.action"}, method={RequestMethod.GET, RequestMethod.POST})
    public String login(Model model, Users users, HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        users.setUsername(username);
        users.setPassword(password);
        Users tuser = userservice.dologin(users);
        if (tuser!=null && tuser.role.equals("administrator")) {
        	List<Indents> ilist = userservice.showAllIndents();
        	model.addAttribute("ilist",ilist);
            return "administrator";
        }else if(tuser!=null && tuser.role.equals("customer")){
        	cusid = tuser.id;
        	List<Good> glist = userservice.showAllGoods();
        	model.addAttribute("glist",glist);
        	return "mall";
        }else {
        	return "failLogin";
        }
    }
}
