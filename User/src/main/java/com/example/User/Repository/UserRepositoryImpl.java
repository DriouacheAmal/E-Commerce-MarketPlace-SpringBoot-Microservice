package com.example.User.Repository;

import com.example.User.Entity.User;
import com.example.User.enums.UserRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

/*public class UserRepositoryImpl implements UserRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Date findExpiryDateByResetPasswordToken(String token) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.userRole = :role AND u.resetPasswordToken = :token", User.class);
        query.setParameter("role", UserRole.CUSTOMER);
        query.setParameter("token", token);
        List<User> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0).getResetPasswordTokenExpiryDate();
        } else {
            return null;
        }
    }


}

 */
