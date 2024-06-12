package com.th.repositories;

import java.util.Date;
import java.util.List;

public interface StatsRepository {
    List<Object[]> statsUsersByRole();
    List<Object[]> statsUsersByPeriod(int year, String period);
    List<Object[]> countUsersCreated(int year, String period);

    List<Object[]> countPostsByStatus();
}
