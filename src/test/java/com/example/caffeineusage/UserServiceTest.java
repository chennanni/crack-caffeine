package com.example.caffeineusage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserService.class, UserMockDao.class, CacheConfig.class})
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testCacheTimeExpired() throws InterruptedException {
        User u1 = userService.getUserWithExpiredCache(1);
        Thread.sleep(1000);
        User u2 = userService.getUserWithExpiredCache(1);
        Assertions.assertNotEquals(u1, u2);

        User u3 = userService.getUserWithExpiredCache(2);
        User u4 = userService.getUserWithExpiredCache(2);
        Assertions.assertEquals(u3, u4);
    }
}
