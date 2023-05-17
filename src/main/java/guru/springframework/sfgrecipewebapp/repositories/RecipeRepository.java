package guru.springframework.sfgrecipewebapp.repositories;

import guru.springframework.sfgrecipewebapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
