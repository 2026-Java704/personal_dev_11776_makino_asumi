package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	//ログイン画面
	@GetMapping({ "/", "/login", "/logout" })
	public String index() {
		return "login";
	}

	//新規登録
	@GetMapping("/users/new")
	public String create(Model model) {

		model.addAttribute("user", new User());
		return "newUsers";

	}

	//新規作成ボタンを押したとき
	@PostMapping("/users/add")
	public String add(User user) {

		userRepository.save(user);
		return "redirect:/login";

	}

	//ログインボタンをクリック
	@PostMapping("/login")
	public String login(
			@RequestParam String email,
			@RequestParam String password,
			Model model) {

		//エラーチェック
		List<String> errorList = new ArrayList<>();
		if (email.length() == 0 && password.length() == 0) {
			errorList.add("メールアドレスとパスワードを入力してください");
		} else if (email.length() == 0) {
			errorList.add("メールアドレスは必須です");
		} else if (password.length() == 0) {
			errorList.add("パスワードは必須です");
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			return "login";
		}
		return "redirect:/recipes";
	}

}
