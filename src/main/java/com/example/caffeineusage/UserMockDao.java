package com.example.caffeineusage;

import org.springframework.stereotype.Repository;

@Repository
public class UserMockDao {
    public User getUser(int id) {
        User user = new User(id);

        int age = (int) (Math.random()*50+10);
        String name = "Alice" + id;

        user.setName(name);
        user.setAge(age);
        return user;
    }
}