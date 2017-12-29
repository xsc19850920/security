package com.genpact.security.modules.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.genpact.security.modules.demo.dao.DemoCacheRepository;
import com.genpact.security.modules.demo.entity.DemoCache;
import com.google.common.collect.Maps;

@Service
public class DemoCacheService {

	@Autowired
	private DemoCacheRepository demoCacheRepository;
	
	public DemoCache save(DemoCache demo) {
		DemoCache s = demoCacheRepository.save(demo);
		return s;
	}
	
	public void delete(Integer id) {
		demoCacheRepository.delete(id);
	}
	
	public DemoCache findOne(int id) {
		DemoCache s = demoCacheRepository.findOne(id);
		return s;
	}
	
	public Map<String,Object> findAll(int page,int size,int draw,String orderProperty, String orderdir,String searchValue) {
		//根据name属性查询
		ExampleMatcher matcher = ExampleMatcher.matching()
											   .withMatcher("name", GenericPropertyMatchers.contains())
											   .withIgnoreCase();
		DemoCache demo = new DemoCache();
		demo.setName(searchValue);
		Example<DemoCache> example = Example.of(demo,matcher) ;
		Page<DemoCache> result = demoCacheRepository.findAll(example ,new PageRequest(page, size,Sort.Direction.fromString(orderdir),orderProperty) );
		
//		draw:表示请求次数
//		recordsTotal:总记录数
//		recordsFiltered:过滤后的总记录数
//		data:具体的数据对象数组
		Map<String,Object> map = Maps.newHashMap();
		map.put("data", result.getContent() );
		map.put("draw", draw);
		map.put("recordsTotal", result.getTotalElements());
		map.put("recordsFiltered", result.getTotalElements());
		return map;
	}

}
