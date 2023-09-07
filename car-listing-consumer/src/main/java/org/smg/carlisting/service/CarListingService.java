package org.smg.carlisting.service;

import org.smg.carlisting.controller.dto.CarListingDto;
import org.smg.carlisting.exception.CarListingNotFoundException;
import org.smg.carlisting.mapper.CarListingMapper;
import org.smg.carlisting.model.CarListing;
import org.smg.carlisting.model.CarListingFilterRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarListingService {

    ElasticsearchOperations elasticsearchOperations;
    CarListingMapper carListingMapper;

    public CarListingService(ElasticsearchOperations elasticsearchOperations, CarListingMapper carListingMapper) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.carListingMapper = carListingMapper;
    }

    public CarListing save(CarListing carListing) {
        return elasticsearchOperations.save(carListing);
    }

    public void delete(String id) {
        elasticsearchOperations.delete(id, CarListing.class);
    }

    public void update(CarListing carListing) {
        elasticsearchOperations.update(carListing);
    }

    public CarListingDto findById(String id) {
        var carListing = elasticsearchOperations.get(id, CarListing.class);
        if (carListing == null) {
            throw new CarListingNotFoundException("Car Listing not found. Id:" + id);
        }
        return carListingMapper.mapCarListingDto(carListing);
    }

    public List<CarListingDto> findAll(CarListingFilterRequest carListingFilterRequest) {

        Query searchQuery = getFilterQuery(carListingFilterRequest);

        SearchHits<CarListing> carListingSearchHits = elasticsearchOperations
                .search(searchQuery,
                        CarListing.class,
                        IndexCoordinates.of("car-listing"));

        List<CarListing> carListings = new ArrayList<>();
        carListingSearchHits.forEach(s -> carListings.add(s.getContent()));

        return carListings.stream().map(carListing -> carListingMapper.mapCarListingDto(carListing)).collect(Collectors.toList());
    }

    private static Query getFilterQuery(CarListingFilterRequest carListingFilterRequest) {
        Criteria criteria = new Criteria();

        if (carListingFilterRequest.id() != null && !carListingFilterRequest.id().isEmpty()) {
            criteria.and(new Criteria("id").is(carListingFilterRequest.id()));
        }
        if (carListingFilterRequest.make() != null && !carListingFilterRequest.make().isEmpty()) {
            criteria.and(new Criteria("make").is(carListingFilterRequest.make()));
        }
        if (carListingFilterRequest.model() != null && !carListingFilterRequest.model().isEmpty()) {
            criteria.and(new Criteria("model").is(carListingFilterRequest.model()));
        }
        if (carListingFilterRequest.year() != null) {
            criteria.and(new Criteria("year").is(carListingFilterRequest.year()));
        }
        Query searchQuery = new CriteriaQuery(criteria);

        if (carListingFilterRequest.sortBy() != null && !carListingFilterRequest.sortBy().isEmpty()) {
            searchQuery.addSort(Sort.by(carListingFilterRequest.sortBy()).ascending());
        }

        if (carListingFilterRequest.pageNo() != null && carListingFilterRequest.pageSize() != null) {
            searchQuery.setPageable(PageRequest.of(carListingFilterRequest.pageNo(), carListingFilterRequest.pageSize()));
        }

        return searchQuery;
    }
}
