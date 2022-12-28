package top.finder.aether.data.core.component;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.finder.aether.common.support.helper.SpringBeanHelper;
import top.finder.aether.data.core.properties.AetherSnowflakeProperties;

import javax.annotation.PostConstruct;

/**
 * <p>雪花生成器</p>
 *
 * @author hedwing
 * @since 2022/10/22
 **/
@Component
public class SnowflakeWorker implements IdentifierGenerator {
    private final static Logger logger = LoggerFactory.getLogger(SnowflakeWorker.class);

    private AetherSnowflakeProperties aetherSnowflakeProperties;

    public SnowflakeWorker(AetherSnowflakeProperties aetherSnowflakeProperties) {
        this.aetherSnowflakeProperties = aetherSnowflakeProperties;
    }

    public static SnowflakeWorker getBean() {
        return SpringBeanHelper.getBean(SnowflakeWorker.class);
    }

    /**
     * 起始的时间戳:这个时间戳自己随意获取，比如自己代码的时间戳
     */
    private final static long START_TIMESTAMP = 1660320000000L;

    /**
     * 序列号占用的位数
     */
    private final static long SEQUENCE_BIT = 12;

    /**
     * 机器标识占用的位数
     */
    private final static long MACHINE_BIT = 5;

    /**
     * 数据中心占用的位数
     */
    private final static long DATACENTER_BIT = 5;

    /**
     * 用位运算计算出最大支持的数据中心数量：31
     */
    public final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);

    /**
     * 用位运算计算出最大支持的机器数量：31
     */
    public final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);

    /**
     * 用位运算计算出12位能存储的最大正整数：4095
     */
    public final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 机器标志较序列号的偏移量【向左的位移】
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;

    /**
     * 数据中心较机器标志的偏移量【向左的位移】
     */
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;

    /**
     * 时间戳较数据中心的偏移量【向左的位移】
     */
    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    /**
     * 序列号
     */
    private static long sequence = 0L;

    /**
     * 上一次时间戳
     */
    private static long lastTimestamp = -1L;

    private long machineId;

    private long datacenterId;

    @PostConstruct
    public void init() {
        machineId = aetherSnowflakeProperties.getMachineId();
        datacenterId = aetherSnowflakeProperties.getDatacenterId();
        logger.info("雪花算法正在运行...[机器id={}, 数据中心id={}]", machineId, datacenterId);
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            logger.error("机器id必须大于0并且小于{}", MAX_MACHINE_NUM);
        }
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            logger.error("数据中心id必须大于0并且小于{}", MAX_DATACENTER_NUM);
        }
    }

    /**
     * <p>产生下一个ID</p>
     *
     * @return long
     * @author hedwing
     * @since 2022/8/12
     */
    public synchronized long nextId() {
        // 获取当前时间戳
        long currTimestamp = getNewTimestamp();
        // 如果当前时间戳小于上次时间戳则抛出异常
        if (currTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }
        // 相同毫秒内
        if (currTimestamp == lastTimestamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                // 获取下一时间的时间戳并赋值给当前时间戳
                currTimestamp = getNextMill();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }
        // 当前时间戳存档记录，用于下次产生id时对比是否为相同时间戳
        lastTimestamp = currTimestamp;
        return (currTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT
                | datacenterId << DATACENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    /**
     * <p>获取下一时间的时间戳</p>
     *
     * @return long
     * @author hedwing
     * @since 2022/8/12
     */
    private static long getNextMill() {
        long mill = getNewTimestamp();
        while (mill <= lastTimestamp) {
            mill = getNewTimestamp();
        }
        return mill;
    }

    /**
     * <p>获取系统时间戳</p>
     *
     * @return long
     * @author hedwing
     * @since 2022/8/12
     */
    private static long getNewTimestamp() {
        return System.currentTimeMillis();
    }


    /**
     * 生成Id
     *
     * @param entity 实体
     * @return id
     */
    @Override
    public Long nextId(Object entity) {
        return SnowflakeWorker.getBean().nextId();
    }
}
