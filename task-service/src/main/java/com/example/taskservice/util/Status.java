package com.example.taskservice.util;

public enum Status {
    COMPLETED("Completed"), IN_PROGRESS("In Progress"), UNCOMPLETED("Uncompleted");

    private final String toString;

    Status(String toString) {
        this.toString = toString;
    }

    @Override
    public String toString() {
        return this.toString;
    }

    // Adding a method to get the enum from the custom output
    public static Status fromString(String text) {
        for (Status status : Status.values()) {
            if (status.toString.equals(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum with toString " + text + " was found");
    }
}
