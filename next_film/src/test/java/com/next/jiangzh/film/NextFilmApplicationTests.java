package com.next.jiangzh.film;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

		AbstractWrapper abstractWrapper = new QueryWrapper();
		abstractWrapper.eq("user_name","admin");

//		List<NextUser> nextUsers = nextUserMapper.selectList(null);
		List<NextUser> nextUsers = nextUserMapper.selectList(abstractWrapper);

		nextUsers.forEach(System.out::println);

	}

	@Test
	public void addUser(){
		NextUser nextUser = new NextUser();
		nextUser.setUserName("next学院");
		nextUser.setUserPwd("looks good");

		int isSuccess = nextUserMapper.insert(nextUser);

		System.out.println("isSuccess="+(isSuccess == 1 ? true : false));
	}

	@Test
	public void updateUser(){
		NextUser nextUser = new NextUser();
//		nextUser.setUuid(6);
//		nextUser.setUserName("next学院");
		nextUser.setUserPwd("looks very good !!!~ ");

//		int isSuccess = nextUserMapper.updateById(nextUser);

		AbstractWrapper abstractWrapper = new UpdateWrapper();
		abstractWrapper.eq("user_name","next学院");

		int isSuccess = nextUserMapper.update(nextUser,abstractWrapper);

		System.out.println("isSuccess="+(isSuccess == 1 ? true : false));
	}

	@Test
	public void deleteUser(){
		int id = 6;
		int isSuccess = nextUserMapper.deleteById(id);
		System.out.println("isSuccess="+(isSuccess == 1 ? true : false));
	}

	@Test
	public void queryById(){
		int id = 5;

		NextUser nextUser = nextUserMapper.selectById(id);

		System.out.println("isSuccess="+nextUser);

	}

	@Test
	public void defineSqlTest(){

		IPage<NextUser> iPage = new Page<>();
		iPage.setCurrent(1);
		iPage.setSize(2);

		List<NextUser> nextUsers = nextUserMapper.getUsers(iPage);

		nextUsers.forEach(System.out::println);

	}

	@Test
	public void pageTest(){

		IPage<NextUser> iPage = new Page<>();
		iPage.setCurrent(1);
		iPage.setSize(2);

		IPage<NextUser> nextUserIPage = nextUserMapper.selectPage(iPage, null);

		System.out.println("nextUserIPage="+nextUserIPage.getTotal());
		nextUserIPage.getRecords().forEach(System.out::println);

	}

}
