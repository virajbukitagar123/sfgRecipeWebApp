package guru.springframework.sfgrecipewebapp.convertors;

import guru.springframework.sfgrecipewebapp.commands.UnitOfMeasureCommand;
import guru.springframework.sfgrecipewebapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final long ID = 1L;
    public static final String DESCRIPTION = "Cup";
    UnitOfMeasureToUnitOfMeasureCommand convertor;

    @BeforeEach
    void setUp() {
        convertor = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void convertNullValue() {
        UnitOfMeasureCommand uom = convertor.convert(null);
        assertThat(uom)
                .isNull();
    }

    @Test
    void convertEmptyObject() {
        UnitOfMeasureCommand uom = convertor.convert(new UnitOfMeasure());
        assertThat(uom)
                .isNotNull()
                .extracting("id", "description")
                .containsExactly(null, null);
    }

    @Test
    void convert() {
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(ID);
        uom.setDescription(DESCRIPTION);
        UnitOfMeasureCommand uomCommand = convertor.convert(uom);
        assertThat(uomCommand)
                .isNotNull()
                .extracting("id", "description")
                .containsExactly(ID, DESCRIPTION);
    }
}