package com.upgrade.pacificocean.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import com.upgrade.pacificocean.domain.Booking;
import com.upgrade.pacificocean.domain.Campsite;
import com.upgrade.pacificocean.domain.Schedule;

@Mapper
public interface CampsiteMapper {
    @Select("SELECT * FROM `campsite` WHERE id = #{id}")
    Campsite getCampsite(@Param("id") int id);
    
    @Select("SELECT * FROM `schedule` WHERE camp_id = #{id} AND book_date > #{start} AND book_date <= #{end}")
    List<Schedule> getSchedule(@Param("id") int id, @Param("start") int start, @Param("end") int end);
    
//    @Select("SELECT LAST_INSERT_ID()")
//    int getLastBooking();
    
    @Select("SELECT * FROM `booking` WHERE id = #{id}")
    Booking getBooking(@Param("id") int id);
    
    @Insert("INSERT INTO `booking`(`id`, `name`, `email`, `camp_id`, `start_date`, `end_date`) "
    		+ "VALUES (#{id}, #{name}, #{email}, #{camp_id}, #{start_date}, #{end_date})")
    void createBooking(@Param("id") int id, @Param("name") String name, @Param("email") String email, 
    		@Param("camp_id") int camp_id, @Param("start_date") int start_date, @Param("end_date") int end_date);
    
    @Update("UPDATE `booking` SET `name`=#{name},`email`=#{email},"
    		+ "`start_date`=#{start_date},`end_date`=#{end_date},`cancelled`=#{cancelled} WHERE id = #{id}")
    void updateBooking(@Param("id") int id, @Param("name") String name, @Param("email") String email,
			@Param("start_date") int start_date, @Param("end_date") int end_date, @Param("cancelled") boolean cancelled);
    
    @Delete("DELETE FROM `booking` WHERE id = #{id}")
    void deleteBooking(@Param("id") int id);
}