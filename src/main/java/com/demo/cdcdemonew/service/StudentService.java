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
}