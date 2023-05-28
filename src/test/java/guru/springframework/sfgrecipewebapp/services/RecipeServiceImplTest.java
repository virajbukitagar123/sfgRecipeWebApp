package guru.springframework.sfgrecipewebapp.services;

import guru.springframework.sfgrecipewebapp.commands.RecipeCommand;
import guru.springframework.sfgrecipewebapp.convertors.RecipeCommandToRecipe;
import guru.springframework.sfgrecipewebapp.convertors.RecipeToRecipeCommand;
import guru.springframework.sfgrecipewebapp.domain.Recipe;
import guru.springframework.sfgrecipewebapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Test
    void getRecipesTest() {
        // given
        Recipe recipe = new Recipe();
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe);

        when(recipeRepository.findAll())
                .thenReturn(recipeSet);

        // when
        Set<Recipe> recipes = recipeService.getRecipes();

        // then
        assertThat(recipes)
                .hasSize(1);
        verify(recipeRepository, times(1))
                .findAll();
    }

    @Test
    void getRecipeCommandByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeService.findCommandById(1L);

        assertThat(commandById)
                .isNotNull();
        verify(recipeRepository)
                .findById(anyLong());
        verify(recipeRepository, never())
                .findAll();
    }

    @Test
    void getRecipesByIdTest() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> optionalRecipe = Optional.of(recipe);


        when(recipeRepository.findById(1L))
                .thenReturn(optionalRecipe);

        // when
        Recipe returnedRecipe = recipeService.findById(1L);

        // then
        assertThat(returnedRecipe)
                .isNotNull();
        verify(recipeRepository, times(1))
                .findById(anyLong());
        verify(recipeRepository, never())
                .findAll();
    }

    @Test
    public void testDeleteById() {
        Long idToDelete = 2L;
        recipeService.deleteById(idToDelete);

        verify(recipeRepository, times(1))
                .deleteById(anyLong());
    }

}