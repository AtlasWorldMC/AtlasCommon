/*
  AtlasWorld's Proprietary License

  Copyright (c) 2022 - 2024 AtlasWorld Studio. All Rights Reserved.

  This software is proprietary to AtlasWorld Studio and may only be used internally
  within the organization obtaining the software. Any commercial use, copying, modification,
  distribution, or exploitation of the software requires express written permission from AtlasWorld Studio.
*/
package fr.atlasworld.common.compound;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public interface CompoundObject extends CompoundElement {

    /**
     * Remove an entry of the compound.
     *
     * @param key key of the entry that should be removed.
     * @return the {@link CompoundElement} object that is being removed,
     * or {@link CompoundNull} if no entry with this key exists.
     * @throws NullPointerException if {@code key} is null.
     */
    CompoundElement remove(@NotNull String key);

    /**
     * Add a new entry to the compound.
     * <p>
     * Helper method for chaining methods when creating new compound.
     *
     * @param key     key of the entry.
     * @param builder consumer that initializes the JsonObject's data.
     * @return instance of this object.
     * @throws NullPointerException if {@code key} or {@code builder} is null.
     */
    CompoundObject addObject(@NotNull String key, @NotNull Consumer<CompoundObject> builder);

    /**
     * Add a new entry to the compound.
     * <p>
     * Helper method for chaining methods when creating new compound.
     *
     * @param key     key of the entry.
     * @param builder consumer that initializes the JsonObject's data.
     * @return instance of this object.
     * @throws NullPointerException if {@code key} or {@code builder} is null.
     */
    CompoundObject addArray(@NotNull String key, @NotNull Consumer<CompoundArray> builder);

    /**
     * Add a new entry to the compound.
     *
     * @param key   key of the entry.
     * @param value value of the entry.
     * @return instance of this object.
     * @throws NullPointerException if {@code key} or {@code value} is null.
     */
    CompoundObject add(@NotNull String key, @NotNull CompoundElement value);

    /**
     * Add a new entry to the compound.
     *
     * @param key   key of the entry.
     * @param value value of the entry.
     * @return instance of this object.
     * @throws NullPointerException if {@code key} is null.
     */
    CompoundObject add(@NotNull String key, boolean value);

    /**
     * Add a new entry to the compound.
     *
     * @param key   key of the entry.
     * @param value value of the entry.
     * @return instance of this object.
     * @throws NullPointerException if {@code key} is null.
     */
    CompoundObject add(@NotNull String key, double value);

    /**
     * Add a new entry to the compound.
     *
     * @param key   key of the entry.
     * @param value value of the entry.
     * @return instance of this object.
     * @throws NullPointerException if {@code key} is null.
     */
    CompoundObject add(@NotNull String key, long value);

    /**
     * Add a new entry to the compound.
     *
     * @param key   key of the entry.
     * @param value value of the entry.
     * @return instance of this object.
     * @throws NullPointerException if {@code key} is null.
     */
    CompoundObject add(@NotNull String key, int value);

    /**
     * Add a new entry to the compound.
     *
     * @param key   key of the entry.
     * @param value value of the entry.
     * @return instance of this object.
     * @throws NullPointerException if {@code key} is null.
     */
    CompoundObject add(@NotNull String key, byte value);

    /**
     * Add a new entry to the compound.
     *
     * @param key   key of the entry.
     * @param value value of the entry.
     * @return instance of this object.
     * @throws NullPointerException if {@code key} or {@code value} is null.
     */
    CompoundObject add(@NotNull String key, byte[] value);

    /**
     * Add a new entry to the compound.
     *
     * @param key   key of the entry.
     * @param value value of the entry.
     * @return instance of this object.
     * @throws NullPointerException if {@code key} is null.
     */
    CompoundObject add(@NotNull String key, char value);

    /**
     * Add a new entry to the compound.
     *
     * @param key   key of the entry.
     * @param value value of the entry.
     * @return instance of this object.
     * @throws NullPointerException if {@code key} or {@code value} is null.
     */
    CompoundObject add(@NotNull String key, @NotNull String value);

    /**
     * Retrieve a set of entries of this object.
     *
     * @return set of the entries of this object.
     */
    Set<Map.Entry<String, CompoundElement>> entrySet();

    /**
     * Retrieve the number of entries in this object.
     *
     * @return number of entries in this object.
     */
    int size();

    /**
     * Checks whether there a no entries in this object.
     *
     * @return true if no entries are in this object, false otherwise.
     */
    boolean isEmpty();

    /**
     * Checks whether an entry with the specified name exists.
     *
     * @param key key of entry that is being checked.
     * @return true if there is en entry with the specified key, false otherwise.
     * @throws NullPointerException if {@code key} is null.
     */
    boolean has(@NotNull String key);

    /**
     * Retrieve the compound element with the specified key.
     *
     * @param key key of the entry being requested.
     * @return the {@link CompoundElement} of the matching name,
     * or {@link CompoundNull} if no such member exists.
     * @throws NullPointerException if {@code key} is null.
     */
    CompoundElement get(@NotNull String key);

    /**
     * Retrieve the compound element as a {@link CompoundPrimitive} with the specified key.
     *
     * @param key key of the entry being requested.
     * @return the {@link CompoundPrimitive}, or null if the requested entry does not exist.
     * @throws NullPointerException  if {@code key} is null.
     * @throws IllegalStateException if the element is not a {@link CompoundPrimitive}.
     */
    CompoundPrimitive getAsPrimitive(@NotNull String key);

    /**
     * Retrieve the compound element as a {@link CompoundArray} with the specified key.
     *
     * @param key key of the entry being requested.
     * @return the {@link CompoundArray}, or null if the requested entry does not exist.
     * @throws NullPointerException  if {@code key} is null.
     * @throws IllegalStateException if the element is not a {@link CompoundArray}.
     */
    CompoundArray getAsArray(@NotNull String key);

    /**
     * Retrieve the compound element as a {@link CompoundObject} with the specified key.
     *
     * @param key key of the entry being requested.
     * @return the {@link CompoundObject}, or null if the requested entry does not exist.
     * @throws NullPointerException  if {@code key} is null.
     * @throws IllegalStateException if the element is not a {@link CompoundObject}.
     */
    CompoundObject getAsObject(@NotNull String key);

    /**
     * Get this object's entries as a map.
     *
     * @return a <strong>immutable</strong> map of the entries of this object.
     */
    Map<String, CompoundElement> asMap();

    /**
     * Clones this compound.
     *
     * @return new clone of this object.
     */
    CompoundObject clone();
}
