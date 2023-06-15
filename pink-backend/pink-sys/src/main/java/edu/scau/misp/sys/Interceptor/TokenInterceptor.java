package edu.scau.misp.sys.Interceptor;

import edu.scau.misp.sys.util.HttpStatus;
import edu.scau.misp.sys.util.JwtUtil;
import edu.scau.misp.todo.util.ServiceException;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public TokenInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 通过拦截器对请求头进行校验
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String header = request.getHeader("Authorization");
//        System.out.println("header:"+header);
        if (header != null && !"".equals(header)) {
//            if (header.startsWith("Bearer ")) {
                //获得token
                String token = header;
//            System.out.println("token:"+token);
                //验证token
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String skey = (String) claims.get("skey");
//                    System.out.println("skey:"+skey);
                    if (skey != null) {
                        request.setAttribute("openid",claims.getId());
                        request.setAttribute("skey",skey);
                        return true;
                    } else {
                        throw new BadCredentialsException("请先登录");
//                        throw new IllegalArgumentException("1");
//                        throw new ServiceException("访问受限", HttpStatus.FORBIDDEN);
                    }
                } catch (Exception e) {
                    throw new BadCredentialsException("请先登录");
//                    throw new IllegalArgumentException("2");
//                    throw new ServiceException("访问受限", HttpStatus.FORBIDDEN);
                }
//            }
        }
//        throw new IllegalArgumentException("3");
//        throw new ServiceException("请先授权", HttpStatus.UNAUTHORIZED);
        throw new AuthenticationCredentialsNotFoundException("请先登录");
//        return true;
    }
}


