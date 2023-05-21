package guru.springframework.sfgrecipewebapp.convertors;

import guru.springframework.sfgrecipewebapp.commands.NotesCommand;
import guru.springframework.sfgrecipewebapp.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotesCommandToNotesTest {

    public static final Long ID_VALUE = 1L;
    public static final String RECIPE_NOTES = "recipe_notes";

    NotesCommandToNotes convertor;

    @BeforeEach
    void setUp() {
        convertor = new NotesCommandToNotes();
    }

    @Test
    void convertNull() {
        assertThat(convertor.convert(null))
                .isNull();
    }

    @Test
    void convertEmptyObject() {
        assertThat(convertor.convert(new NotesCommand()))
                .isNotNull()
                .extracting("id", "recipeNotes")
                .containsExactly(null, null);
    }

    @Test
    void convert() {
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        Notes notes = convertor.convert(notesCommand);

        assertThat(notes)
                .isNotNull()
                .extracting("id", "recipeNotes")
                .containsExactly(ID_VALUE, RECIPE_NOTES);
    }
}