package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

	List<Recipe> findByCategoryId(Integer categoryId);

}
