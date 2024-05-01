package com.example.Gateway.rolesFilter;

import com.example.Gateway.Utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
@AllArgsConstructor
public class RoleFilter {
    private final JwtUtil jwtUtil;

    public boolean filtersRoles(String token, String path) {
        Claims claims = jwtUtil.validateToken(token);
        String iss = claims.get("iss", String.class);
        Pattern pattern = Pattern.compile("role=(\\w+)");
        Matcher matcher = pattern.matcher(iss);
        List<String> roles = new ArrayList<>();
        while (matcher.find()) {
            String role = matcher.group(1);
            roles.add(role);
        }
        if (roles.isEmpty()) return false;       //predicate
        if (roles.contains("ADMIN")) return true;
        if (roles.contains("CUSTOMER") && !roles.contains("ADMIN")) {
            if (path.contains("/admin")) {
                return false;
            }
            return true;
        }
        return false;
    }

}
