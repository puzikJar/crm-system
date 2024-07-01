package com.ox.company.crm.repository;

import com.ox.company.crm.entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Task t WHERE t.contact.id = :id")
    void deleteRelatedTasksByContactId(@Param("id") Long id);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.contact WHERE t.id = :id")
    Task findTaskById(@Param("id") Long id);

}