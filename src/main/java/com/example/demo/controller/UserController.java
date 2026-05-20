package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

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

	// ログイン画面
	@GetMapping({ "/", "/login" })
	public String index() {
		return "login";
	}

	// ログアウト
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	// 新規登録画面
	@GetMapping("/users/new")
	public String create(Model model) {
		model.addAttribute("user", new User());
		return "newUsers";
	}

	// 新規作成ボタンを押したとき
	@PostMapping("/users/add")
	public String add(
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam String passwordConfirm,
			Model model) {

		List<String> errorList = new ArrayList<>();
		if (email.isEmpty() && password.isEmpty()) {
			errorList.add("メールアドレスとパスワードを入力してください");
		} else if (email.isEmpty()) {
			errorList.add("メールアドレスは必須です");
		} else if (password.isEmpty()) {
			errorList.add("パスワードは必須です");
		}

		if (!password.isEmpty() && !password.equals(passwordConfirm)) {
			errorList.add("パスワードが一致しません");
		}
		if (!email.isEmpty()) {
			if (userRepository.findByEmail(email).isPresent()) {
				errorList.add("このメールアドレスは既に登録されています");
			}
		}

		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("email", email);
			return "newUsers";
		}

		User user = new User();
		user.setEmail(email);
		user.setPassword(password);

		userRepository.save(user);
		return "redirect:/login";
	}

	//ログイン
	@PostMapping("/login")
	public String login(
			@RequestParam String email,
			@RequestParam String password,
			HttpSession session,
			Model model) {

		// 未入力チェック
		List<String> errorList = new ArrayList<>();
		if (email.isEmpty() && password.isEmpty()) {
			errorList.add("メールアドレスとパスワードを入力してください");
		} else if (email.isEmpty()) {
			errorList.add("メールアドレスは必須です");
		} else if (password.isEmpty()) {
			errorList.add("パスワードは必須です");
		}

		// 登録済みチェック・パスワード照合
		if (errorList.isEmpty()) {
			Optional<User> userOpt = userRepository.findByEmail(email);

			if (userOpt.isEmpty()) {
				errorList.add("登録されていません");
			} else {
				User user = userOpt.get();
				if (!user.getPassword().equals(password)) {
					errorList.add("メールアドレスまたはパスワードが間違っています");
				} else {
					session.setAttribute("loginUser", user);
				}
			}
		}

		// エラーがあったらログイン画面に戻す
		if (errorList.size() > 0) {
			model.addAttribute("errorList", errorList);
			model.addAttribute("email", email);
			return "login";
		}

		// ログイン成功
		return "redirect:/recipes";
	}
}
