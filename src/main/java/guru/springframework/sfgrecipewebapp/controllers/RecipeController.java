package guru.springframework.sfgrecipewebapp.controllers;

import guru.springframework.sfgrecipewebapp.commands.RecipeCommand;
import guru.springframework.sfgrecipewebapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showById(@PathVariable Long id, Model model){

        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipe(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping ("/recipe/{id}/update")
    public String update(Model model, @PathVariable Long id) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/recipeform";
    }

    @GetMapping("/recipe/{id}/delete")
    public String delete(Model model, @PathVariable Long id){
        recipeService.deleteById(id);
        return "redirect:/";
    }
}
