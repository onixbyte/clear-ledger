/**
 * A container object which may or may not contain a non-null value.
 * This class is inspired by Java's Optional, providing a way to handle potentially absent values
 * in a type-safe manner.
 *
 * @template T - The type of the value that may be contained.
 */
class Optional<T> {
  /** @private The internal value, which may be null or undefined */
  private readonly value: T | null | undefined

  /**
   * Constructs an Optional instance with the given value.
   * This constructor is private; use static methods to create instances.
   *
   * @param value - The value to wrap, which may be null or undefined.
   */
  private constructor(value: T | null | undefined) {
    this.value = value
  }

  /**
   * Creates an Optional containing a non-null value.
   * Throws an error if the value is null or undefined.
   *
   * @template T - The type of the value.
   * @param value - The non-null value to wrap.
   * @returns An Optional containing the value.
   * @throws {Error} If the value is null or undefined.
   */
  static of<T>(value: T): Optional<T> {
    if (value === null || value === undefined) {
      throw new Error("Value cannot be null or undefined")
    }
    return new Optional<T>(value)
  }

  /**
   * Creates an Optional that may contain a null or undefined value.
   *
   * @template T - The type of the value.
   * @param value - The value to wrap, which may be null or undefined.
   * @returns An Optional containing the value or an empty Optional if null/undefined.
   */
  static ofNullable<T>(value: T | null | undefined): Optional<T> {
    return new Optional<T>(value)
  }

  /**
   * Creates an empty Optional with no value.
   *
   * @template T - The type of the value.
   * @returns An empty Optional instance.
   */
  static empty<T>(): Optional<T> {
    return new Optional<T>(null)
  }

  /**
   * Checks whether a value is present in this Optional.
   *
   * @returns {boolean} True if a value is present, false otherwise.
   */
  isPresent(): boolean {
    return this.value !== null && this.value !== undefined
  }

  /**
   * Retrieves the value if present, otherwise throws an error.
   *
   * @returns The contained value.
   * @throws {Error} If no value is present.
   */
  get(): T {
    if (!this.isPresent()) {
      throw new Error("No value present")
    }
    return this.value as T
  }

  /**
   * Returns the contained value if present, otherwise returns the provided default value.
   *
   * @param defaultValue - The value to return if no value is present.
   * @returns The contained value or the default value.
   */
  orElse(defaultValue: T): T {
    return this.isPresent() ? (this.value as T) : defaultValue
  }

  /**
   * Returns the contained value if present, otherwise returns the result of the supplier function.
   *
   * @param supplier - A function that provides a default value if none is present.
   * @returns The contained value or the result of the supplier.
   */
  orElseGet(supplier: () => T): T {
    return this.isPresent() ? (this.value as T) : supplier()
  }

  /**
   * Returns this Optional if a value is present, otherwise returns the result of the supplier.
   *
   * @param supplier - A function that provides an alternative Optional.
   * @returns This Optional if a value is present, or the supplied Optional.
   */
  or(supplier: () => Optional<T>): Optional<T> {
    return this.isPresent() ? this : supplier()
  }

  /**
   * Executes the provided consumer function if a value is present.
   *
   * @param consumer - A function to execute with the value if present.
   */
  ifPresent(consumer: (value: T) => void): void {
    if (this.isPresent()) {
      consumer(this.value as T)
    }
  }

  /**
   * Applies a transformation function to the value if present, returning a new Optional
   * containing the result. If no value is present or the mapper returns null/undefined,
   * returns an empty Optional.
   *
   * @template U The type of the transformed value.
   * @param mapper A function that transforms the contained value into a new value,
   *               which may be null or undefined.
   * @returns An Optional containing the transformed value if a value is present and
   *          the mapper returns a non-null value, or an empty Optional otherwise.
   */
  map<U>(mapper: (value: T) => U | null | undefined): Optional<U> {
    if (!this.isPresent()) {
      return Optional.empty<U>()
    }
    const result = mapper(this.value as T)
    return Optional.ofNullable(result)
  }
}

export { Optional }
