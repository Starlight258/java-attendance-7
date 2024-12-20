package attendance.exception;

import attendance.view.OutputView;
import java.util.Optional;
import java.util.function.Supplier;

public class ExceptionHandler {

    private final OutputView outputView;

    public ExceptionHandler(final OutputView outputView) {
        this.outputView = outputView;
    }

    public <T> T retryUntilSuccess(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                outputView.showException(e);
            }
        }
    }

    public void retryUntilSuccess(Runnable callback) {
        while (true) {
            try {
                callback.run();
                return;
            } catch (IllegalArgumentException e) {
                outputView.showException(e);
            }
        }
    }

    public <T> T retryUntilPresent(Supplier<Optional<T>> action) {
        while (true) {
            try {
                Optional<T> result = action.get();
                if (result.isPresent()) {
                    return result.get();
                }
            } catch (IllegalArgumentException e) {
                outputView.showException(e);
            }
        }
    }


}
