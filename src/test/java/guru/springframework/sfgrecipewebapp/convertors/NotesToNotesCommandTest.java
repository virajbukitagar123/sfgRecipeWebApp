package guru.springframework.sfgrecipewebapp.convertors;

import guru.springframework.sfgrecipewebapp.commands.NotesCommand;
import guru.springframework.sfgrecipewebapp.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotesToNotesCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "recipe_notes";

    NotesToNotesCommand convertor;

    @BeforeEach
    void setUp() {
        convertor = new NotesToNotesCommand();
    }

    @Test
    void convertNull() {
        assertThat(convertor.convert(null))
                .isNull();
    }

    @Test
    void convertEmptyObject() {
        assertThat(convertor.convert(new Notes()))
                .isNotNull()
                .extracting("id", "recipeNotes")
                .containsExactly(null, null);
    }

    @Test
    void convert() {
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        NotesCommand notesCommand = convertor.convert(notes);

        assertThat(notesCommand)
                .isNotNull()
                .extracting("id", "recipeNotes")
                .containsExactly(ID_VALUE, RECIPE_NOTES);
    }
}