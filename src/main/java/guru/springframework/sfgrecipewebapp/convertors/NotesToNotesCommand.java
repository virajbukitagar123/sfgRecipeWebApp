package guru.springframework.sfgrecipewebapp.convertors;

import guru.springframework.sfgrecipewebapp.commands.NotesCommand;
import guru.springframework.sfgrecipewebapp.domain.Notes;
import org.springframework.stereotype.Component;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand>{

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if (source == null) {
            return null;
        }

        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());
        return notesCommand;
    }
}
