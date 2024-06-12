package com.th.controllers;

import com.th.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class StatsController {
    @Autowired
    private StatsService statsService;

    @GetMapping("/stats")
    public String stats(Model model, @RequestParam Map<String, String> params) {
        String year = params.getOrDefault("year", String.valueOf(LocalDate.now().getYear()));
        String period = params.getOrDefault("period", "MONTH");
        List<Object[]> usersByRole = statsService.getUsersByRole();
        List<Object[]> usersByPeriod = statsService.getUsersByPeriod(Integer.parseInt(year), period);
        List<Object[]> countUsersCreated = statsService.getCountUsersCreated(Integer.parseInt(year), period);
        List<Object[]> countPost = statsService.getPostCount();

        model.addAttribute("usersByRole", usersByRole);
        model.addAttribute("usersByPeriod", usersByPeriod);
        model.addAttribute("countUsersCreated", countUsersCreated);
        model.addAttribute("post", countPost);
        model.addAttribute("year", year);
        model.addAttribute("period", period);

        return "stats";
    }

}
