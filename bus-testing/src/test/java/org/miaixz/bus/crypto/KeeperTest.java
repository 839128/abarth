package org.miaixz.bus.crypto;

import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.miaixz.bus.core.lang.exception.CryptoException;
import org.miaixz.bus.core.xyz.ByteKit;
import org.miaixz.bus.core.xyz.FileKit;
import org.miaixz.bus.core.xyz.ResourceKit;
import org.miaixz.bus.crypto.builtin.asymmetric.KeyType;
import org.miaixz.bus.crypto.center.RSA;
import org.miaixz.bus.crypto.center.SM2;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeeperTest {

    @Test
    public void readPrivateKeyTest() {
        final PrivateKey privateKey = Keeper.readPemPrivateKey(ResourceKit.getStream("/test/crypto/test_private_key.pem"));
        Assertions.assertNotNull(privateKey);
    }

    @Test
    public void readPublicKeyTest() {
        final PublicKey publicKey = Keeper.readPemPublicKey(ResourceKit.getStream("/test/crypto/test_public_key.csr"));
        Assertions.assertNotNull(publicKey);
    }

    @Test
    public void readPemKeyTest() {
        final PublicKey publicKey = (PublicKey) Keeper.readPemKey(ResourceKit.getStream("/test/crypto/test_public_key.csr"));
        Assertions.assertNotNull(publicKey);
    }

    @Test
    public void validateKey() {
        final PrivateKey privateKey = Keeper.readPemPrivateKey(ResourceKit.getStream("/test/crypto/test_private_key.pem"));
        final PublicKey publicKey = Keeper.readPemPublicKey(ResourceKit.getStream("/test/crypto/test_public_key.csr"));

        final RSA rsa = new RSA(privateKey, publicKey);
        final String str = "你好";//测试字符串

        final String encryptStr = rsa.encryptBase64(str, KeyType.PublicKey);
        final String decryptStr = rsa.decryptString(encryptStr, KeyType.PrivateKey);
        Assertions.assertEquals(str, decryptStr);
    }

    @Test
    public void readECPrivateKeyTest() {
        final PrivateKey privateKey = Keeper.readPemPrivateKey(ResourceKit.getStream("/test/crypto/test_ec_private_key.pem"));
        final SM2 sm2 = new SM2(privateKey, null);
        sm2.usePlainEncoding();

        //需要签名的明文,得到明文对应的字节数组
        final byte[] dataBytes = "我是一段测试aaaa".getBytes(StandardCharsets.UTF_8);

        final byte[] sign = sm2.sign(dataBytes, null);
        // 64位签名
        Assertions.assertEquals(64, sign.length);
    }

    @Test
    public void readECSec1PrivateKeyTest() {
        final PrivateKey privateKey = Keeper.readPemPrivateKey(ResourceKit.getStream("/test/crypto/test_ec_sec1_private_key.pem"));
        final SM2 sm2 = new SM2(privateKey, null);
        sm2.usePlainEncoding();

        //需要签名的明文,得到明文对应的字节数组
        final byte[] dataBytes = "我是一段测试aaaa".getBytes(StandardCharsets.UTF_8);

        final byte[] sign = sm2.sign(dataBytes, null);
        // 64位签名
        Assertions.assertEquals(64, sign.length);
    }

    @Test
    @Disabled
    public void readECPrivateKeyTest2() {
        final byte[] d = Keeper.readPem(FileKit.getInputStream("/test/crypto/priv.key"));
        final byte[] publicKey = Keeper.readPem(FileKit.getInputStream("/test/crypto/pub.key"));

        final SM2 sm2 = new SM2(d, publicKey);
        sm2.usePlainEncoding();

        final String content = "我是Hanley.";
        final byte[] sign = sm2.sign(ByteKit.toBytes(content));
        final boolean verify = sm2.verify(ByteKit.toBytes(content), sign);
        Assertions.assertTrue(verify);
    }

    /**
     * 密钥生成来自：<a href="https://i.goto327.top/CryptTools/SM2.aspx?tdsourcetag=s_pctim_aiomsg">...</a>
     */
    @Test
    public void createECPublicKeyParametersTest() {
        final String x = "706AD9DAA3E5CEAC3DA59F583429E8043BAFC576BE10092C4EA4D8E19846CA62";
        final String y = "F7E938B02EED7280277493B8556E5B01CB436E018A562DFDC53342BF41FDF728";

        final ECPublicKeyParameters keyParameters = Keeper.toSm2PublicParams(x, y);
        Assertions.assertNotNull(keyParameters);
    }

    @Test
    public void createECPrivateKeyParametersTest() {
        final String privateKeyHex = "5F6CA5BB044C40ED2355F0372BF72A5B3AE6943712F9FDB7C1FFBAECC06F3829";

        final ECPrivateKeyParameters keyParameters = Keeper.toSm2PrivateParams(privateKeyHex);
        Assertions.assertNotNull(keyParameters);
    }

    /**
     * 测试关闭BouncyCastle支持时是否会正常抛出异常，即关闭是否有效
     */
    @Test
    @Disabled
    public void generateKeyPairTest() {
        Assertions.assertThrows(CryptoException.class, () -> {
            Holder.setUseCustomProvider(false);
            final KeyPair pair = Keeper.generateKeyPair("SM2");
            Assertions.assertNotNull(pair);
        });
    }

    @Test
    public void getRSAPublicKeyTest() {
        final KeyPair keyPair = Keeper.generateKeyPair("RSA");
        final PrivateKey aPrivate = keyPair.getPrivate();
        final PublicKey rsaPublicKey = Keeper.getRSAPublicKey(aPrivate);
        Assertions.assertEquals(rsaPublicKey, keyPair.getPublic());
    }

    /**
     * 测试EC和ECIES算法生成的KEY是一致的
     */
    @Test
    public void generateECIESKeyTest() {
        final KeyPair ecies = Keeper.generateKeyPair("ECIES");
        Assertions.assertNotNull(ecies.getPrivate());
        Assertions.assertNotNull(ecies.getPublic());

        final byte[] privateKeyBytes = ecies.getPrivate().getEncoded();

        final PrivateKey privateKey = Keeper.generatePrivateKey("EC", privateKeyBytes);
        Assertions.assertEquals(ecies.getPrivate(), privateKey);
    }

    @Test
    public void generateDHTest() {
        final KeyPair dh = Keeper.generateKeyPair("DH");
        Assertions.assertNotNull(dh.getPrivate());
        Assertions.assertNotNull(dh.getPublic());

        final byte[] privateKeyBytes = dh.getPrivate().getEncoded();

        final PrivateKey privateKey = Keeper.generatePrivateKey("DH", privateKeyBytes);
        Assertions.assertEquals(dh.getPrivate(), privateKey);
    }

    @Test
    public void generateSm4KeyTest() {
        Assertions.assertEquals(16, Keeper.generateKey("sm4").getEncoded().length);
        Assertions.assertEquals(32, Keeper.generateKey("sm4", 256).getEncoded().length);
    }

}
