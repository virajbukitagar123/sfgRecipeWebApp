package guru.springframework.sfgrecipewebapp.services;

import guru.springframework.sfgrecipewebapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);
}
