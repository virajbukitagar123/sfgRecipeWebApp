package guru.springframework.sfgrecipewebapp.services;

import guru.springframework.sfgrecipewebapp.commands.IngredientCommand;
import guru.springframework.sfgrecipewebapp.convertors.IngredientCommandToIngredient;
import guru.springframework.sfgrecipewebapp.convertors.IngredientToIngredientCommand;
import guru.springframework.sfgrecipewebapp.convertors.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.sfgrecipewebapp.convertors.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.sfgrecipewebapp.domain.Ingredient;
import guru.springframework.sfgrecipewebapp.domain.Recipe;
import guru.springframework.sfgrecipewebapp.repositories.RecipeRepository;
import guru.springframework.sfgrecipewebapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientServiceImpl ingredientService;

    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(
                new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(
                new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    void setup() throws Exception {
        ingredientService = new IngredientServiceImpl(
                recipeRepository,
                ingredientToIngredientCommand,
                ingredientCommandToIngredient,
                unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndId() throws Exception {
    }

    @Test
    void findByRecipeIdAndIngredientId() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        when(recipeRepository.findById(anyLong()))
                .thenReturn(Optional.of(recipe));

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        assertThat(ingredientCommand.getId())
                .isEqualTo(3L);
        assertThat(ingredientCommand.getRecipeId())
                .isEqualTo(1L);
        verify(recipeRepository, times(1))
                .findById(anyLong());

    }

    @Test
    void testSaveRecipeCommand() throws Exception {
        // given
        IngredientCommand command = new IngredientCommand();
        command.setId(1L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(1L);

        when(recipeRepository.findById(anyLong()))
                .thenReturn(recipeOptional);
        when(recipeRepository.save(any()))
                .thenReturn(savedRecipe);

        // when
        IngredientCommand savedCommand = ingredientService.saveIngredient(command);

        // then
        assertThat(savedCommand.getId())
                .isEqualTo(1L);

        verify(recipeRepository)
                .findById(anyLong());
        verify(recipeRepository)
                .save(any(Recipe.class));
    }

    @Test
    void deleteIngredient() throws Exception {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(2L);
        ingredient.setRecipe(recipe);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(3L);
        ingredient1.setRecipe(recipe);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient);

        when(recipeRepository.findById(anyLong()))
                .thenReturn(Optional.of(recipe));

        // when
        ingredientService.deleteIngredient(1L, 2L);

        verify(recipeRepository)
                .findById(anyLong());
        verify(recipeRepository)
                .save(any(Recipe.class));
    }
}