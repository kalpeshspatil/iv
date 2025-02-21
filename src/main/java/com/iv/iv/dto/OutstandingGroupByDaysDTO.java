package com.iv.iv.dto;

public class OutstandingGroupByDaysDTO {
    private String dayGroup;
    private Long totalOutstanding;

    public OutstandingGroupByDaysDTO(String dayGroup, Long totalOutstanding) {
        this.dayGroup = dayGroup;
        this.totalOutstanding = totalOutstanding;
    }

    // Getters and setters
    public String getDayGroup() {
        return dayGroup;
    }

    public void setDayGroup(String dayGroup) {
        this.dayGroup = dayGroup;
    }

    public Long getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(Long totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }
}

