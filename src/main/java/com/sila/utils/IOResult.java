package com.sila.utils;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author amin
 *
 *         A monadic class that contains either a value or an error Implements
 *         map and flatmap for
 *
 * @param <E>
 *            the error, i.e., an exception or an enum
 * @param <R>
 *            the result
 */
public class IOResult<E, R> {

	/**
	 * Returns an empty {@code Optional} instance. No value is present for this
	 * Optional.
	 *
	 * @apiNote Though it may be tempting to do so, avoid testing if an object
	 *          is empty by comparing with {@code ==} against instances returned
	 *          by {@code Option.empty()}. There is no guarantee that it is a
	 *          singleton. Instead, use {@link #isPresent()}.
	 *
	 * @param <T>
	 *            Type of the non-existent value
	 * @return an empty {@code Optional}
	 */
	public static <E, R> IOResult<E, R> error(final E error) {
		@SuppressWarnings("unchecked")
		final IOResult<E, R> t = new IOResult<>(error, null);
		return t;
	}

	/**
	 * Returns an {@code Optional} describing the specified value, if non-null,
	 * otherwise returns an empty {@code Optional}.
	 *
	 * @param <T>
	 *            the class of the value
	 * @param value
	 *            the possibly-null value to describe
	 * @return an {@code Optional} with a present value if the specified value
	 *         is non-null, otherwise an empty {@code Optional}
	 */
	public static <E, R> IOResult<E, R> success(final R result) {
		return new IOResult(null, result);
	}

	private final E error;
	private final R result;

	private IOResult(final E error, final R result) {
		this.error = error;
		this.result = result;
	}

	/**
	 * Indicates whether some other object is "equal to" this Optional. The
	 * other object is considered equal if:
	 * <ul>
	 * <li>it is also an {@code Optional} and;
	 * <li>both instances have no value present or;
	 * <li>the present values are "equal to" each other via {@code equals()}.
	 * </ul>
	 *
	 * @param obj
	 *            an object to be tested for equality
	 * @return {code true} if the other object is "equal to" this object
	 *         otherwise {@code false}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof IOResult)) {
			return false;
		}

		final IOResult<?, ?> other = (IOResult<?, ?>) obj;
		return Objects.equals(result, other.result);
	}

	/**
	 * If a value is present, and the value matches the given predicate, return
	 * an {@code Optional} describing the value, otherwise return an empty
	 * {@code Optional}.
	 *
	 * @param predicate
	 *            a predicate to apply to the value, if present
	 * @return an {@code Optional} describing the value of this {@code Optional}
	 *         if a value is present and the value matches the given predicate,
	 *         otherwise an empty {@code Optional}
	 * @throws NullPointerException
	 *             if the predicate is null
	 */
	public IOResult<E, R> filter(final Predicate<? super R> predicate,
			final E error) {
		Objects.requireNonNull(predicate);
		if (!isSuccess())
			return this;
		else
			return predicate.test(result) ? this : error(error);
	}

	/**
	 * If a value is present, apply the provided {@code Optional}-bearing
	 * mapping function to it, return that result, otherwise return an empty
	 * {@code Optional}. This method is similar to {@link #map(Function)}, but
	 * the provided mapper is one whose result is already an {@code Optional},
	 * and if invoked, {@code flatMap} does not wrap it with an additional
	 * {@code Optional}.
	 *
	 * @param <U>
	 *            The type parameter to the {@code Optional} returned by
	 * @param mapper
	 *            a mapping function to apply to the value, if present the
	 *            mapping function
	 * @return the result of applying an {@code Optional}-bearing mapping
	 *         function to the value of this {@code Optional}, if a value is
	 *         present, otherwise an empty {@code Optional}
	 * @throws NullPointerException
	 *             if the mapping function is null or returns a null result
	 */
	public <U> IOResult<E, U> flatMap(
			final Function<? super R, IOResult<E, U>> mapper) {
		Objects.requireNonNull(mapper);
		if (!isSuccess())
			return new IOResult(error, null);
		else {
			return Objects.requireNonNull(mapper.apply(result));
		}
	}

