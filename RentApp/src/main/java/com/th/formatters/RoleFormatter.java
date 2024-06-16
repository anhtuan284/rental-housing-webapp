package com.th.formatters;
import org.springframework.format.Formatter;

import com.th.pojo.Role;

import java.text.ParseException;
import java.util.Locale;

public class RoleFormatter implements Formatter<Role> {

    @Override
    public Role parse(String id, Locale locale) throws ParseException {
        Role role = new Role();
        role.setId(Integer.parseInt(id));
        return role;
    }

    @Override
    public String print(Role role, Locale locale) {
        return String.valueOf(role.getId());
    }
}
