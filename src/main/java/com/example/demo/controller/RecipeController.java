package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;

@Controller
public class RecipeController {

	@Autowired
	private RecipeRepository recipeRepository;

	//レシピ画面・一覧表示
	@GetMapping("/recipes")
	public String index(
			@RequestParam(name = "categoryId", required = false) Integer categoryId,
			Model model) {

		List<String> categoryNames = List.of("丼もの", "麵類", "デザート", "肉料理", "サラダ", "魚介", "鍋もの", "粉もの");
		model.addAttribute("categoryNames", categoryNames);

		List<Recipe> recipeList = null;

		if (categoryId != null) {
			recipeList = recipeRepository.findByCategoryId(categoryId);
		} else {
			recipeList = recipeRepository.findAll();
		}
		model.addAttribute("recipes", recipeList);

		return "recipes";

	}

}
