package guru.springframework.sfgrecipewebapp.repositories;

import guru.springframework.sfgrecipewebapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    // Dynamic Finder
    Optional<Category> findByDescription(String description);

}
