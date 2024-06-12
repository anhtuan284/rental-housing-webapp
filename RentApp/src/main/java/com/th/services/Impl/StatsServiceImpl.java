package com.th.services.Impl;

import com.th.repositories.StatsRepository;
import com.th.services.StatsService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @Override
    public List<Object[]> getUsersByRole() {
        return statsRepository.statsUsersByRole();
    }

    @Override
    public List<Object[]> getUsersByPeriod(int year, String period) {
        return statsRepository.statsUsersByPeriod(year, period);
    }

    @Override
    public List<Object[]> getCountUsersCreated(int year, String period) {
        return statsRepository.countUsersCreated(year, period);
    }

    @Override
    public List<Object[]> getPostCount() {
        return statsRepository.countPostsByStatus();
    }
}
