package com.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.MyRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RestController
@RequestMapping("/api")
public class MyController {

    @GetMapping("/getExample")
    public ResponseEntity<String> getExample() {
        return ResponseEntity.ok("GET request received successfully!");
    }

    @PostMapping("/postExample")
    public ResponseEntity<String> postExample(@RequestBody MyRequestDto requestDto) {
        if (requestDto == null || requestDto.getStringValue() == null || requestDto.getIntValue() <= 0) {
            return ResponseEntity.badRequest().body("Invalid request data");
        }
        return ResponseEntity.ok("POST request received successfully!");
    }

    @DeleteMapping("/deleteExample")
    public ResponseEntity<String> deleteExample() {
        return ResponseEntity.ok("DELETE request received successfully!");
    }

    @PutMapping("/putExample")
    public ResponseEntity<String> putExample() {
        return ResponseEntity.ok("PUT request received successfully!");
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetExample() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getExample"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("GET request received successfully!"));
    }

    @Test
    void testPostExampleWithValidData() throws Exception {
        MyRequestDto requestDto = new MyRequestDto();
        requestDto.setStringValue("example");
        requestDto.setIntValue(42);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/postExample")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"stringValue\":\"example\",\"intValue\":42}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("POST request received successfully!"));
    }

    @Test
    void testPostExampleWithInvalidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/postExample")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"stringValue\":null,\"intValue\":0}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid request data"));
    }

    @Test
    void testDeleteExample() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/deleteExample"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("DELETE request received successfully!"));
    }

    @Test
    void testPutExample() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/putExample"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("PUT request received successfully!"));
    }
}