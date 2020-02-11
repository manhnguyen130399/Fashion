package com.manhnguyen.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.manhnguyen.entity.ChucVu;
import com.manhnguyen.entity.NhanVien;
import com.manhnguyen.service.EmployeeService;

@Controller
@RequestMapping(value = {"/","home/"})
@SessionAttributes("dangnhap")
public class HomeController {
	@Autowired
	EmployeeService qlns;
	@GetMapping
	public String DefaultHome(ModelMap map,HttpSession httpSession) {
		if(httpSession.getAttribute("dangnhap")!=null)
		{
			String email=(String) httpSession.getAttribute("dangnhap");
			map.addAttribute("dangnhap",email);
		}
		return"web/home";
	}
	@GetMapping("Logout/")
	public String Logout(ModelMap map,HttpSession session,SessionStatus sessionStatus) {
		session.removeAttribute("dangnhap");
		String kt=(String) session.getAttribute("dangnhap");
		System.out.println("ss con lai "+kt);
		sessionStatus.setComplete();
        return "redirect:/";
		
	}
	
	@PostMapping("Singin/")
	public String Singin(@RequestParam String user,@RequestParam String pass,@RequestParam String email,@RequestParam String repass,@RequestParam(required=false) boolean check) {
		NhanVien nv= new NhanVien();
		ChucVu cv=new ChucVu();
		cv.setTenchucvu("KHACHHANG");
		cv.setMachucvu(1);
		if(pass.equals(repass)&& check==true) {
		nv.setChucvu(cv);
		nv.setEmail(email);
		nv.setTendangnhap(user);
		nv.setMatkhau(pass);
		boolean kt =qlns.insertCustomer(nv);
		if(kt!=true) {
			return "web/404";
		}
		}else {
			return "web/404";
		}
	
		return "redirect:/";
	}
	@PostMapping("Login/")
	public String Login(@RequestParam String username,@RequestParam String password,ModelMap map) {
		boolean kiemtra=qlns.checkLogin(username, password);
		if(kiemtra==true) {
			map.addAttribute("dangnhap", username);
			System.out.println("thanh cong");
			return "redirect:/";
			
		}
		
		return "web/404";
	}

}
