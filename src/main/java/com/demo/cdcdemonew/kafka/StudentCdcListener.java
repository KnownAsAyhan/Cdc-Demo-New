package com.demo.cdcdemonew.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StudentCdcListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "dbserver1.demo_cdc.student", groupId = "cdc-demo-group")
    public void listen(String message) {
        try {
            JsonNode root = objectMapper.readTree(message);

            // Debezium operation type: c, u, d, r
            String op = root.path("op").asText("unknown");

            // "after" exists for create/update/read, usually null for delete
            JsonNode after = root.path("after");
            // "before" exists for update/delete, usually null for create/read
            JsonNode before = root.path("before");

            System.out.println("\n=== CDC EVENT ===");
            System.out.println("op     = " + op);
            System.out.println("before = " + (before.isMissingNode() ? "missing" : before.toString()));
            System.out.println("after  = " + (after.isMissingNode() ? "missing" : after.toString()));
            System.out.println("=================\n");

        } catch (Exception e) {
            System.out.println("\n!!! CDC PARSE ERROR !!!");
            System.out.println("Could not parse message as JSON.");
            System.out.println("Raw message: " + message);
            System.out.println("Error: " + e.getMessage());
            System.out.println("=======================\n");
        }
    }
}