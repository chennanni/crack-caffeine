package com.example.caffeineusage;

import com.github.benmanes.caffeine.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private Cache<Integer, Object> caffeineCache;

    @Autowired
    private Cache<Integer, Object> caffeineCacheTiny;

    @Autowired
    private UserMockDao userDao;

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

    public User getUserWithExpiredCache(int userId) {
        // 1. get from cache
        User result = (User) caffeineCacheTiny.getIfPresent(userId);
        if (result != null) {
            logger.info("get from cache: {}",userId);
            return result;
        }
        // 2. get from db
        User user = userDao.getUser(userId);
        if (user != null) {
            caffeineCacheTiny.put(userId, user);
            logger.info("save to cache: {}",userId);
            return user;
        }
        return null;
    }

}
