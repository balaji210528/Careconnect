package com.careconnect.careconnect.model;

import jakarta.persistence.*;

@Entity
public class LabTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testType;
    private String testDate;
    private String timeSlot;

    // Getters and Setters
    public Long getId() { return id; }
    public String getTestType() { return testType; }
    public void setTestType(String testType) { this.testType = testType; }
    public String getTestDate() { return testDate; }
    public void setTestDate(String testDate) { this.testDate = testDate; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
}
