package com.tenfar.yiyi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 42位的时间前缀+10位的节点标识+12位的sequence避免并发的数字（12位不够用时强制得到新的时间前缀）
 * <p>
 * <b>对系统时间的依赖性非常强，需要关闭ntp的时间同步功能，或者当检测到ntp时间调整后，拒绝分配id。
 */
public class IdWorker {
    private final static Logger logger = LoggerFactory.getLogger(IdWorker.class);
    private final long workerId;
    /**
     * 起始标记点，作为基准
     **/
    private final long snsEpoch = 1330328109047L;
    /**
     * 只允许workid的范围为：0-1023
     **/
    private final long workerIdBits = 10L;
    /**
     * 1023,1111111111,10位
     **/
    private final long maxWorkerId = ~(-1L << this.workerIdBits);
    /**
     * sequence值控制在0-4095
     **/
    private final long sequenceBits = 12L;
    /**
     * 12
     **/
    private final long workerIdShift = this.sequenceBits;
    /**
     * 22
     **/
    private final long timestampLeftShift = this.sequenceBits + this.workerIdBits;
    /**
     * 4095,111111111111,12位
     **/
    private final long sequenceMask = ~(-1L << this.sequenceBits);
    /**
     * 0，并发控制
     **/
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public IdWorker(long workerId) {
        super();
        /** workid < 1024[10位：2的10次方] **/
        if (workerId > this.maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", this.maxWorkerId));
        }
        this.workerId = workerId;
    }

    public synchronized long nextId() throws Exception {
        long timestamp = this.timeGen();
        /** 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环)，下次再使用时sequence是新值 **/
        if (this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1 & this.sequenceMask;
            if (this.sequence == 0) {
                // 重新生成timestamp
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            logger.error(String.format("Clock moved backwards.Refusing to generate id for %d milliseconds", (this.lastTimestamp - timestamp)));
            throw new Exception(String.format("Clock moved backwards.Refusing to generate id for %d milliseconds", (this.lastTimestamp - timestamp)));
        }

        this.lastTimestamp = timestamp;
        // 生成的timestamp
        return timestamp - this.snsEpoch << this.timestampLeftShift | this.workerId << this.workerIdShift | this.sequence;
    }

    /**
     * 保证返回的毫秒数在参数之后
     *
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 获得系统当前毫秒数
     *
     * @return
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

}
