package org.miaixz.bus.health;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisabledOnOs(OS.WINDOWS)
class IdGroupTest {

    @Test
    void testGetUser() {
        List<String> checkedUid = new ArrayList<>();
        List<String> passwd = Executor.runNative("getent passwd");
        passwd.stream().map(s -> s.split(":")).filter(arr -> arr.length > 2).forEach(split -> {
            String uid = split[2];
            String userName = split[0];
            if (!checkedUid.contains(uid)) {
                assertThat("Incorrect result for USER_ID_MAP", IdGroup.getUser(uid), is(userName));
                checkedUid.add(uid);
            }
        });
    }

    @Test
    void testGetGroupName() {
        List<String> checkedGid = new ArrayList<>();
        List<String> group = Executor.runNative("getent group");
        group.stream().map(s -> s.split(":")).filter(arr -> arr.length > 2).forEach(split -> {
            String gid = split[2];
            String groupName = split[0];
            if (!checkedGid.contains(gid)) {
                assertThat("Incorrect result for GROUPS_ID_MAP", IdGroup.getGroupName(gid), is(groupName));
                checkedGid.add(gid);
            }
        });
    }

}
