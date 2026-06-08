package com.smartcollab.project.repository;

import com.smartcollab.project.model.Project;
import com.smartcollab.project.model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

    List<ProjectMember> findByProject(Project project);
}