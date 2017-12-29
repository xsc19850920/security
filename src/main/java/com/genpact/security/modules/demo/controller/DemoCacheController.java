package com.genpact.security.modules.demo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.genpact.security.modules.demo.entity.DemoCache;
import com.genpact.security.modules.demo.service.DemoCacheService;

//GET /demos - 获取 demos 列表
//GET /demos/12 - 获取一个单独的 demo
//POST /demos - 创建一个新的 demo
//PUT /demos/12 - 更新 demo #12
//PATCH /demos/12 - 部分更新 demo #12
//DELETE /demos/12 - 删除 demo #12
@Controller
@RequestMapping("/demo")
public class DemoCacheController {
	Logger logger = LoggerFactory.getLogger(DemoCacheController.class);
	@Autowired
	private DemoCacheService demoCacheService;
	/**
	 * 方法名:save
	 * 描    述: method=RequestMethod.POST 限定请求的方法
	 * 		  @RequestBody 限定请求的参数是json格式 如果去掉普通表单提交数据
	 * 返回值:SysUser
	 * 参    数:@param demoCache
	 * 参    数:@return
	 * 作    者:710009498
	 * 时    间:Apr 10, 2017 9:22:37 AM
	 */
	@PostMapping(value = "/demos")
	public ResponseEntity<?> save(@RequestBody DemoCache demoCache) {
		DemoCache demo = demoCacheService.save(demoCache);
		if (demo == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(demo, HttpStatus.OK);
	}
	/**
	 * 方法名:delete
	 * 描    述:删除
	 * 返回值:ResponseEntity<?>
	 * 参    数:@param id
	 * 参    数:@return
	 * 作    者:710009498
	 * 时    间:May 15, 2017 10:14:59 AM
	 */
	@DeleteMapping("/demos/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		demoCacheService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/**
	 * 方法名:updateById
	 * 描    述:更新
	 * 返回值:ResponseEntity<?>
	 * 参    数:@param id
	 * 参    数:@param uiDemo
	 * 参    数:@return
	 * 作    者:710009498
	 * 时    间:May 15, 2017 10:14:50 AM
	 */
	@PutMapping("/demos/{id}")
	public ResponseEntity<?> updateById(@PathVariable Integer id,DemoCache uiDemo) {
		DemoCache demo = demoCacheService.findOne(id);
		BeanUtils.copyProperties(uiDemo, demo, "id");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/**
	 * 方法名:updatePartById
	 * 描    述:部分更新
	 * 返回值:ResponseEntity<?>
	 * 参    数:@param id
	 * 参    数:@param uiDemo
	 * 参    数:@return
	 * 作    者:710009498
	 * 时    间:May 15, 2017 10:14:37 AM
	 */
	@PatchMapping("/demos/{id}")
	public ResponseEntity<?> updatePartById(@PathVariable Integer id,DemoCache uiDemo) {
		DemoCache demo = demoCacheService.findOne(id);
		BeanUtils.copyProperties(uiDemo, demo, "id");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	/**
	 * 方法名:findOne
	 * 描    述:根据id查询
	 * 返回值:ResponseEntity<?>
	 * 参    数:@param id
	 * 参    数:@return
	 * 作    者:710009498
	 * 时    间:May 15, 2017 10:15:38 AM
	 */
	@GetMapping("/demos/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> findOne(@PathVariable Integer id) {
		DemoCache demo = demoCacheService.findOne(id);
		if (demo == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(demo, HttpStatus.OK);
	}
	/**
	 * 方法名:list
	 * 描    述:查询列表
	 * 返回值:ResponseEntity<?>
	 * 参    数:@return
	 * 作    者:710009498
	 * 时    间:May 15, 2017 10:16:00 AM
	 * @throws ServletRequestBindingException 
	 * @throws InterruptedException 
	 */
	@GetMapping("/demos")
	@ModelAttribute(value="order[0][column]",name="orderIndex")
	public  ResponseEntity<?>  list(
									@RequestParam(value="start",defaultValue = "1") int page,
									@RequestParam(value="length",defaultValue = "10") int size,
									@RequestParam(value="draw") int draw,
									@RequestParam(value="order[0][column]") int orderIndex,
									@RequestParam(value="order[0][dir]") String orderdir,
									@RequestParam(value="search[value]") String searchValue,
									HttpServletRequest request
									) throws ServletRequestBindingException  {
		String orderProperty = ServletRequestUtils.getStringParameter(request, "columns["+orderIndex+"][data]");
		Map<String, Object> map = demoCacheService.findAll(page,size,draw,orderProperty,orderdir,searchValue);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping("/view")
	public String toView(){
		return "demo/view";
	}
	

}
