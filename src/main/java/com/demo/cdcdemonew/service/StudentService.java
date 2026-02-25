package com.demo.cdcdemonew.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final JdbcTemplate jdbcTemplate;

    public StudentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsertStudents(int count) {

        String sql = "INSERT INTO student (full_name, age) VALUES (?, ?)";

        List<Object[]> batchArgs = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            batchArgs.add(new Object[]{
                    "Student " + i,
                    18 + (i % 10)
            });
        }

        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    public long insertOneByOne(int count) {
        long start = System.nanoTime();

        for (int i = 1; i <= count; i++) {
            // Example data (change if you want)
            String fullName = "Student " + i;
            int age = 18 + (i % 10);

            jdbcTemplate.update(
                    "INSERT INTO student (full_name, age, created_at) VALUES (?, ?, NOW())",
                    fullName, age
            );
        }

        long end = System.nanoTime();
        return (end - start) / 1_000_000; // ns -> ms
    }

    public long insertBatch(int count) {
        long start = System.nanoTime();

        // Build batch args
        List<Object[]> batchArgs = new ArrayList<>(count);
        for (int i = 1; i <= count; i++) {
            String fullName = "Student " + i;
            int age = 18 + (i % 10);
            batchArgs.add(new Object[]{fullName, age});
        }

        jdbcTemplate.batchUpdate(
                "INSERT INTO student (full_name, age, created_at) VALUES (?, ?, NOW())",
                batchArgs
        );

        long end = System.nanoTime();
        return (end - start) / 1_000_000;
    }
}