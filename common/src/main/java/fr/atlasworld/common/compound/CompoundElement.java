/*
  AtlasWorld's Proprietary License

  Copyright (c) 2022 - 2024 AtlasWorld Studio. All Rights Reserved.

  This software is proprietary to AtlasWorld Studio and may only be used internally
  within the organization obtaining the software. Any commercial use, copying, modification,
  distribution, or exploitation of the software requires express written permission from AtlasWorld Studio.
*/
package fr.atlasworld.common.compound;

import java.util.Date;

/**
 * Class representing an element of a network compound.
 *
 * @see CompoundObject
 * @see CompoundArray
 * @see CompoundPrimitive
 * @see CompoundNull
 */
public interface CompoundElement extends Cloneable {

    /**
     * Checks whether this element is a {@link CompoundArray} or not.
     *
     * @return true if this element is a {@link CompoundArray}, false otherwise.
     */
    boolean isArray();

    /**
     * Checks whether this element is a {@link CompoundObject} or not.
     *
     * @return true if this element is a {@link CompoundObject}, false otherwise.
     */
    boolean isObject();

    /**
     * Checks whether this element is a {@link CompoundPrimitive} or not.
     *
     * @return true if this element is a {@link CompoundPrimitive}, false otherwise.
     */
    boolean isPrimitive();

    /**
     * Checks whether this element is a {@link CompoundNull} or not.
     *
     * @return true if this element is a {@link CompoundNull}, false otherwise.
     */
    boolean isNull();

    /**
     * Retrieve the compound as a {@link CompoundArray}.
     *
     * @return this element as a {@link CompoundArray}.
     * @throws IllegalStateException if this element is not a {@link CompoundArray}.
     */
    CompoundArray getAsArray();

    /**
     * Retrieve the compound as a {@link CompoundObject}.
     *
     * @return this element as a {@link CompoundObject}.
     * @throws IllegalStateException if this element is not a {@link CompoundObject}.
     */
    CompoundObject getAsObject();

    /**
     * Retrieve the compound as a {@link CompoundPrimitive}.
     *
     * @return this element as a {@link CompoundPrimitive}.
     * @throws IllegalStateException if this element is not a {@link CompoundPrimitive}.
     */
    CompoundPrimitive getAsPrimitive();

    /**
     * Retrieve the compound as a {@link CompoundNull}.
     *
     * @return this element as a {@link CompoundNull}.
     * @throws IllegalStateException if this element is not a {@link CompoundNull}.
     */
    CompoundNull getAsNull();

    /**
     * Retrieve this element as a {@code boolean} value.
     * <p>
     * If this element is not a {@code boolean},
     * the {@code string} value will be parsed, and any other value is considered as {@code false}
     *
     * @return this element as a {@code boolean} value.
     * @throws UnsupportedOperationException if this element is not a {@link CompoundPrimitive} or a {@code boolean}.
     */
    boolean getAsBoolean();

    /**
     * Retrieve this element as a 64-Bit {@code double} value.
     *
     * @return this element as a {@code double} value.
     * @throws UnsupportedOperationException if this element is not a {@link CompoundPrimitive} or a {@code double}.
     */
    double getAsDouble();

    /**
     * Retrieve this element as a 64-Bit {@code long} value.
     *
     * @return this element as a {@code long} value.
     * @throws UnsupportedOperationException if this element is not a {@link CompoundPrimitive} or a {@code long}.
     */
    long getAsLong();

    /**
     * Retrieve this element as a 32-Bit {@code int} value.
     *
     * @return this element as a {@code int} value.
     * @throws UnsupportedOperationException if this element is not a {@link CompoundPrimitive} or an {@code int}.
     */
    int getAsInt();

    /**
     * Retrieve this element as an 8-Bit {@code byte} value.
     *
     * @return this element as a {@code byte} value.
     * @throws UnsupportedOperationException if this element is not a {@link CompoundPrimitive} or a {@code byte}.
     */
    byte getAsByte();

    /**
     * Retrieve this element as a byte array.
     *
     * @return this element as a byte array.
     * @throws UnsupportedOperationException if this element is not a {@link CompoundPrimitive} or a byte array.
     */
    byte[] getAsByteArray();

    /**
     * Retrieve this element as a {@link Date}.
     *
     * @return this element as a {@link Date}.
     * @throws UnsupportedOperationException if this element is not a {@link CompoundPrimitive} or a {@link Date}.
     */
    Date getAsDate();

    /**
     * Retrieve this element as a char.
     *
     * @return this element as a char.
     * @throws UnsupportedOperationException if this element is not a {@link CompoundPrimitive} or a char.
     */
    char getAsChar();

    /**
     * Retrieve this element as a string.
     *
     * @return this element as a string.
     * @throws UnsupportedOperationException if this element is not a {@link CompoundPrimitive} or a string.
     */
    String getAsString();

    /**
     * Convert this element into {@code JSON}.
     *
     * @return this element as a {@code JSON}.
     */
    String toJson();

    /**
     * Clones this compound.
     *
     * @return new clone of this element.
     */
    CompoundElement clone();
}
