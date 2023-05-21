package guru.springframework.sfgrecipewebapp.convertors;

import guru.springframework.sfgrecipewebapp.commands.CategoryCommand;
import guru.springframework.sfgrecipewebapp.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryToCategoryCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    CategoryToCategoryCommand convertor;

    @BeforeEach
    void setUp() {
        convertor = new CategoryToCategoryCommand();
    }

    @Test
    void convertNull() {
        assertThat(convertor.convert(null))
                .isNull();
    }

    @Test
    void convertEmptyObject() {
        assertThat(convertor.convert(new Category()))
                .isNotNull()
                .extracting("id", "description")
                .containsExactly(null, null);
    }

    @Test
    void convert() {
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        CategoryCommand categoryCommand = convertor.convert(category);

        assertThat(categoryCommand)
                .isNotNull()
                .extracting("id", "description")
                .containsExactly(ID_VALUE, DESCRIPTION);
    }
}