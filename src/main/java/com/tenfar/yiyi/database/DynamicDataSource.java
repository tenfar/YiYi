package com.tenfar.yiyi.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 从数据源
     */
    private List<Object> slaveDataSources = new ArrayList<Object>();
    /**
     * 轮询计数
     */
    private AtomicInteger sequence = new AtomicInteger(0);

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        Object key = "";
        //主库
        if (DynamicDataSourceHolder.isMaster()) {
            key = DynamicDataSourceHolder.MASTER;
        } else {
            //从库
            key = getSlaveKey();
        }
        log.debug("==> select datasource key [{}]", key);
        return key;
    }

    public void setSlaveDataSources(List<Object> slaveDataSources) {
        this.slaveDataSources = slaveDataSources;
    }

    /**
     * 轮询获取从库
     *
     * @return
     */
    public Object getSlaveKey() {
        if (sequence.intValue() == Integer.MAX_VALUE) {
            synchronized (sequence) {
                if (sequence.intValue() == Integer.MAX_VALUE) {
                    sequence = new AtomicInteger(0);
                }
            }
        }
        int idx = sequence.getAndIncrement() % slaveDataSources.size();
        return slaveDataSources.get(idx);
    }
}
