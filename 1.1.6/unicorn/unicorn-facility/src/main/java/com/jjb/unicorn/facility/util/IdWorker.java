package com.jjb.unicorn.facility.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 移植tweeter的snowflake:
 * (a) id构成: 42位的时间前缀 + 10位的节点标识 + 12位的sequence避免并发的数字(12位不够用时强制得到新的时间前缀)
 * 注意这里进行了小改动: snowkflake是5位的datacenter加5位的机器id; 这里变成使用10位的机器id
 * (b) 对系统时间的依赖性非常强，需关闭ntp的时间同步功能。当检测到ntp时间调整后，将会拒绝分配id
 *
 */
public class IdWorker {

    protected static final Logger logger = LoggerFactory.getLogger(IdWorker.class);
    private static IdWorker instance = new IdWorker(1, 1);
    private long workerId;
    private long datacenterId;
    // 0，并发控制
    private long sequence = 0L;
    // 时间起始标记点，作为基准，一般取系统的最近时间
    private long twepoch = 1288834974657L;
    // 机器标识位数
    private long workerIdBits = 5L;
    // 数据中心标识位数
    private long datacenterIdBits = 5L;
    // 机器ID最大值: 1023
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    //数据中心ID最大值
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    //毫秒内自增位
    private long sequenceBits = 12L;
    private long workerIdShift = sequenceBits; //12
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask = -1L ^ (-1L << sequenceBits);
    private long lastTimestamp = -1L;

    public IdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("机器标示位数不能大于 %d 或者小于 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("数据中心标示位数不能大于 %d 或者小于 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        logger.info(String.format("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d", timestampLeftShift,
            datacenterIdBits, workerIdBits, sequenceBits, workerId));
    }

    public static IdWorker getInstance() {
        return instance;
    }

    /**
     * 测试示例.
     *
     * @param args
     */
    public static void main(String[] args) {
        IdWorker worker1 = new IdWorker(1, 1);

    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            logger.error(String.format("机器时钟错误，至 %d 不能产生ID.", lastTimestamp));
            throw new RuntimeException(String.format("机器时钟错误，  期间 %d  不能产生ID.", lastTimestamp - timestamp));
        }

        // 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环); 对新的timestamp，sequence从0开始
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 重新生成timestamp
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    /**
     * 等待下一个毫秒的到来, 保证返回的毫秒数在参数lastTimestamp之后
     *
     * @param lastTimestamp
     * @return
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获得系统当前毫秒数
     *
     * @return
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}

