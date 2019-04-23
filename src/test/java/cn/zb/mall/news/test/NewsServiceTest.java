package cn.zb.mall.news.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;

import cn.zb.base.controller.CallContext;
import cn.zb.mall.news.entity.News;
import cn.zb.mall.news.service.INewsService;
import cn.zb.page.PageData;
import cn.zb.page.Pageable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest {
	@Autowired
	INewsService newsServcie;

	@Test
	public void testSimplPage() {
		try {
			News temp = new News();
			temp.setNewstype((short) 99);
			Pageable pageable = new cn.zb.page.PageRequest(1, 10, 0);
			CallContext callContext = new CallContext();
			PageData<News> news = newsServcie.simpleList(temp, pageable, callContext);

			System.out.println(JSONObject.toJSON(news));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
