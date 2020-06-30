package com.test.demo.repo;



import com.test.demo.domain.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface RecordRepo extends JpaRepository<Record, Long> {

//    @Query("select a from record a where a.updated >= :from and a.updated <= :to")
    Page<Record> findAllByUpdatedIsBetween(Timestamp from, Timestamp to, Pageable pageable);
//            @Param("from") String from, @Param("to") String to, Pageable pageable);
//    Page<Record> findReferenceFieldBetween(String from,String to, Pageable pageable);
    Record findByPrimaryKey(String primaryKey);
}
