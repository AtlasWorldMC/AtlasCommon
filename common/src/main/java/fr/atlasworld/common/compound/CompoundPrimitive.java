/*
  AtlasWorld's Proprietary License

  Copyright (c) 2022 - 2024 AtlasWorld Studio. All Rights Reserved.

  This software is proprietary to AtlasWorld Studio and may only be used internally
  within the organization obtaining the software. Any commercial use, copying, modification,
  distribution, or exploitation of the software requires express written permission from AtlasWorld Studio.
*/
package fr.atlasworld.common.compound;

public interface CompoundPrimitive extends CompoundElement {

    /**
     * Checks whether this primitive is a {@code boolean}.
     *
     * @return true if this primitive is a {@code boolean}, false otherwise.
     */
    boolean isBoolean();

    /**
     * Checks whether this primitive is a {@code double}.
     *
     * @return true if this primitive is a {@code double}, false otherwise.
     */
    boolean isDouble();

    /**
     * Checks whether this primitive is a {@code long}.
     *
     * @return true if this primitive is a {@code long}, false otherwise.
     */
    boolean isLong();

    /**
     * Checks whether this primitive is an {@code int}.
     *
     * @return true if this primitive is an {@code int}, false otherwise.
     */
    boolean isInt();

    /**
     * Checks whether this primitive is a {@code byte}.
     *
     * @return true if this primitive is a {@code byte}, false otherwise.
     */
    boolean isByte();

    /**
     * Checks whether this primitive is a byte-array.
     *
     * @return true if this primitive is a byte-array, false otherwise.
     */
    boolean isByteArray();

    /**
     * Checks whether this primitive is a {@link java.util.Date}.
     *
     * @return true if this primitive is a {@link java.util.Date}, false otherwise.
     */
    boolean isDate();

    /**
     * Checks whether this primitive is a {@code char}.
     *
     * @return true if this primitive is a {@code char}, false otherwise.
     */
    boolean isChar();

    /**
     * Checks whether this primitive is a string.
     *
     * @return true if this primitive is a string, false otherwise.
     */
    boolean isString();

    /**
     * Clones this compound.
     *
     * @return new clone of this primitive.
     */
    CompoundPrimitive clone();
}
