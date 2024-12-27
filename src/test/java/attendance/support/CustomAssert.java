package attendance.support;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import attendance.exception.ErrorMessage;
import attendance.exception.ErrorPrefix;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.ThrowableAssert;

public class CustomAssert {

    public static AbstractThrowableAssert<?, ? extends Throwable> assertIllegalArgument(
            ThrowableAssert.ThrowingCallable throwingCallable) {
        return assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith(ErrorPrefix.format(""));
    }

    public static AbstractThrowableAssert<?, ? extends Throwable> assertIllegalArgument(
            ThrowableAssert.ThrowingCallable throwingCallable,
            ErrorMessage expectedMessage) {
        return assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith(ErrorPrefix.format(""))
                .hasMessageContaining(expectedMessage.getMessage());
    }
}