	/**
	 * If a value is present in this {@code Optional}, returns the value,
	 * otherwise throws {@code NoSuchElementException}.
	 *
	 * @return the non-null value held by this {@code Optional}
	 * @throws NoSuchElementException
	 *             if there is no value present
	 *
	 * @see Optional#isPresent()
	 */
	public R getResult() {
		if (result == null) {
			throw new NoSuchElementException("No value present");
		}
		return result;
	}

	/**
	 * Returns the hash code value of the present value, if any, or 0 (zero) if
	 * no value is present.
	 *
	 * @return hash code value of the present value or 0 if no value is present
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(result);
	}

	/**
	 * If a value is present, invoke the specified consumer with the value,
	 * otherwise do nothing.
	 *
	 * @param consumer
	 *            block to be executed if a value is present
	 * @throws NullPointerException
	 *             if value is present and {@code consumer} is null
	 */
	public void ifSuccess(final Consumer<? super R> consumer) {
		if (result != null)
			consumer.accept(result);
	}

	/**
	 * Return {@code true} if there is a value present, otherwise {@code false}.
	 *
	 * @return {@code true} if there is a value present, otherwise {@code false}
	 */
	public boolean isSuccess() {
		return result != null;
	}

	/**
	 * If a value is present, apply the provided mapping function to it, and if
	 * the result is non-null, return an {@code Optional} describing the result.
	 * Otherwise return an empty {@code Optional}.
	 *
	 * @apiNote This method supports post-processing on optional values, without
	 *          the need to explicitly check for a return status. For example,
	 *          the following code traverses a stream of file names, selects one
	 *          that has not yet been processed, and then opens that file,
	 *          returning an {@code Optional<FileInputStream>}:
	 *
	 *          <pre>
	 * {
	 * 	&#064;code
	 * 	Optional&lt;FileInputStream&gt; fis = names.stream()
	 * 			.filter(name -&gt; !isProcessedYet(name)).findFirst()
	 * 			.map(name -&gt; new FileInputStream(name));
	 * }
	 * </pre>
	 *
	 *          Here, {@code findFirst} returns an {@code Optional<String>}, and
	 *          then {@code map} returns an {@code Optional<FileInputStream>}
	 *          for the desired file if one exists.
	 *
	 * @param <U>
	 *            The type of the result of the mapping function
	 * @param mapper
	 *            a mapping function to apply to the value, if present
	 * @return an {@code Optional} describing the result of applying a mapping
	 *         function to the value of this {@code Optional}, if a value is
	 *         present, otherwise an empty {@code Optional}
	 * @throws NullPointerException
	 *             if the mapping function is null
	 */
	public <U, E> IOResult<E, U> map(
			final Function<? super R, ? extends U> mapper) {
		Objects.requireNonNull(mapper);
		if (!isSuccess())
			return (IOResult<E, U>) this;
		else {
			return success(mapper.apply(result));
		}
	}

	// /**
	// * Return the value if present, otherwise return {@code other}.
	// *
	// * @param other
	// * the value to be returned if there is no value present, may be
	// * null
	// * @return the value, if present, otherwise {@code other}
	// */
	// public T orElse(final T other) {
	// return value != null ? value : other;
	// }
	//
	// /**
	// * Return the value if present, otherwise invoke {@code other} and return
	// * the result of that invocation.
	// *
	// * @param other
	// * a {@code Supplier} whose result is returned if no value is
	// * present
	// * @return the value if present otherwise the result of {@code
	// other.get()}
	// * @throws NullPointerException
	// * if value is not present and {@code other} is null
	// */
	// public T orElseGet(final Supplier<? extends T> other) {
	// return value != null ? value : other.get();
	// }

	/**
	 * Returns a non-empty string representation of this Optional suitable for
	 * debugging. The exact presentation format is unspecified and may vary
	 * between implementations and versions.
	 *
	 * @implSpec If a value is present the result must include its string
	 *           representation in the result. Empty and present Optionals must
	 *           be unambiguously differentiable.
	 *
	 * @return the string representation of this instance
	 */
	@Override
	public String toString() {
		return result != null ? String.format("IOResult[%s]", result) : String
				.format("IOResult[%s]", error);
	}
}
