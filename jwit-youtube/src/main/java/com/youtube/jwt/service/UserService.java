package com.youtube.jwt.service;

import com.youtube.jwt.dao.RoleDao;
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.Role;
import com.youtube.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public User registerNewUser(User user){

       Role role= roleDao.findById("User").get();
       Set<Role> roles=new HashSet<>();
       roles.add(role);
       user.setRole(roles);
       user.setUserPassword(getEncodedPassword(user.getUserPassword()));
    return userDao.save(user);
    }

    public void initRolesAndUser(){
        Role adminRole=new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin Role");
        roleDao.save(adminRole);

        Role userRole=new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser=new User();
        adminUser.setUserFirstName("Admin");
        adminUser.setUserLastName("Admin");
        adminUser.setUserName("Admin234");
        adminUser.setUserPassword(getEncodedPassword("Admin@pass"));
        Set<Role> adminRoles=new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

//        User user=new User();
//        user.setUserFirstName("Abdur");
//        user.setUserLastName("Rahman");
//        user.setUserName("Rahman234");
//        user.setUserPassword(getEncodedPassword("Rahman@pass"));
//        Set<Role> userRoles=new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userDao.save(user);

    }
    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }
}
