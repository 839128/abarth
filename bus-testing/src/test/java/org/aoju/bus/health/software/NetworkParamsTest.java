package org.aoju.bus.health.software;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.software.NetworkParams;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test network parameters
 */
public class NetworkParamsTest {

    /**
     * Test network parameters
     */
    @Test
    public void testNetworkParams() {
        NetworkParams params = Builder.getOs().getNetworkParams();
        assertNotNull(params.getHostName());
        assertNotNull(params.getDomainName());
        assertNotNull(params.getDnsServers());
        assertNotNull(params.getIpv4DefaultGateway());
        assertNotNull(params.getIpv6DefaultGateway());
    }

}
