package com.sparta.quokkatravel.domain.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import com.sparta.quokkatravel.domain.search.document.CouponDocument;
import com.sparta.quokkatravel.domain.search.repository.AccommodationSearchRepository;
import com.sparta.quokkatravel.domain.search.repository.CouponSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final AccommodationSearchRepository accommodationSearchRepository;
    private final CouponSearchRepository couponSearchRepository;
    private final ElasticsearchClient elasticsearchClient;

    @Override
    public List<AccommodationDocument> searchAccommodations(String name, String address, Long rating) throws IOException {

        List<Query> mustQuery = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            Query multiMatchQuery = new Query.Builder()
                    .multiMatch(m -> m
                            .query(name)
                            .fields(List.of("name", "name.korean", "name.english"))
                            .minimumShouldMatch("75%")
                    ).build();
            mustQuery.add(multiMatchQuery);
        }
        if (address != null && !address.isEmpty()) {
            Query termQuery = new Query.Builder()
                    .term(t -> t.field("address").value(address))
                    .build();
            mustQuery.add(termQuery);
        }
        if (rating != null) {
            Query termQuery = new Query.Builder()
                    .term(t -> t.field("rating").value(rating))
                    .build();
            mustQuery.add(termQuery);
        }

        BoolQuery boolQuery = new BoolQuery.Builder()
                .must(mustQuery).build();

        SearchRequest searchRequest = new SearchRequest.Builder()
                .index("accommodations")
                .query(new Query.Builder().bool(boolQuery).build()).build();

        SearchResponse<AccommodationDocument> response = elasticsearchClient.search(searchRequest, AccommodationDocument.class);

        List<AccommodationDocument> documentList = new ArrayList<>();

        for (Hit<AccommodationDocument> hit : response.hits().hits()) {
            documentList.add(hit.source());
        }

        return documentList;

    }

    @Override
    public List<CouponDocument> searchCoupons(String name, CouponType couponType, CouponStatus couponStatus) throws IOException {

        List<Query> mustQuery = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            Query multiMatchQuery = new Query.Builder()
                    .multiMatch(m -> m
                            .query(name)
                            .fields(List.of("name.korean", "name.english"))
                            .minimumShouldMatch("75%")
                    ).build();
            mustQuery.add(multiMatchQuery);
        }
        if (couponType != null) {
            Query termQuery = new Query.Builder()
                    .term(t -> t.field("couponType").value(couponType.toString()))
                    .build();
            mustQuery.add(termQuery);
        }
        if (couponStatus != null) {
            Query termQuery = new Query.Builder()
                    .term(t -> t.field("couponStatus").value(couponStatus.toString()))
                    .build();
            mustQuery.add(termQuery);
        }

        BoolQuery boolQuery = new BoolQuery.Builder()
                .must(mustQuery).build();

        SearchRequest searchRequest = new SearchRequest.Builder()
                .index("coupons")
                .query(new Query.Builder().bool(boolQuery).build()).build();

        SearchResponse<CouponDocument> response = elasticsearchClient.search(searchRequest, CouponDocument.class);

        List<CouponDocument> documentList = new ArrayList<>();

        for (Hit<CouponDocument> hit : response.hits().hits()) {
            documentList.add(hit.source());
        }

        return documentList;
    }

}
