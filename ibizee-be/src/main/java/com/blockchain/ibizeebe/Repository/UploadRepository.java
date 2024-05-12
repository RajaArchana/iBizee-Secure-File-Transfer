package com.blockchain.ibizeebe.Repository;

import com.blockchain.ibizeebe.Model.UploadRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadRepository extends JpaRepository <UploadRequest, Integer> {
    List<UploadRequest> findByDepartmentId(int departmentId);
}
