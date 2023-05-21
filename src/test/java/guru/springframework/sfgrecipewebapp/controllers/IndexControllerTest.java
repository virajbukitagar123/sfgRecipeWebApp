package guru.springframework.sfgrecipewebapp.controllers;

import guru.springframework.sfgrecipewebapp.domain.Recipe;
import guru.springframework.sfgrecipewebapp.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    @InjectMocks
    IndexController indexController;

    @Captor
    ArgumentCaptor<Set<Recipe>> argumentCaptor;

    @Test
    void getIndexPage() {
        // given
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(Recipe.builder().id(1L).build());
        recipeSet.add(Recipe.builder().id(2L).build());

        when(recipeService.getRecipes())
                .thenReturn(recipeSet);


        String indexPage = indexController.getIndexPage(model);

        assertThat(indexPage)
                .isEqualTo("index");
        verify(model, times(1))
                .addAttribute(eq("recipes"), argumentCaptor.capture());
        verify(recipeService, times(1))
                .getRecipes();
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertThat(setInController)
                .hasSize(2);
    }
}