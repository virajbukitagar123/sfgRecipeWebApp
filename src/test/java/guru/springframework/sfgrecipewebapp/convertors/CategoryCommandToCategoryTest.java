package guru.springframework.sfgrecipewebapp.convertors;

import guru.springframework.sfgrecipewebapp.commands.CategoryCommand;
import guru.springframework.sfgrecipewebapp.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryCommandToCategoryTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    CategoryCommandToCategory convertor;

    @BeforeEach
    void setUp() {
        convertor = new CategoryCommandToCategory();
    }

    @Test
    void convertNull() {
        assertThat(convertor.convert(null))
                .isNull();
    }

    @Test
    void convertEmptyObject() {
        assertThat(convertor.convert(new CategoryCommand()))
                .isNotNull()
                .extracting("id", "description")
                .containsExactly(null, null);
    }

    @Test
    void convert() {
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION);

        Category category = convertor.convert(categoryCommand);

        assertThat(category)
                .isNotNull()
                .extracting("id", "description")
                .containsExactly(ID_VALUE, DESCRIPTION);
    }
}