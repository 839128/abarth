package org.miaixz.bus.health;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.xyz.ByteKit;
import org.miaixz.bus.core.xyz.StringKit;

import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/*
 * Tests Builder
 */
public class BuilderTest {

    public static final String UUID_REGEX = "\\b[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-\\b[0-9a-fA-F]{12}\\b";

    public static final String EDID_HEADER = "00FFFFFFFFFFFF00";
    public static final String EDID_MANUFID = "0610";
    public static final String EDID_PRODCODE = "2792";
    public static final String EDID_SERIAL = "250C2C16";
    public static final String EDID_WKYR = "2C16";
    public static final String EDID_VERSION = "0104";
    public static final String EDID_VIDEO = "B53C2278226FB1A7554C9E250C5054000000";
    public static final String EDID_TIMING = "01010101010101010101010101010101";
    public static final String EDID_DESC1 = "565E00A0A0A029503020350055502100001A";
    public static final String EDID_DESC2 = "1A1D008051D01C204080350055502100001C";
    public static final String EDID_DESC3 = "000000FF004330324A4D325046463247430A";
    public static final String EDID_DESC4 = "000000FC005468756E646572626F6C740A20";
    public static final String EDID_DESC5 = "000000FA004330324A4D325046463247430A";
    public static final String EDID_DESC6 = "000000FB005468756E646572626F6C740A20";
    public static final String EDID_DESC7 = "000000FD004330324A4D325046463247430A";
    public static final String EDID_DESC8 = "000000FE005468756E646572626F6C740A20";
    public static final String EDID_EXTS = "01";
    public static final String EDID_CHKSUM = "C7";
    public static final String EDID_STR = EDID_HEADER + EDID_MANUFID + EDID_PRODCODE + EDID_SERIAL + EDID_WKYR
            + EDID_VERSION + EDID_VIDEO + EDID_TIMING + EDID_DESC1 + EDID_DESC2 + EDID_DESC3 + EDID_DESC4 + EDID_EXTS
            + EDID_CHKSUM;
    public static final byte[] EDID = ByteKit.hexStringToByteArray(EDID_STR);
    public static final String EDID_STR2 = EDID_HEADER + EDID_MANUFID + EDID_PRODCODE + EDID_SERIAL + EDID_WKYR
            + EDID_VERSION + EDID_VIDEO + EDID_TIMING + EDID_DESC5 + EDID_DESC6 + EDID_DESC7 + EDID_DESC8 + EDID_EXTS
            + EDID_CHKSUM;

    @Test
    void testGetEdidAttrs() {
        assertThat("manufacturerId", Builder.getManufacturerID(EDID), is("A"));
        assertThat("productId", Builder.getProductID(EDID), is("9227"));
        assertThat("serialNo", Builder.getSerialNo(EDID), is("162C0C25"));
        assertThat("week", Builder.getWeek(EDID), is((byte) 44));
        assertThat("year", Builder.getYear(EDID), is(2012));
        assertThat("version", Builder.getVersion(EDID), is("1.4"));
        assertThat("digital", Builder.isDigital(EDID), is(true));
        assertThat("hcm", Builder.getHcm(EDID), is(60));
        assertThat("vcm", Builder.getVcm(EDID), is(34));
    }

    @Test
    void testGetDescriptors() {
        byte[][] descs = Builder.getDescriptors(EDID);
        for (int i = 0; i < 4; i++) {
            int type = Builder.getDescriptorType(descs[i]);
            String timing = Builder.getTimingDescriptor(descs[i]);
            String range = Builder.getDescriptorRangeLimits(descs[i]);
            switch (i) {
                case 0:
                    assertThat("desc 0 type", type, is(0x565E00A0));
                    assertThat("desc 0 timing", timing, is("Clock 241MHz, Active Pixels 2560x1440 "));
                    assertThat("desc 0 range", range,
                            is("Field Rate -96-41 Hz vertical, 80-48 Hz horizontal, Max clock: 320 MHz"));
                    break;
                case 1:
                    assertThat("desc 1 type", type, is(0x1A1D0080));
                    assertThat("desc 1 timing", timing, is("Clock 74MHz, Active Pixels 1280x720 "));
                    assertThat("desc 1 range", range,
                            is("Field Rate -48-28 Hz vertical, 32-64 Hz horizontal, Max clock: -1280 MHz"));
                    break;
                case 2:
                    assertThat("desc 2 type", type, is(0xFF));
                    assertThat("desc 2 descriptorText", Builder.getDescriptorText(descs[i]), is("C02JM2PFF2GC"));
                    assertThat("desc 2 descriptorHex", ByteKit.byteArrayToHexString(descs[i]), is(EDID_DESC3));
                    break;
                case 3:
                    assertThat("desc 3 type", type, is(0xFC));
                    assertThat("desc 3 descriptorText", Builder.getDescriptorText(descs[i]), is("Thunderbolt"));
                    break;
                default:
            }
        }
    }

