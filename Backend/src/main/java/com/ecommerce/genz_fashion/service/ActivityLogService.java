package com.ecommerce.genz_fashion.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.genz_fashion.entity.ActivityLogs;
import com.ecommerce.genz_fashion.repository.ActivityLogsRepository;

@Service
@Transactional
public class ActivityLogService {
    
    @Autowired
    private ActivityLogsRepository activityLogsRepository;
    
    public List<ActivityLogs> getAllActivityLogs() {
        return activityLogsRepository.findAll();
    }
    
    public Page<ActivityLogs> getActivityLogsWithPagination(Pageable pageable) {
        return activityLogsRepository.findAll(pageable);
    }
    
    public Optional<ActivityLogs> getActivityLogById(Long id) {
        return activityLogsRepository.findById(id);
    }
    
    public List<ActivityLogs> getActivityLogsByUserId(Long userId) {
        return activityLogsRepository.findAll().stream()
                .filter(log -> log.getChangedBy() != null && log.getChangedBy().equals(userId))
                .toList();
    }
    
    public List<ActivityLogs> getActivityLogsByTableName(String tableName) {
        return activityLogsRepository.findAll().stream()
                .filter(log -> tableName.equals(log.getTableName()))
                .toList();
    }
    
    public List<ActivityLogs> getActivityLogsByActionType(String actionType) {
        return activityLogsRepository.findAll().stream()
                .filter(log -> actionType.equals(log.getActionType()))
                .toList();
    }
    
    public ActivityLogs saveActivityLog(ActivityLogs activityLog) {
        if (activityLog.getChangedAt() == null) {
            activityLog.setChangedAt(new Date());
        }
        return activityLogsRepository.save(activityLog);
    }
    
    public void deleteActivityLog(Long id) {
        activityLogsRepository.deleteById(id);
    }
    
    public ActivityLogs logInsert(String tableName, Long recordId, String newValues, Long changedBy, String ipAddress, String userAgent) {
        ActivityLogs log = new ActivityLogs();
        log.setTableName(tableName);
        log.setActionType("INSERT");
        log.setRecordId(recordId);
        log.setNewValues(newValues);
        log.setChangedBy(changedBy);
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        log.setChangedAt(new Date());
        return saveActivityLog(log);
    }
    
    public ActivityLogs logUpdate(String tableName, Long recordId, String oldValues, String newValues, Long changedBy, String ipAddress, String userAgent) {
        ActivityLogs log = new ActivityLogs();
        log.setTableName(tableName);
        log.setActionType("UPDATE");
        log.setRecordId(recordId);
        log.setOldValues(oldValues);
        log.setNewValues(newValues);
        log.setChangedBy(changedBy);
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        log.setChangedAt(new Date());
        return saveActivityLog(log);
    }
    
    public ActivityLogs logDelete(String tableName, Long recordId, String oldValues, Long changedBy, String ipAddress, String userAgent) {
        ActivityLogs log = new ActivityLogs();
        log.setTableName(tableName);
        log.setActionType("DELETE");
        log.setRecordId(recordId);
        log.setOldValues(oldValues);
        log.setChangedBy(changedBy);
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        log.setChangedAt(new Date());
        return saveActivityLog(log);
    }
    
    public List<ActivityLogs> getRecentActivities(int limit) {
        return activityLogsRepository.findAll().stream()
                .sorted((a, b) -> b.getChangedAt().compareTo(a.getChangedAt()))
                .limit(limit)
                .toList();
    }
    
    public List<ActivityLogs> getActivitiesByDateRange(Date startDate, Date endDate) {
        return activityLogsRepository.findAll().stream()
                .filter(log -> log.getChangedAt().after(startDate) && log.getChangedAt().before(endDate))
                .toList();
    }
    
    public List<ActivityLogs> getUserActivitiesInDateRange(Long userId, Date startDate, Date endDate) {
        return activityLogsRepository.findAll().stream()
                .filter(log -> log.getChangedBy() != null && log.getChangedBy().equals(userId))
                .filter(log -> log.getChangedAt().after(startDate) && log.getChangedAt().before(endDate))
                .toList();
    }
    
    public long getActivityCountByUser(Long userId) {
        return activityLogsRepository.findAll().stream()
                .filter(log -> log.getChangedBy() != null && log.getChangedBy().equals(userId))
                .count();
    }
    
    public long getActivityCountByTable(String tableName) {
        return activityLogsRepository.findAll().stream()
                .filter(log -> tableName.equals(log.getTableName()))
                .count();
    }
    
    public void cleanupOldLogs(Date beforeDate) {
        List<ActivityLogs> oldLogs = activityLogsRepository.findAll().stream()
                .filter(log -> log.getChangedAt().before(beforeDate))
                .toList();
        
        for (ActivityLogs log : oldLogs) {
            activityLogsRepository.delete(log);
        }
    }
}