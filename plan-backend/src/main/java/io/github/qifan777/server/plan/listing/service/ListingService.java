package io.github.qifan777.server.plan.listing.service;

import io.github.qifan777.server.plan.listing.repository.ListingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ListingService {
    private final ListingRepository listingRepository;

}