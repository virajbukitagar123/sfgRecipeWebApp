package guru.springframework.sfgrecipewebapp.services;

import guru.springframework.sfgrecipewebapp.commands.IngredientCommand;
import guru.springframework.sfgrecipewebapp.convertors.IngredientCommandToIngredient;
import guru.springframework.sfgrecipewebapp.convertors.IngredientToIngredientCommand;
import guru.springframework.sfgrecipewebapp.domain.Ingredient;
import guru.springframework.sfgrecipewebapp.domain.Recipe;
import guru.springframework.sfgrecipewebapp.repositories.RecipeRepository;
import guru.springframework.sfgrecipewebapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(
            RecipeRepository recipeRepository,
            IngredientToIngredientCommand ingredientToIngredientCommand,
            IngredientCommandToIngredient ingredientCommandToIngredient,
            UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
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

    @Override
    @Transactional
    public void deleteIngredient(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isEmpty()){
            log.error("Recipe Id not found Id: " + recipeId);
            return;
        }

        Recipe recipe = recipeOptional.get();
        Set<Ingredient> ingredients = recipe.getIngredients();
        Optional<Ingredient> ingredientOptional = ingredients.stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();

        if(ingredientOptional.isEmpty()) {
            log.error("Ingredient Id not found Id: " + ingredientId);
            return;
        }

        Ingredient ingredientToDelete = ingredientOptional.get();
        ingredientToDelete.setRecipe(null);
        recipe.getIngredients().remove(ingredientToDelete);
        recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredient(IngredientCommand ingredientCommand) {
        Long recipeId = ingredientCommand.getRecipeId();

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isEmpty()){
            log.error("Recipe not found with Id: " + recipeId);
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()) {
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredient.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("Unit Of Measure Not Found")));
                ingredient.setAmount(ingredientCommand.getAmount());
                 // recipe.addIngredient(ingredient);
             } else {
                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
             }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> saveIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(saveIngredientOptional.isEmpty()) {
                saveIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(saveIngredientOptional.get());
        }
    }
}
