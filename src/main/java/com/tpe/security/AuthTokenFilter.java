package com.tpe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    //tokenı filtreleyeceğiz, requestden tokenı almamız gerekiyor. -- bu methotla methon basindaki Bearer kelimesinden kurtulup direk tokeni almak icin kullandik
    private String parseToken(HttpServletRequest request){
        String header = request.getHeader("Authorization"); // Bearer afafadfAD...
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //request icerisinden token'i aldik
        String token = parseToken(request);
        //simdi token kontrolu yapalim
        try {
            if (token!=null && jwtUtils.validateToken(token)){
                //kullanici login olabilir : username'ini alalim
                String username = jwtUtils.getUsernameFromToken(token);
                //username ile useri bulabiliriz : burda bulunan user securitynin data tipinde user artik elimizde
                UserDetails user = userDetailsService.loadUserByUsername(username);

                /* Spring Security, kimlik doğrulama işleminden sonra kullanıcının
                  bilgilerini Security Context'e otomatik olarak ekler. Ancak
                  özel bir Filter yazılırsa, kimlik doğrulama sonrası Security Context'e
                 kullanıcıyı manuel olarak eklemek gerekir.
                 */
                //login olan userı security contexte koymak için authenticaion objesi gerekli
                UsernamePasswordAuthenticationToken authenticated =
                        new UsernamePasswordAuthenticationToken(user,
                                null, //password istiyor ama vermeye gerek yok
                                user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticated);

            }
        } catch (UsernameNotFoundException e) {
            e.getStackTrace();
        }
        filterChain.doFilter(request,response);
        //bu filtreden sonra diğer filtreler ile devam ett

    }
}
