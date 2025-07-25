package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.ActivityLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ActivityLogsRepository extends JpaRepository<ActivityLogs, Long> {
    
    List<ActivityLogs> findByChangedBy(Long changedBy);
    
    List<ActivityLogs> findByTableName(String tableName);
    
    List<ActivityLogs> findByActionType(String actionType);
    
    @Query("SELECT a FROM ActivityLogs a WHERE a.changedAt BETWEEN :startDate AND :endDate ORDER BY a.changedAt DESC")
    List<ActivityLogs> findByChangedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query("SELECT a FROM ActivityLogs a WHERE a.changedBy = :userId AND a.changedAt BETWEEN :startDate AND :endDate ORDER BY a.changedAt DESC")
    List<ActivityLogs> findByChangedByAndChangedAtBetween(@Param("userId") Long userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query("SELECT a FROM ActivityLogs a ORDER BY a.changedAt DESC")
    List<ActivityLogs> findAllOrderByChangedAtDesc();
    
    long countByChangedBy(Long changedBy);
    
    long countByTableName(String tableName);
    
    @Query("SELECT a FROM ActivityLogs a WHERE a.changedAt < :beforeDate")
    List<ActivityLogs> findByChangedAtBefore(@Param("beforeDate") Date beforeDate);
}