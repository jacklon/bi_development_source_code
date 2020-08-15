//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.java.bi.db.util;

import java.net.InetAddress;

public class UUIDGenerator {
    private static final int IP;
    private static short counter;
    private static final int JVM;

    public UUIDGenerator() {
    }

    public static String generate() {
        return (new StringBuilder(32)).append(format(getIP())).append(format(getJVM())).append(format(getHiTime())).append(format(getLoTime())).append(format(getCount())).toString();
    }

    private static final String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuilder buf = new StringBuilder("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    private static final String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuilder buf = new StringBuilder("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    private static final int getJVM() {
        return JVM;
    }

    private static final short getCount() {
        Class var0 = UUIDGenerator.class;
        synchronized(UUIDGenerator.class) {
            if (counter < 0) {
                counter = 0;
            }

            short var10000 = counter;
            counter = (short)(var10000 + 1);
            return var10000;
        }
    }

    private static final int getIP() {
        return IP;
    }

    private static final short getHiTime() {
        return (short)((int)(System.currentTimeMillis() >>> 32));
    }

    private static final int getLoTime() {
        return (int)System.currentTimeMillis();
    }

    private static final int toInt(byte[] bytes) {
        int result = 0;

        for(int i = 0; i < 4; ++i) {
            result = (result << 8) - -128 + bytes[i];
        }

        return result;
    }

    static {
        int ipadd;
        try {
            ipadd = toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception var2) {
            ipadd = 0;
        }

        IP = ipadd;
        counter = 0;
        JVM = (int)(System.currentTimeMillis() >>> 8);
    }
}
