package com.example.application.repository;

import com.example.application.model.Group;
import com.example.application.model.GroupRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRequestRepository extends JpaRepository<GroupRequest, Long> {
    List<GroupRequest> findByGroupName(String groupName);
}
