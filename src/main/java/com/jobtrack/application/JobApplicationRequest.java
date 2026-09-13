package com.jobtrack.application;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record JobApplicationRequest(
        @NotBlank String company,
        @NotBlank String position,
        String location,
        String jobUrl,
        String recruiterName,
        @Email String recruiterEmail,
        ApplicationStatus status,
        LocalDate appliedDate,
        LocalDate nextFollowUpDate,
        String notes
) {}
