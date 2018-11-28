package com.sensor.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sensor.entity.UserEntity;
import com.sensor.service.UserService;
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	
//	@GetMapping(value= {"/logins","/"})
//	public ModelAndView login(ModelAndView mv){
//		mv.setViewName("login");
//	    return mv;
//	}
//	
	@GetMapping(value= {"/indexs"})
	@ResponseBody
	public ModelAndView index(ModelAndView mv){
		mv.setViewName("network");
	    return mv;
	}

	@GetMapping(value="/log2")
	public String returnid2(@RequestParam(value="id",required=false,defaultValue="0") Integer myid){
	    return "id:"+myid;
	}
	
//	@GetMapping(value="/findone/{userid}")
//	public void findone(@PathVariable("userid") Long userid) throws Exception{
//		userService.findById(userid);
//		
//	}
 

	@RequestMapping("/in")
	public ModelAndView inde(){
		ModelAndView mv=new ModelAndView();
	    mv.setViewName("index");
	    return mv;
	}
	
	@RequestMapping("/gcOriginInfo")
	@ResponseBody
	public Map<Integer, List<Object>> gcOriginInfo(String x_loc,String y_loc, String SensorNum,String UnicatNumber, String radius,String sinkRadius) throws InterruptedException{
		Map<Integer, List<Object>> originInfo =userService.gcOriginInfo(Integer.parseInt(x_loc), Integer.parseInt(y_loc),Integer.parseInt(SensorNum), Integer.parseInt(UnicatNumber), Integer.parseInt(radius), Integer.parseInt(sinkRadius));
		return originInfo;
	}
	
	
	@RequestMapping("/user/userlists")
	public List<UserEntity> queryLists(){
		List<UserEntity> result=userService.queryList();
		System.out.println(result);
		return result;
	}
	
	
	@RequestMapping("/sys")
	public void sys(){
		System.out.println("123");

	}


 
    @RequestMapping("/insertParam")
    public int insertParam() {
        return userService.insertParam();
    }
 
    @RequestMapping("/insertByMap")
    public int insertByMap() {
        return userService.insertByMap();
    }
 
    @PostMapping(value="/updateEntity")
    public int updateEntity(UserEntity userEntity) {
        return userService.updateEntity(userEntity);
    }
 
    @RequestMapping("/deleteEntity")
    public int deleteEntity() {
        return userService.deleteEntity();
    }
    
    @GetMapping(value="/findBynickName/{nickName}")
    public UserEntity findByNickname(@PathVariable("nickName") String nickName) {
    	return userService.findByNickName(nickName);
    }
}

