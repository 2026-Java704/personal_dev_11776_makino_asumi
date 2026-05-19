package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	//ログイン画面
	@GetMapping({ "/", "/login", "/logout" })
	public String index() {
		return "login";
	}

	//新規登録
	@GetMapping("/users/new")
	public String create() {
		return "newUsers";

	}

	//新規作成ボタンを押したとき
	@PostMapping("/users/add")
	public String add() {
		return "redirect:/login";
	}

	//ログインボタンをクリック
	@PostMapping("/login")
	public String login() {
		return "redirect:/recipes";
	}

}
