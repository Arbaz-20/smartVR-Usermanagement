package com.smartVR.usermanagement.Repository;

import com.smartVR.usermanagement.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "select * from user where username=?",nativeQuery = true)
    Optional<User> findByUserName(@Param("") String name);

}
