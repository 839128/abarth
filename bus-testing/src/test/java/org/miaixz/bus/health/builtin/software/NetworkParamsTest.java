/*
 * Copyright 2017-2022 The OSHI Project Contributors
 * SPDX-License-Identifier: MIT
 */
package org.miaixz.bus.health.builtin.software;

import org.junit.jupiter.api.Test;
import org.miaixz.bus.health.Platform;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Test network parameters
 */
class NetworkParamsTest {

    /**
     * Test network parameters
     */
    @Test
    void testNetworkParams() {
        Platform si = new Platform();
        NetworkParams params = si.getOperatingSystem().getNetworkParams();
        assertThat("Network parameters hostname is null.", params.getHostName(), is(notNullValue()));
        assertThat("Network parameters domain name is null.", params.getDomainName(), is(notNullValue()));
        assertThat("Network parameters DNS server is null.", params.getDnsServers(), is(notNullValue()));
        assertThat("Network parameters IPv4 default gateway is null.", params.getIpv4DefaultGateway(),
                is(notNullValue()));
        assertThat("Network parameters IPv6 default gateway is null.", params.getIpv6DefaultGateway(),
                is(notNullValue()));
    }

}
