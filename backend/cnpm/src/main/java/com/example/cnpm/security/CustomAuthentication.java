package com.example.cnpm.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomAuthentication implements Authentication {

    private Long id;
    private boolean authenticated;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomAuthentication(Long id, Collection<? extends GrantedAuthority> authorities) {
        this(id, true, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return this.authorities;
    }

    @Override
    public Object getDetails() {
        return getId();
    }

    @Override
    public Object getPrincipal() {
        return getName();
    }

    @Override
    public String getName() {
        return getId().toString();
    }
}
