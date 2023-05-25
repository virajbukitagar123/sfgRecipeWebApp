package guru.springframework.sfgrecipewebapp.services;

import guru.springframework.sfgrecipewebapp.commands.IngredientCommand;
import guru.springframework.sfgrecipewebapp.convertors.IngredientToIngredientCommand;
import guru.springframework.sfgrecipewebapp.domain.Recipe;
import guru.springframework.sfgrecipewebapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(
            RecipeRepository recipeRepository,
            IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isEmpty()){
            log.error("Recipe Id not found Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients()
                .stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();

        if(ingredientCommandOptional.isEmpty()){
            log.error("Ingredient Id not found Id: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }
}
