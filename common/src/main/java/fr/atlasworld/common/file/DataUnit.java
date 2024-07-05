package fr.atlasworld.common.file;

public final class DataUnit {
    public static final DataUnit BYTES = new DataUnit("Byte", "b", 1);
    public static final DataUnit KB = new DataUnit("Kilobyte", "kb", BYTES.multiplier * 1024);
    public static final DataUnit MB = new DataUnit("Megabyte", "mb", KB.multiplier * 1024);
    public static final DataUnit GB = new DataUnit("Gigabyte", "gb", MB.multiplier * 1024);
    public static final DataUnit TB = new DataUnit("Terabyte", "tb", GB.multiplier * 1024);
    public static final DataUnit PB = new DataUnit("Petabyte", "pb", TB.multiplier * 1024);

    private final String displayName;
    private final String shortName;
    private final long multiplier;

    private DataUnit(String displayName, String shortName, long multiplier) {
        this.displayName = displayName;
        this.shortName = shortName;
        this.multiplier = multiplier;
    }

    public String displayName() {
        return displayName;
    }

    public String shortUnit() {
        return shortName;
    }

    public long multiplier() {
        return multiplier;
    }

    public long convert(long value, DataUnit sourceUnit) {
        long byteSize = value * sourceUnit.multiplier;
        return byteSize / this.multiplier;
    }
}
