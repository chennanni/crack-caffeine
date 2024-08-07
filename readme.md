# Caffeine Usage

## 1. Caffeine的基本使用

缓存的配置如下：

~~~ java
@Bean
public Cache<Integer, Object> caffeineCache() {
    return Caffeine.newBuilder()
            // 设置最后一次写入或访问后经过固定时间过期
            .expireAfterWrite(60, TimeUnit.SECONDS)
            // 初始的缓存空间大小
            .initialCapacity(100)
            // 缓存的最大条数
            .maximumSize(1000)
            .build();
}
~~~

使用缓存的核心逻辑在`UserService`中：

~~~ java
public User getUser(int userId) {
    // 1. get from cache
    User result = (User) caffeineCache.getIfPresent(userId);
    if (result != null) {
        logger.info("get from cache: {}",userId);
        return result;
    }
    // 2. get from db
    User user = userDao.getUser(userId);
    if (user != null) {
        caffeineCache.put(userId, user);
        logger.info("save to cache: {}",userId);
        return user;
    }
    return null;
}
~~~

实际应用中，两次分别尝试从cache中拉数据，在`Application`中运行示例如下：

~~~
Start to get user...
[timestamp] - save to cache: 1
[timestamp] - save to cache: 2
[timestamp] - save to cache: 3
Get user again...
[timestamp] - get from cache: 1
[timestamp] - get from cache: 2
[timestamp] - get from cache: 3
End.
~~~

## 2. Caffeine的淘汰策略

测试缓存过期的情况（基于时间淘汰）：基于`UserServiceTest`，在过期时间之后get同一个元素，不是直接从缓存中过去，而是从数据库中获取。

## 3. Caffeine缓存自动加载

在第一章节中，缓存是手动加载的，这一章节配置自动加载。

## 4. Caffeine的性能测试

对于同一组数据，对比`ConcurrentHashmap`和`Caffeine`的性能。