    @Test
    void testToString() {
        assertThat("edid toString", StringKit.toString(EDID).split("\\n"), is(arrayWithSize(6)));
        assertThat("edid2 toString", StringKit.toString(ByteKit.hexStringToByteArray(EDID_STR2)).split("\\n"),
                is(arrayWithSize(6)));
    }

    /**
     * If no configuration is provided (in oshi.properties) then file store is included by default.
     */
    @Test
    void testIsFileStoreIncludedByDefault() {
        assertThat("file store included by default", !Builder.isFileStoreExcluded("/any-path", "/any-volume",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
    }

    /**
     * Test path includes and excludes.
     */
    @Test
    void testIsFileStoreExcludedSimple() {
        List<PathMatcher> pathExcludes = Builder.parseFileSystemConfig("excluded-path");
        List<PathMatcher> pathIncludes = Builder.parseFileSystemConfig("included-path");
        List<PathMatcher> volumeExcludes = Builder.parseFileSystemConfig("excluded-volume");
        List<PathMatcher> volumeIncludes = Builder.parseFileSystemConfig("included-volume");

        assertThat("excluded excluded-path", Builder.isFileStoreExcluded("excluded-path", "", pathIncludes,
                pathExcludes, volumeIncludes, volumeExcludes));
        assertThat("included included-path", !Builder.isFileStoreExcluded("included-path", "", pathIncludes,
                pathExcludes, volumeIncludes, volumeExcludes));

        assertThat("excluded excluded-volume", Builder.isFileStoreExcluded("", "excluded-volume", pathIncludes,
                pathExcludes, volumeIncludes, volumeExcludes));
        assertThat("included included-volume", !Builder.isFileStoreExcluded("", "included-volume", pathIncludes,
                pathExcludes, volumeIncludes, volumeExcludes));
    }

    /**
     * Test that includes has priority over excludes.
     */
    @Test
    void testIsFileStoreExcludedPriority() {
        List<PathMatcher> pathExcludes = Builder.parseFileSystemConfig("path,path-excluded");
        List<PathMatcher> pathIncludes = Builder.parseFileSystemConfig("path");
        List<PathMatcher> volumeExcludes = Builder.parseFileSystemConfig("volume,volume-excluded");
        List<PathMatcher> volumeIncludes = Builder.parseFileSystemConfig("volume");

        assertThat("excluded path-exclude", Builder.isFileStoreExcluded("path-excluded", "", pathIncludes,
                pathExcludes, volumeIncludes, volumeExcludes));
        // "path" is both included and excluded and since included has priority, it
        // should be included
        assertThat("included path", !Builder.isFileStoreExcluded("path", "", pathIncludes, pathExcludes,
                volumeIncludes, volumeExcludes));

        assertThat("excluded volume-excluded", Builder.isFileStoreExcluded("", "volume-excluded", pathIncludes,
                pathExcludes, volumeIncludes, volumeExcludes));
        // "volume" is both included and excluded and since included has priority, it
        // should be included
        assertThat("included volume", !Builder.isFileStoreExcluded("", "volume", pathIncludes, pathExcludes,
                volumeIncludes, volumeExcludes));
    }

    @Test
    void testParseFileSystemConfigSimple() {
        List<PathMatcher> matchers = Builder.parseFileSystemConfig("simple-path");
        assertThat("simple-path is matched", Builder.matches(Paths.get("simple-path"), matchers));
        assertThat("other-path is not matched", !Builder.matches(Paths.get("other-path"), matchers));
    }

    @Test
    void testWithGlobPrefix() {
        List<PathMatcher> matchers = Builder.parseFileSystemConfig("glob:simple-path");
        assertThat("simple-path is matched", Builder.matches(Paths.get("simple-path"), matchers));
        assertThat("other-path is not matched", !Builder.matches(Paths.get("other-path"), matchers));
    }

    @Test
    void testWithMoreItems() {
        List<PathMatcher> matchers = Builder.parseFileSystemConfig("simple-path1,simple-path2,simple-path3");
        assertThat("simple-path1 is matched", Builder.matches(Paths.get("simple-path1"), matchers));
        assertThat("simple-path2 is matched", Builder.matches(Paths.get("simple-path2"), matchers));
        assertThat("simple-path3 is matched", Builder.matches(Paths.get("simple-path3"), matchers));
        assertThat("other-path is not matched", !Builder.matches(Paths.get("other-path"), matchers));
    }

    @Test
    void testWithMultiDirPattern() {
        List<PathMatcher> matchers = Builder.parseFileSystemConfig("**/complex-path");
        assertThat("/complex-path is matched", Builder.matches(Paths.get("/complex-path"), matchers));
        assertThat("/var/complex-path is matched", Builder.matches(Paths.get("/var/complex-path"), matchers));
        assertThat("other-path is not matched", !Builder.matches(Paths.get("other-path"), matchers));
    }

    @Test
    void testWithSuffixPattern() {
        List<PathMatcher> matchers = Builder.parseFileSystemConfig("suffix-path*");
        assertThat("suffix-path is matched", Builder.matches(Paths.get("suffix-path"), matchers));
        assertThat("suffix-path/ is matched", Builder.matches(Paths.get("suffix-path/"), matchers));
        assertThat("suffix-path1 is matched", Builder.matches(Paths.get("suffix-path1"), matchers));
        assertThat("suffix-path/a is not matched", !Builder.matches(Paths.get("suffix-path/a"), matchers));
        assertThat("suffix-path/a/b/c is not matched",
                !Builder.matches(Paths.get("suffix-path/a/b/c"), matchers));
        assertThat("123-suffix-path is not matched", !Builder.matches(Paths.get("123-suffix-path"), matchers));
    }

    @Test
    void testWithSuffixMultiDirPattern() {
        List<PathMatcher> matchers = Builder.parseFileSystemConfig("suffix-path/**");
        assertThat("suffix-path/ is not matched", !Builder.matches(Paths.get("suffix-path/"), matchers));
        assertThat("suffix-path/a is matched", Builder.matches(Paths.get("suffix-path/a"), matchers));
        assertThat("suffix-path/a/b/c is matched", Builder.matches(Paths.get("suffix-path/a/b/c"), matchers));
        assertThat("123-suffix-path is not matched", !Builder.matches(Paths.get("123-suffix-path"), matchers));
    }

    @Test
    void testSleep() {
        // Windows counters may be up to 1/64 second (16ms) off
        long now = System.nanoTime();
        Builder.sleep(100);
        assertThat(System.nanoTime() - now, is(greaterThan(84_375_000L)));
    }

    @Test
    void testWildcardMatch() {
        assertThat("Test should not match est", Builder.wildcardMatch("Test", "est"), is(false));
        assertThat("Test should match ^est", Builder.wildcardMatch("Test", "^est"), is(true));
        assertThat("Test should not match ^^est", Builder.wildcardMatch("Test", "^^est"), is(false));
        assertThat("Test should match ?est", Builder.wildcardMatch("Test", "?est"), is(true));
        assertThat("Test should not match ^?est", Builder.wildcardMatch("Test", "^?est"), is(false));
        assertThat("Test should match *est", Builder.wildcardMatch("Test", "*est"), is(true));
        assertThat("Test should not match ^*est", Builder.wildcardMatch("Test", "^*est"), is(false));

        assertThat("Test should not match T?t", Builder.wildcardMatch("Test", "T?t"), is(false));
        assertThat("Test should match T??t", Builder.wildcardMatch("Test", "T??t"), is(true));
        assertThat("Test should match T*t", Builder.wildcardMatch("Test", "T*t"), is(true));

        assertThat("Test should not match Tes", Builder.wildcardMatch("Test", "Tes"), is(false));
        assertThat("Test should match Tes?", Builder.wildcardMatch("Test", "Tes?"), is(true));
        assertThat("Test should match Tes*", Builder.wildcardMatch("Test", "Tes*"), is(true));

        assertThat("Test should not match Te?", Builder.wildcardMatch("Test", "Te?"), is(false));
        assertThat("Test should match Te*", Builder.wildcardMatch("Test", "Te*"), is(true));
    }

    @Test
    void testIsSessionValid() {
        assertThat("Session is invalid because user is empty", Builder.isSessionValid("", "device", (long) 0), is(false));
        assertThat("Session is invalid because device is empty", Builder.isSessionValid("user", "", (long) 0), is(false));
        assertThat("Session is invalid because loginTime is greater than current system time",
                Builder.isSessionValid("user", "device", Long.MAX_VALUE), is(false));
        assertThat("Session is invalid because loginTime is lesser than zero",
                Builder.isSessionValid("user", "device", Long.MIN_VALUE), is(false));
        assertThat("Session is valid because all the arguments are appropriate",
                Builder.isSessionValid("user", "device", (long) 999999999), is(true));
    }

}
