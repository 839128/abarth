package org.miaixz.bus.extra.ftp;

public class SimpleFtpServerTest {

    public static void main(final String[] args) {
        SimpleFtpServer
                .of()
                .addAnonymous("d:/test/ftp/")
                .start();
    }

}
