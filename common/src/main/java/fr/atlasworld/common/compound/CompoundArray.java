/*
  AtlasWorld's Proprietary License

  Copyright (c) 2022 - 2024 AtlasWorld Studio. All Rights Reserved.

  This software is proprietary to AtlasWorld Studio and may only be used internally
  within the organization obtaining the software. Any commercial use, copying, modification,
  distribution, or exploitation of the software requires express written permission from AtlasWorld Studio.
*/
package fr.atlasworld.common.compound;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public interface CompoundArray extends CompoundElement, Iterable<CompoundElement> {

    /**
     * Add a new element to the array.
     * <p>
     * Helper method for chaining methods when creating new compounds.
     *
     * @param builder consumer that initializes the JsonObject's data.
     * @return instance of this array.
     * @throws NullPointerException if {@code builder} is null.
     */
    CompoundArray addObject(@NotNull Consumer<CompoundObject> builder);

    /**
     * Add a new element to the array.
     * <p>
     * Helper method for chaining methods when creating new compounds.
     *
     * @param builder consumer that initializes the JsonObject's data.
     * @return instance of this array.
     * @throws NullPointerException if {@code builder} is null.
     */
    CompoundArray addArray(@NotNull Consumer<CompoundArray> builder);

    /**
     * Add a new element to the array.
     *
     * @param value element to store
     * @return instance of this array.
     * @throws NullPointerException if {@code value} is null.
     */
    CompoundArray add(@NotNull CompoundElement value);

    /**
     * Add a new element to the array.
     *
     * @param value element to store
     * @return instance of this array.
     */
    CompoundArray add(boolean value);

    /**
     * Add a new element to the array.
     *
     * @param value element to store
     * @return instance of this array.
     */
    CompoundArray add(double value);

    /**
     * Add a new element to the array.
     *
     * @param value element to store
     * @return instance of this array.
     */
    CompoundArray add(long value);

    /**
     * Add a new element to the array.
     *
     * @param value element to store
     * @return instance of this array.
     */
    CompoundArray add(int value);

    /**
     * Add a new element to the array.
     *
     * @param value element to store
     * @return instance of this array.
     */
    CompoundArray add(byte value);

    /**
     * Add a new element to the array.
     *
     * @param value element to store
     * @return instance of this array.
     * @throws NullPointerException if {@code value} is null.
     */
    CompoundArray add(byte[] value);

    /**
     * Add a new element to the array.
     *
     * @param value element to store
     * @return instance of this array.
     */
    CompoundArray add(char value);

    /**
     * Add a new element to the array.
     *
     * @param value element to store
     * @return instance of this array.
     * @throws NullPointerException if {@code value} is null.
     */
    CompoundArray add(@NotNull String value);

    /**
     * Adds all the element of the provided array.
     *
     * @param array the array whose elements need to be added.
     * @return instance of this array.
     * @throws NullPointerException if {@code array} is null.
     */
    CompoundArray addAll(@NotNull CompoundArray array);

    /**
     * Replaces the element at the specified position.
     * <p>
     * Helper method for chaining methods when creating new compounds.
     *
     * @param index   index of the element to replace.
     * @param builder builder of the element to replace with.
     * @return the element previously stored at this position.
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds.
     * @throws NullPointerException      if {@code builder} is null.
     */
    CompoundElement setObject(int index, @NotNull Consumer<CompoundObject> builder);

    /**
     * Replaces the element at the specified position.
     * <p>
     * Helper method for chaining methods when creating new compounds.
     *
     * @param index   index of the element to replace.
     * @param builder builder of the element to replace with.
     * @return the element previously stored at this position.
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds.
     * @throws NullPointerException      if {@code builder} is null.
     */
    CompoundElement setArray(int index, @NotNull Consumer<CompoundArray> builder);

    /**
     * Replaces the element at the specified position.
     *
     * @param index   index of the element to replace.
     * @param element element to replace with.
     * @return the element previously stored at this position.
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds.
     * @throws NullPointerException      if {@code element} is null.
     */
    CompoundElement set(int index, @NotNull CompoundElement element);

    /**
     * Replaces the element at the specified position.
     *
     * @param index index of the element to replace.
     * @param value to replace with.
     * @return the element previously stored at this position.
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds.
     */
    CompoundElement set(int index, boolean value);

    /**
     * Replaces the element at the specified position.
     *
     * @param index index of the element to replace.
     * @param value element to replace with.
     * @return the element previously stored at this position.
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds.
     */
    CompoundElement set(int index, double value);

    /**
     * Replaces the element at the specified position.
     *
     * @param index index of the element to replace.
     * @param value element to replace with.
     * @return the element previously stored at this position.
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds.
     */
    CompoundElement set(int index, long value);

    /**
     * Replaces the element at the specified position.
     *
     * @param index index of the element to replace.
     * @param value element to replace with.
     * @return the element previously stored at this position.
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds.
     */
    CompoundElement set(int index, int value);

    /**
     * Replaces the element at the specified position.
     *
     * @param index index of the element to replace.
     * @param value element to replace with.
     * @return the element previously stored at this position.
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds.
     */
    CompoundElement set(int index, byte value);

    /**
     * Replaces the element at the specified position.
     *
     * @param index index of the element to replace.
     * @param value element to replace with.
     * @return the element previously stored at this position.
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds.
     * @throws NullPointerException      if {@code value} is null.
     */
    CompoundElement set(int index, byte[] value);

    /**
     * Replaces the element at the specified position.
     *
     * @param index index of the element to replace.
     * @param value element to replace with.
     * @return the element previously stored at this position.
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds.
     */
    CompoundElement set(int index, char value);

    /**
     * Replaces the element at the specified position.
     *
     * @param index index of the element to replace.
     * @param value element to replace with.
     * @return the element previously stored at this position.
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds.
     * @throws NullPointerException      if {@code value} is null.
     */
    CompoundElement set(int index, @NotNull String value);

    /**
     * Remove the first element that matches the provided element of the array.
     *
     * @param element element to be removed from this array
     * @return true if the element was in the array, false otherwise.
     * @throws NullPointerException if {@code element} is null.
     */
    boolean remove(@NotNull CompoundElement element);

    /**
     * Removes an element of the given index in the array.
     *
     * @param index index of the element to remove.
     * @return the previously stored element at the specified position.
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds.
     */
    CompoundElement remove(int index);

    /**
     * Checks whether this array contains the specified element.
     *
     * @param element element to check for.
     * @return true if this array contains the element, false otherwise.
     * @throws NullPointerException if {@code element} is null.
     */
    boolean contains(@NotNull CompoundElement element);

    /**
     * Retrieve the number of elements in this array.
     *
     * @return number of elements in this array.
     */
    int size();

    /**
     * Checks whether this array is empty.
     *
     * @return true if this array is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Retrieves the element at the index.
     *
     * @param index index of the element to retrieve.
     * @return the {@link CompoundElement} stored at that index.
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds.
     */
    CompoundElement get(int index);

    /**
     * Retrieve this compound array as a list.
     *
     * @return <strong>immutable</strong> list of the element contained in this array.
     */
    List<CompoundElement> asList();

    /**
     * Clones this compound.
     *
     * @return new clone of this array.
     */
    CompoundArray clone();
}
