package com.upgrade.pacificocean.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upgrade.pacificocean.dao.CampsiteMapper;
import com.upgrade.pacificocean.domain.Schedule;

@Service
@Transactional(readOnly = true)
public class ScheduleService {
    @Autowired
    private CampsiteMapper scheduleMapper;

    public List<Schedule> getSchedule(int id, int date) {
    	return scheduleMapper.getSchedule(id, date);
    }

}