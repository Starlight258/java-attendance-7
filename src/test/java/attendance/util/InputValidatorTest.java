package attendance.util;

import static attendance.exception.ErrorMessage.INVALID_FORMAT;
import static attendance.support.CustomAssert.assertIllegalArgument;

import attendance.exception.ErrorMessage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void null이거나_빈_값이면_예외가_발생한다(String input) {
        assertIllegalArgument(
                () -> InputValidator.validateNotNullOrBlank(input, INVALID_FORMAT),
                ErrorMessage.INVALID_FORMAT);
    }
}
