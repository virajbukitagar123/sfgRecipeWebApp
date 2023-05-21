package guru.springframework.sfgrecipewebapp.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    Category category;

    @BeforeEach
    void setup() {
        category = new Category();
    }

    @Test
    void getId() {
        Long idValue = 4L;
        category.setId(idValue);
        assertThat(category.getId())
                .isEqualTo(idValue);
    }

    @Test
    void getDescription() {
    }

    @Test
    void getRecipes() {
    }
}