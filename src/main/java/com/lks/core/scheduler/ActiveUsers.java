package com.lks.core.scheduler;

import com.lks.core.model.UserModelDO;
import com.lks.security.IUserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lokkur on 1/20/2016.
 */
public class ActiveUsers {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ActiveUsers.class);

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;

    @Resource(name = "userDetailService")
    IUserService userService;

    public void getListOfActiveUsers(){
        List<Object> principals = sessionRegistry.getAllPrincipals();
        Map<String, List<String>> userMap = new HashMap<>();

        for (Object principal: principals) {
            if (principal instanceof User) {
                String username = ((User) principal).getUsername();
                UserModelDO user = userService.findUser(username);
                if(userMap.containsKey(user.getUserRole())){
                    userMap.get(user.getUserRole()).add(username);
                }
                else {
                    List<String> userNameList = new ArrayList<>();
                    userNameList.add(username);
                    userMap.put(user.getUserRole(), userNameList);
                }
            }
        }

        logger.info("***************Active Users***************");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        logger.info("Current time: "+timeStamp);
        for(String role: userMap.keySet()){
            logger.info("Number of active "+role+" in the system: "+ userMap.get(role).size());
        }
        logger.info("******************************************");
    }
}
