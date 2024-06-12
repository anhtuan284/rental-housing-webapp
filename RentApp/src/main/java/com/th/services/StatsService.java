package com.th.services;

import java.util.Date;
import java.util.List;

public interface StatsService {
    List<Object[]> getUsersByRole();
    List<Object[]> getUsersByPeriod(int year, String period);
    List<Object[]> getCountUsersCreated(int year, String period);

    List<Object[]> getPostCount();
}
