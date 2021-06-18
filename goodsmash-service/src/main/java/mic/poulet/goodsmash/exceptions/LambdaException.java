package mic.poulet.goodsmash.exceptions;

import java.util.function.Consumer;
import java.util.function.Function;

public class LambdaException extends RuntimeException {

	private static final long serialVersionUID = -1204563775853998716L;

	private <E extends Exception> LambdaException(E cause) {
		super(cause);
	}

	public <E extends Exception> void relance(Class<E> classeCause) throws E {
		if (getCause() instanceof RuntimeException)
			throw (RuntimeException) getCause();
		throw classeCause.cast(getCause());
	}

	@FunctionalInterface
	public interface ConsumerEx<T, E extends Exception> {
		void accept(T t) throws E;
	}

	public static <T, E extends Exception> Consumer<T> catchConsumer(ConsumerEx<T, E> consumer) {
		return t -> {
			try {
				consumer.accept(t);
			} catch (Exception e) {
				throw new LambdaException(e);
			}
		};
	}

	@FunctionalInterface
	public interface FunctionEx<T, R, E extends Exception> {
		R transform(T t) throws E;
	}

	public static <T, R, E extends Exception> Function<T, R> catchFunction(FunctionEx<T, R, E> function) {
		return t -> {
			try {
				return function.transform(t);
			} catch (Exception e) {
				throw new LambdaException(e);
			}
		};
	}

	@FunctionalInterface
	public interface SupplierEx<T, E extends Exception> {
		T get() throws E;
	}

	public static <T, E extends Exception> Runnable catchRunnable(SupplierEx<T, E> supplier) {
		return () -> {
			try {
				supplier.get();
			} catch (Exception e) {
				throw new LambdaException(e);
			}
		};
	}
}
