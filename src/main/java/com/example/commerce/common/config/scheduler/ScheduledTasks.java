package com.example.commerce.common.config.scheduler;

import com.example.commerce.business.point.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private PointService pointService;

    @Autowired
    public ScheduledTasks(PointService pointService) {
        this.pointService = pointService;
    }

    @Async
    @Scheduled(cron = "0/30 * * * * ?", zone = "Asia/Seoul")
    private void updateExpiredPoint() {
        pointService.findAllExpiredPoint();
    }
}
