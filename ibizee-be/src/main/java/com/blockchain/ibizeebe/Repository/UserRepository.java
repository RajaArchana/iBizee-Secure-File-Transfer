package com.blockchain.ibizeebe.Repository;

import com.blockchain.ibizeebe.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findByUserEmail(String userEmail);
    List<Users> findAllByDepartmentId(int departmentId);

    Users findByUserId(int userId);
}
