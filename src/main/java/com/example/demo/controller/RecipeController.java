package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;

@Controller
public class RecipeController {

	@Autowired
	private RecipeRepository recipeRepository;

	// レシピ画面・一覧表示
	@GetMapping("/recipes")
	public String index(
			@RequestParam(name = "categoryId", required = false) Integer categoryId,
			@RequestParam(defaultValue = "") String keyword,
			Model model) {

		List<String> categoryNames = List.of("丼もの", "麵類", "デザート", "肉料理", "スープ", "サラダ", "魚介", "パン", "鍋もの", "粉もの");
		model.addAttribute("categoryNames", categoryNames);

		List<Recipe> recipeList = null;

		// キーワード検索
		if (keyword.length() > 0) {
			recipeList = recipeRepository.findByNameContaining(keyword);
		} else if (categoryId != null) {
			recipeList = recipeRepository.findByCategoryId(categoryId);
		} else {
			// 全レシピ検索
			recipeList = recipeRepository.findAll();
		}

		model.addAttribute("keyword", keyword);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("recipes", recipeList);

		return "recipes";
	}

	//レシピ閲覧画面
	@GetMapping("/recipes/{id}")
	public String showRecipe(@PathVariable("id") Integer id, Model model) {
		Recipe recipe = recipeRepository.findById(id).orElse(null);
		model.addAttribute("recipe", recipe);
		return "recipeView";

	}

	//投稿画面
	@GetMapping("/recipeCreate")
	public String create() {
		return "recipeCreate";
	}

}