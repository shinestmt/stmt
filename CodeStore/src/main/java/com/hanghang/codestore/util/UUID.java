package com.hanghang.codestore.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ����ΨһID��
 * <p>
 * ΨһID������Ԫ�ع��ɣ�<code>machineId-jvmId-timestamp-counter</code>��
 * </p>
 * <p>
 * Ĭ������£�UUID�����ֺʹ�д��ĸ���ɡ�����ڹ��캯��ʱ��ָ��<code>noCase=false</code>
 * ����ô�����ɵ�ID������Сд��ĸ������ID�ĳ��Ȼ�϶̡�
 * </p>
 * 
 * @author Michael Zhou
 */
public class UUID {
    private boolean noCase;
    private String instanceId;
    private AtomicInteger counter;

    public UUID() {
        this(true);
    }

    public UUID(boolean noCase) {
        // 1. Machine ID - ����IP/MAC����
        byte[] machineId = getLocalHostAddress();

        // 2. JVM ID - ��������ʱ������ + �����
        byte[] jvmId = getRandomizedTime();

        this.instanceId = StringUtil.bytesToString(machineId, noCase) + "-" + StringUtil.bytesToString(jvmId, noCase);

        // counter
        this.counter = new AtomicInteger();

        this.noCase = noCase;
    }

    /**
     * ȡ��local host�ĵ�ַ������п��ܣ�ȡ������MAC��ַ��
     */
    private static byte[] getLocalHostAddress() {
        Method getHardwareAddress;

        try {
            getHardwareAddress = NetworkInterface.class.getMethod("getHardwareAddress");
        } catch (Exception e) {
            getHardwareAddress = null;
        }

        byte[] addr;

        try {
            InetAddress localHost = InetAddress.getLocalHost();

            if (getHardwareAddress != null) {
                addr = (byte[]) getHardwareAddress.invoke(NetworkInterface.getByInetAddress(localHost)); // maybe null
            } else {
                addr = localHost.getAddress();
            }
        } catch (Exception e) {
            addr = null;
        }

        if (addr == null) {
            addr = new byte[] { 127, 0, 0, 1 };
        }

        return addr;
    }

    /**
     * ȡ�õ�ǰʱ�䣬�����������
     */
    private byte[] getRandomizedTime() {
        long jvmId = System.currentTimeMillis();
        long random = new SecureRandom().nextLong();

        // ȡ������ID��bytes����ת�����ַ���
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        try {
            dos.writeLong(jvmId);
            dos.writeLong(random);
        } catch (Exception e) {
        }

        return baos.toByteArray();
    }

    public String nextID() {
        // MACHINE_ID + JVM_ID + ��ǰʱ�� + counter
        return instanceId + "-" + StringUtil.longToString(System.currentTimeMillis(), noCase) + "-"
                + StringUtil.longToString(counter.getAndIncrement(), noCase);
    }
    
    public static void main(String[] args) {
		UUID uuid = new UUID(false);
		for (int i = 0; i < 39; i++) {
			System.out.println(uuid.nextID());
		}
		
	}
}