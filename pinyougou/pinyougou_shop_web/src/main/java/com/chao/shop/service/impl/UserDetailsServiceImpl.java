package com.chao.shop.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chao.pojo.TbSeller;
import com.chao.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

/**
 * 自定义认证类
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    @Reference
    private SellerService sellerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));

        //根据用户名(id)查询用户
        TbSeller seller = sellerService.findOne(username);
        //已经审核通过的商家才能通过
        if (seller != null && "1".equals(seller.getStatus())) {
            return new User(username, seller.getPassword(), authorities);
        }

        return null;
    }
}
