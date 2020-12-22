package org.aoju.bus.health.software;

import org.aoju.bus.health.Builder;
import org.aoju.bus.health.builtin.software.NetworkParams;
import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertNotNull(params.getHostName());
        Assert.assertNotNull(params.getDomainName());
        Assert.assertNotNull(params.getDnsServers());
        Assert.assertNotNull(params.getIpv4DefaultGateway());
        Assert.assertNotNull(params.getIpv6DefaultGateway());
    }

}
