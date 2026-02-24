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

            // Debezium important fields
            String op = root.path("op").asText();          // c, u, d, r     create  update delete read     read(It loads existing data from DB))
            JsonNode after = root.path("after");          // new row after change
            JsonNode before = root.path("before");        // old row before change

            System.out.println("=== CDC EVENT ===");
            System.out.println("op = " + op);
            System.out.println("after = " + after);
            System.out.println("before = " + before);
            System.out.println("================");
        } catch (Exception e) {
            System.out.println("Could not parse message: " + message);
        }
    }
}