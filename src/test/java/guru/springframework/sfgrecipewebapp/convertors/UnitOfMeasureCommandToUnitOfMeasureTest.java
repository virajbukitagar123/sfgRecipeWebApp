package guru.springframework.sfgrecipewebapp.convertors;

import guru.springframework.sfgrecipewebapp.commands.UnitOfMeasureCommand;
import guru.springframework.sfgrecipewebapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final long ID = 1L;
    public static final String DESCRIPTION = "Cup";
    UnitOfMeasureCommandToUnitOfMeasure convertor;

    @BeforeEach
    void setUp() {
        convertor = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void convertNullValue() {
        UnitOfMeasure uom = convertor.convert(null);
        assertThat(uom)
                .isNull();
    }

    @Test
    void convertEmptyObject() {
        UnitOfMeasure uom = convertor.convert(new UnitOfMeasureCommand());
        assertThat(uom)
                .isNotNull()
                .extracting("id", "description")
                .containsExactly(null, null);
    }

    @Test
    void convert() {
        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(ID);
        uomCommand.setDescription(DESCRIPTION);
        UnitOfMeasure uom = convertor.convert(uomCommand);
        assertThat(uom)
                .isNotNull()
                .extracting("id", "description")
                .containsExactly(ID, DESCRIPTION);
    }
}