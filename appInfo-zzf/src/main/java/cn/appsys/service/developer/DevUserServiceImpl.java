package cn.appsys.service.developer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.devuser.DevUserMapper;
import cn.appsys.pojo.DevUser;

@Service
public class DevUserServiceImpl implements DevUserService {

	@Autowired
	private DevUserMapper devUserMapper;
	@Override
	public DevUser login(String devCode, String password) {
		DevUser user = null;
		user = devUserMapper.getLoginUser(devCode);
		if(user!=null){
			if(!user.getDevPassword().equals(password)){
				user = null;
			}
		}
		return user;
	}

}
