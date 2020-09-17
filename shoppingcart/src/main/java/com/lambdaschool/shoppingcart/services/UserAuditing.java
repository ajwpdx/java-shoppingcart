package com.lambdaschool.shoppingcart.services;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Spring Boot needs to know what username to use for the auditing fields CreatedBy and ModifiedBy
 * For now, a default name will be used
 */
@Component
public class UserAuditing
        implements AuditorAware<String>
{
    /**
     * The current user
     *
     * @return Optional(String) of current user
     */
    @Override
    public Optional<String> getCurrentAuditor()
    {
        String uname;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null)
        {
            //allows us to find the name of the currently signed on user
            uname = authentication.getName();
        } else
        {
            uname = "SYSTEM";
        }

        return Optional.of(uname);
    }
}
