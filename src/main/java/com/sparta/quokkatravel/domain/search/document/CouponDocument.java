package com.sparta.quokkatravel.domain.search.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@NoArgsConstructor
@Document(indexName = "accommodations2")
public class CouponDocument {
}
