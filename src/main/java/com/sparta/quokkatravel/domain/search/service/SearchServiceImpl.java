package com.sparta.quokkatravel.domain.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.sparta.quokkatravel.domain.coupon.entity.CouponStatus;
import com.sparta.quokkatravel.domain.coupon.entity.CouponType;
import com.sparta.quokkatravel.domain.search.document.AccommodationDocument;
import com.sparta.quokkatravel.domain.search.document.CouponDocument;
import com.sparta.quokkatravel.domain.search.dto.SearchAccommodationRes;
import com.sparta.quokkatravel.domain.search.dto.SearchCouponRes;
import com.sparta.quokkatravel.domain.search.repository.AccommodationSearchRepository;
import com.sparta.quokkatravel.domain.search.repository.CouponSearchRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.elasticsearch.repository.query.parser.ElasticsearchQueryCreator;
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
            MultiMatchQuery multiMatchQuery = new MultiMatchQuery.Builder()
                    .query(name)
                    .fields("name", "koreanPartOfName", "englishPartOfName")
                    .build();
            mustQuery.add(new Query.Builder()
                    .multiMatch(multiMatchQuery)
                    .build());
        }
        if (address != null && !address.isEmpty()) {
            MatchQuery matchQuery = new MatchQuery.Builder()
                    .field("address")
                    .query(address)
                    .build();
            mustQuery.add(new Query.Builder()
                    .match(matchQuery)
                    .build());
        }
        if (rating != null) {
            MatchQuery matchQuery = new MatchQuery.Builder()
                    .field("rating")
                    .query(rating)
                    .build();
            mustQuery.add(new Query.Builder()
                    .match(matchQuery)
                    .build());
        }

        BoolQuery boolQuery = new BoolQuery.Builder()
                .must(mustQuery).build();

        SearchRequest searchRequest = new SearchRequest.Builder()
                .index("index005")
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
            MultiMatchQuery multiMatchQuery = new MultiMatchQuery.Builder()
                    .query(name)
                    .fields("name", "koreanPartOfName", "englishPartOfName")
                    .build();
            mustQuery.add(new Query.Builder()
                    .multiMatch(multiMatchQuery)
                    .build());
        }
        if (couponType != null) {
            MatchQuery matchQuery = new MatchQuery.Builder()
                    .field("couponType")
                    .query(couponType.toString())
                    .build();
            mustQuery.add(new Query.Builder()
                    .match(matchQuery)
                    .build());
        }
        if (couponStatus != null) {
            MatchQuery matchQuery = new MatchQuery.Builder()
                    .field("couponStatus")
                    .query(couponStatus.toString())
                    .build();
            mustQuery.add(new Query.Builder()
                    .match(matchQuery)
                    .build());
        }

        BoolQuery boolQuery = new BoolQuery.Builder()
                .must(mustQuery).build();

        SearchRequest searchRequest = new SearchRequest.Builder()
                .index("coupon1")
                .query(new Query.Builder().bool(boolQuery).build()).build();

        SearchResponse<CouponDocument> response = elasticsearchClient.search(searchRequest, CouponDocument.class);

        List<CouponDocument> documentList = new ArrayList<>();

        for (Hit<CouponDocument> hit : response.hits().hits()) {
            documentList.add(hit.source());
        }

        return documentList;
    }

    @Override
    public void deleteDocuments() {
        accommodationSearchRepository.deleteAll();
    }

}
