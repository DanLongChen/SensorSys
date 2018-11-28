package com.sensor.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sensor.Generate.Simulation;
import com.sensor.dao.UserMapper;
import com.sensor.entity.UserEntity;

@Service
public class UserService {
	
	@Autowired(required = false)
	private UserMapper mapper;

	public Map<Integer, List<Object>> gcOriginInfo(int x_loc,int y_loc,int sensorNUm,int UnicatNumber,int radius,int sinkRadius) throws InterruptedException{
		Simulation simulation=new Simulation();
		Map<Integer, List<Object>> result=simulation.GCSimulationInterface(x_loc, y_loc,sensorNUm, UnicatNumber, radius, sinkRadius);
		return result;
	}

//	public List<Object> gcEventInfo(int x_loc,int y_loc,int UnicatNumber,int radius,int sinkRadius) throws InterruptedException{
//		Simulation simulation=new Simulation();
//		Map<Integer, List<Object>> result=simulation.GCSimulationInterface(x_loc, y_loc, UnicatNumber, radius, sinkRadius);
//		List<Object> originInfo=result.get(1);
//		return originInfo;
//	}
	
	
	
	public List<UserEntity> queryList(){
		List<UserEntity> userList=mapper.queryList();
		return userList;
	}
    
	//方法二：用统一的result进行返回，格式一致
    public UserEntity findByIdWay2(UserEntity userEntity){
    	return mapper.findByIdWay2(userEntity);
    }
    

    public UserEntity findByIdreturn(long userId){
       return mapper.findById(userId); 
     }
    

 
	
    public int insertParam() {
        return mapper.insertParam("linzhiqiang","lzq");
    }
 
    public int insertByMap() {
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("nickName","zhaotong");
        map.put("userCode","zt");
        return mapper.insertByMap(map);
    }
 
    public int updateEntity(UserEntity entity) {
        return mapper.updateEntity(entity);
    }
    
    public UserEntity findByNickName(String nickName) {
        return mapper.findByNickName(nickName);
    }
    
    public int deleteEntity() {
        UserEntity entity=new UserEntity();
        entity.setUserId(11);
        return mapper.deleteEntity(entity);
    }
}
