package com.binar.admin.student.repository;

import com.binar.admin.student.entity.Student;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class StudentRedisRepo {
    public static final String HASH_KEY_NAME = "STUDENT";

    @Autowired
    private RedisTemplate redisTemplate;

    public Student save(Student menu){
        // SETS menu object in MENU-ITEM hashmap at menuId key
        redisTemplate.opsForHash().put(HASH_KEY_NAME,menu.getId(),menu);
        return menu;
    }

    public List<Student> findAll(){
        // GET all Menu values
        return redisTemplate.opsForHash().values(HASH_KEY_NAME);
    }

    public Student findItemById(String id){
        // GET menu object from MENU-ITEM hashmap by menuId key
        return (Student) redisTemplate.opsForHash().get(HASH_KEY_NAME,id);
    }


    public String deleteMenu(String id){
        // DELETE the hashkey by menuId from MENU-ITEM hashmap
        redisTemplate.opsForHash().delete(HASH_KEY_NAME,id);
        return "Menu deleted successfully !!";
    }
}
