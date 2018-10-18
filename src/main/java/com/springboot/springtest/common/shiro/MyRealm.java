package com.springboot.springtest.common.shiro;

import com.springboot.springtest.common.JWTTokenUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class MyRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LogManager.getLogger(MyRealm.class);

//    private MemberUserService userService;

//    @Autowired
//    public void setUserService(MemberUserService userService) {
//        this.userService = userService;
//    }

    /**
     * 必须重写此方法
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userId = JWTTokenUtil.getUserId(principals.toString());
        // todo 获取用户权限，角色从平台
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> role = new HashSet<>();
        //todo 添加角色到role
        simpleAuthorizationInfo.addRoles(role);

        Set<String> permission = new HashSet<>();
        // todo 添加权限到permission
        permission.add("selectUser");
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得userId，用于和数据库进行对比
        String userId = JWTTokenUtil.getUserId(token);
//        if (userId == null) {
//            throw new BusinessException("token invalid");
//        }
//        BaseResult result = userService.getMemberUserById(Integer.parseInt(userId));
//        if (result.getCode() != 200 || result.getData() == null) {
//            throw new BusinessException("用户不存在");
//        }
//        MemberUser user = (MemberUser) result.getData();
//        if (!JWTTokenUtil.verify(token, userId)) {
//            throw new BusinessException("登录已过期，请退出重新登录");
//        }
//        if (Objects.equals(StatusEnum.UNENABLE.getValue(),user.getDelstatus())) {
//            throw new BusinessException("用户被删除，请重新添加");
//        }
        // todo 判断用户启用状态，审核状态等
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}