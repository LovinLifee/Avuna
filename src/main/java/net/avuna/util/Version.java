package net.avuna.util;

import java.util.Objects;

public class Version implements Comparable<Version> {

    private String version;

    public Version(String version) {
        if(!version.matches("[0-9]+(\\.[0-9]+)*")) {
            throw new IllegalArgumentException("Invalid version format");
        }
        this.version = version;
    }

    @Override
    public final String toString() {
        return this.version;
    }

    @Override
    public int compareTo(Version that) {
        if(that == null)
            return 1;
        String[] thisParts = this.toString().split("\\.");
        String[] thatParts = that.toString().split("\\.");
        int length = Math.max(thisParts.length, thatParts.length);
        for(int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
            if(thisPart < thatPart)
                return -1;
            if(thisPart > thatPart)
                return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Version that = (Version) obj;
        return Objects.equals(version, that.version);
    }
}