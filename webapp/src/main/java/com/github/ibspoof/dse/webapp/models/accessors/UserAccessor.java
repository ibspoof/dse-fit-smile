//package com.com.github.ibspoof.cassandraclient.models.accessors;
//
//
//import com.datastax.driver.core.ResultSet;
//import com.datastax.driver.core.Statement;
//import com.datastax.driver.mapping.Result;
//import com.datastax.driver.mapping.annotations.Accessor;
//import com.datastax.driver.mapping.annotations.Param;
//import com.datastax.driver.mapping.annotations.Query;
//import com.datastax.driver.mapping.annotations.QueryParameters;
//import com.com.github.ibspoof.cassandraclient.models.User;
//
//import java.util.UUID;
//
//@Accessor
//public interface UserAccessor {
//
//    @Query("SELECT * FROM users")
////    @QueryParameters(consistency="LOCAL_ONE", fetchSize = 10)
//    Result<User> getAll();
//
//    @Query("INSERT INTO users (user_id, name) VALUES (:userId, :name) IF NOT EXISTS")
//    ResultSet insertUser(@Param("userId") UUID userId, @Param("name") String name);
//
////    @Query("SELECT user_id FROM users")
////    Result<User> getAllFilterUserId();
////
////    @Query("SELECT user_id, first_name FROM users WHERE user_id = ?")
////    Result<User> getUserIdFirstNameByUserId(UUID user_id);
////
////    @Query("SELECT * FROM users WHERE spec_code = :spec AND timestamp > :timeStart and timestamp < :timeStop")
////    Result<User> getDispenseBySpecAndTimeRange(@Param("spec") UUID user_id);
//
//
//}