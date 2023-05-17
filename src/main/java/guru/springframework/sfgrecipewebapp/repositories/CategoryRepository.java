package guru.springframework.sfgrecipewebapp.repositories;

import guru.springframework.sfgrecipewebapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
