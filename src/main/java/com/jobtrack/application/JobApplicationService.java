package com.jobtrack.application;

import com.jobtrack.common.ResourceNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JobApplicationService {

    private final JobApplicationRepository repository;

    public JobApplication create(JobApplicationRequest request) {
        JobApplication application = new JobApplication();
        apply(request, application);
        return repository.save(application);
    }

    @Transactional(readOnly = true)
    public JobApplication get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job application " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public Page<JobApplication> search(String company, ApplicationStatus status, String query, Pageable pageable) {
        Specification<JobApplication> specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (company != null && !company.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("company")), "%" + company.toLowerCase() + "%"));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (query != null && !query.isBlank()) {
                String pattern = "%" + query.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("company")), pattern),
                        cb.like(cb.lower(root.get("position")), pattern),
                        cb.like(cb.lower(root.get("location")), pattern)
                ));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
        return repository.findAll(specification, pageable);
    }

    public JobApplication update(Long id, JobApplicationRequest request) {
        JobApplication application = get(id);
        apply(request, application);
        return repository.save(application);
    }

    public JobApplication updateStatus(Long id, ApplicationStatus status) {
        JobApplication application = get(id);
        application.setStatus(status);
        return repository.save(application);
    }

    public void delete(Long id) {
        repository.delete(get(id));
    }

    private void apply(JobApplicationRequest request, JobApplication application) {
        application.setCompany(request.company());
        application.setPosition(request.position());
        application.setLocation(request.location());
        application.setJobUrl(request.jobUrl());
        application.setRecruiterName(request.recruiterName());
        application.setRecruiterEmail(request.recruiterEmail());
        application.setStatus(request.status() == null ? ApplicationStatus.SAVED : request.status());
        application.setAppliedDate(request.appliedDate());
        application.setNextFollowUpDate(request.nextFollowUpDate());
        application.setNotes(request.notes());
    }
}
