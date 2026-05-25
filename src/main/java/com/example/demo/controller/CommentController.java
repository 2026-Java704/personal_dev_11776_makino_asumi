package com.example.demo.controller;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Recipe;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.RecipeRepository;

@Controller
public class CommentController {

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private CommentRepository commentRepository;

	// コメントを保存する
	@PostMapping("/recipes/{recipeId}/comments")
	@Transactional
	public String addComment(
			@PathVariable Integer recipeId,
			@RequestParam String content) {

		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid recipe Id:" + recipeId));

		// コメントのデータ
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setRecipe(recipe);

		// データベースに保存
		commentRepository.save(comment);

		return "redirect:/recipes/" + recipeId;
	}
}
