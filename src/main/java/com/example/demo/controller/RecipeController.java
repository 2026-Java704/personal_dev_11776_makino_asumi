package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Recipe;
import com.example.demo.repository.RecipeRepository;

@Controller
public class RecipeController {

	@Autowired
	private RecipeRepository recipeRepository;

	//レシピ画面
	@GetMapping("/recipes")
	public String index(Model model) {
		List<Recipe> recipeList = recipeRepository.findAll();

		model.addAttribute("recipes", recipeList);

		return "recipes";
	}
}
