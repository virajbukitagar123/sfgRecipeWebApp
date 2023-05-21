package guru.springframework.sfgrecipewebapp.convertors;

import guru.springframework.sfgrecipewebapp.commands.IngredientCommand;
import guru.springframework.sfgrecipewebapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandConvertor;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandConvertor) {
        this.unitOfMeasureCommandConvertor = unitOfMeasureCommandConvertor;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null)
            return null;

        return Ingredient.builder()
                .id(source.getId())
                .amount(source.getAmount())
                .description(source.getDescription())
                .unitOfMeasure(unitOfMeasureCommandConvertor.convert(source.getUnitOfMeasure()))
                .build();
    }
}
