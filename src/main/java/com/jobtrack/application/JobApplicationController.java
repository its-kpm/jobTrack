package com.jobtrack.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplication create(@Valid @RequestBody JobApplicationRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public JobApplication get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public Page<JobApplication> search(
            @RequestParam(required = false) String company,
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(required = false) String q,
            Pageable pageable) {
        return service.search(company, status, q, pageable);
    }

    @PutMapping("/{id}")
    public JobApplication update(@PathVariable Long id,
                                 @Valid @RequestBody JobApplicationRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public JobApplication updateStatus(@PathVariable Long id,
                                       @RequestParam ApplicationStatus status) {
        return service.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
