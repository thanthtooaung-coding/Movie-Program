package com.moving_system;

import java.util.List;

/**
 * Interface for generating reports from movie data
 * Following Open/Closed Principle
 */
public interface IReportGenerator {
    void generateReport(List<Movie> movies);
}