package guru.springframework.sfgrecipewebapp.convertors;

import guru.springframework.sfgrecipewebapp.commands.CategoryCommand;
import guru.springframework.sfgrecipewebapp.domain.Category;
import org.springframework.stereotype.Component;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }

        final CategoryCommand categoryCommand = new CategoryCommand();

        categoryCommand.setId(source.getId());
        categoryCommand.setDescription(source.getDescription());

        return categoryCommand;
    }
}
