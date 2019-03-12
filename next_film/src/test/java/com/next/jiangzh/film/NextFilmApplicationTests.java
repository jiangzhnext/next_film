package com.next.jiangzh.film;

import com.next.jiangzh.film.dao.entity.NextUser;
import com.next.jiangzh.film.dao.mapper.NextUserMapper;
import com.next.jiangzh.film.example.dao.UserMapper;
import com.next.jiangzh.film.example.dao.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NextFilmApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private NextUserMapper nextUserMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void mybatisHelloWorld(){
		List<User> users = userMapper.selectList(null);

		users.forEach(System.out::println);

	}

	@Test
	public void mybatisGeneratorTest(){
		List<NextUser> nextUsers = nextUserMapper.selectList(null);

		nextUsers.forEach(System.out::println);

	}

}
