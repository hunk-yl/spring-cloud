package com.example.jcloudconsumerfeignhystrix.controller;

import com.example.jcloudconsumerfeignhystrix.client.UserClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Description TODO
 * @Author yanglin
 * @Date 2023/4/24 14:36
 * @Version 1.0
 **/
@RestController
public class HelloController {

    @Autowired
    public UserClient feignClient;
    /**
     * 此处的mapping是一级controller，
     * 调用方法里边绑定了二级的controller，相当于用http完成一次转发
     **/
    @GetMapping("/hello")
    @HystrixCommand(fallbackMethod = "helloFallback", commandProperties = {
            // 等待时间
            @HystrixProperty(name = "timeDeayInMillseconds", value = "100"),
            // 每个批量处理最大的请求数
            @HystrixProperty(name = "maxRequestsInBatch", value = "20"),
            // 不要开启请求缓存，这个功能毫无意义
            @HystrixProperty(name = "requestCacheEnabled", value = "false"),
            // 超时策略，本来默认的超时策略是true，现在策略是false，就表示超时策略不起作用了
            @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
            // 该属性用来配置当HystrixCommand.run()执行超时的时候是否要将它中断。默认的是掐断
            @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout", value = "true"),
            // 该属性用来配置当HystrixCommand.run()执行被取消的时候是否要将它中断。这里是执行被取消
            @HystrixProperty(name = "execution.isolation.thread.interruptOnCancel", value = "true"),
            // 属性用来 设置从调用线程中允许HtstrixCommand.getFallback()方法执行的最大并发请求数
            // 当达到最大并发请求数时，后续的请求将会呗拒绝并抛出异常。这里默认的是40个
            @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "40"),
            // 该属性用来设置服务降级策略是否启用，如果设置为false，
            // name当请求失败或者拒绝发生时，将不会调用HystrixCommand.getFallback()来执行服务降级逻辑。
            @HystrixProperty(name = "fallback.enabled", value =  "true"),
            // 该属性用来确定当服务请求命令失败时，是否使用断路器来跟踪其健康指标和熔断请求
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            // 该属性用来设置在滚动时间窗中，断路器熔断的最小请求数。
            // 意思是本来有50个请求，（10秒的滚动时间窗中）但是只有29个请求失败了有21个请求成功了，
            // 这也不会开启熔断的，因为没达到30个。
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "30"),
            // 该属性用来设置当断路器 打开之后的休眠时间窗。休眠时间窗结束之后，会将断路器置为“搬开”状态，尝试熔断的请求命令，
            // 如果依然失败就将断路器继续设置为“打开”状态，如果成功就设置为“关闭”状态。（复活赛）
            @HystrixProperty(name = "circuitBreaker.sloopWindowinMilliseconds", value = "4000"),// 默认是5000ms
            // 该属性用来设置断路器打开 的错误百分比条件。
            // 例如，默认值为50的情况下，表示在滚动时间窗中，在请求数量超过circuitBreaker.requestVolumeThreshold阈值的前提下，
            // 如果 错误请求数的百分比超过50，就把断路器设置为“打开”状态，否则就设置为“关闭”状态。
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            // circuitBreaker.forceOpen: 如果将该属性设置为true,断路器将强制进入“打开”状态，它会拒绝所有请求。
            // 该属性优先于circuitBreaker.forceClosed属性。
            @HystrixProperty(name = "circuitBreaker.forceOpen", value = "false"),
            // circuitBreaker.forceClosed:如果将该属性设置为true，断路器将强制进入“关闭"状态，它会接收所有请求。
            // 如果 circuitBreaker.forceOpen 属性为true，该属性不会生效。
            @HystrixProperty(name = "circuitBreaker.forceClosed", value = "true"),
            // etrics.rollingStats.timeinMilliseconds:该属性永安里设置滚动时间窗的长度，
            // 单位为毫秒。该时间用于断路器判断健康度时需要收集信息的持续时间。
            // 断路器在收集指标信息的时候会根据设置的时间窗长度拆分成多个“桶”来累积各度量值，每个“桶”记录了一段时间内的采集指标。
            // 例如，当采用默认值10000毫秒时，断路器默认将其拆分成10个桶（桶的数量也可通过metrics.rollingStats.numBuckets参数设置），
            // 每个桶记录1000毫秒内的指标信息。
            @HystrixProperty(name = "etrics.rollingStats.timeinMilliseconds", value = "10000"),
            // metrics.rollingstats.numBuckets: 该属性用来设置滚动时间窗统计指标 信息时划分“桶”的数量
            // 注意！！！这个桶的数量要和上面的滚动时间是整除关系
            @HystrixProperty(name = "metrics.rollingstats.numBuckets", value = "10"),
            // metrics.rollingPercente.enabled: 该属性用来设置对命令的延迟 是否使用百分位数来跟踪和计算。
            // 如果设置为false ，那么所有的概要统计都将返回-1.
            @HystrixProperty(name = "metrics.rollingPercente.enabled", value = "false"),
            // metrics.rollingPercentile.timeinMiliseconds: 该属性用来设置百分位统计的滚动窗口的持续时间，单位为毫秒
            // 这个默认值为60000
            @HystrixProperty(name = "metrics.rollingPercentile.timeinMilliseconds", value = "20000"),
            // metrics.rollingPercentile.numBuckets: 该属性用来设置百分位统计滚动窗口中使用“桶”的数量。
            // 这里的默认值是6
            // 注意！！上面两者同时要保持整除关系
            @HystrixProperty(name = "metrics.rollingPercentile.numBuckets", value = "2"),
            // metrics.rollingPercentile.bucketSize:该属性用来设置在执行过程中每个“桶”中保留的最大执行次数。
            // 如果在滚动时间窗内发生超过该设定值的执行次数，就从最初的位置开始重写。
            // 例如，将该值设置为100，滚动窗口为10秒，若在10秒内一个“桶”中发生了500次执行，
            // 那么该“桶”中只保留最后的100次执行的统计。另外，增加该值的大小将会增加内存量的消耗。
            // 并增加排序百分位数所需的计算时间。
            @HystrixProperty(name = "metrics.rollingPercentile.bucketSize", value = "100"),// 这里的默认值也是100
            // metrics.healthSnapshot.intervalinMilliseconds: 该属性用来设置采集影响断路器状态的健康快照（请求的成功、错误百分比）的间隔等待时间。
            @HystrixProperty(name = "metrics.healthSnapshot.intervalinMilliseconds", value = "600"),// 默认是500
            // 线程池的最大队列长度。
            @HystrixProperty(name = "coreSize", value = "20"),
            // 队列最大拒绝阈值，没有达到队列最大值，也能拒绝。
            @HystrixProperty(name = "maxQueueSize", value = "-1"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "5")

    })// 失败时调用默认返回
    public String hello(){
        return feignClient.hello();
    }

    @GetMapping("/hi")
    @HystrixCommand(fallbackMethod = "hiFailBack") // 失败时调用默认返回，demo中现在我们的客户端调用的接口实际是不存在，所以这个接口会返回下方的默认值
    public String hi(){
        return feignClient.sayHi();
    }

    @GetMapping("/haha")
    @HystrixCommand(fallbackMethod = "hahaFailBack") // 失败时调用默认返回，demo中现在我们的客户端调用的接口实际是不存在，所以这个接口会返回下方的默认值
    public String haha(){
        return feignClient.sayHaha();
    }

    /**
     * 对应上面的方法，参数必须一致，当访问失败时，hystrix直接回调此方法
     * 调用失败时，返回默认值
     **/
    public String helloFallback(){
        return "您请求的数据没拿到，我是hystrix返回的默认数据--helloFallback";
    }

    public String hiFailBack(){
        return "您请求的数据没拿到，我是hystrix返回的默认数据--hiFailBack";
    }

    public String hahaFailBack(){
        return "您请求的数据没拿到，我是Hystrix返回的默认数据--hahaFailBack";
    }
}
