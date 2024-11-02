package com.sparta.quokkatravel.domain.search.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryEntity;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "index002")
public class AccommodationDocument {

    private static final Logger log = LoggerFactory.getLogger(AccommodationDocument.class);
    @Id
    private String id = UUID.randomUUID().toString();

    @Field(type = FieldType.Long)
    private Long accommodationId;

    @Field(type = FieldType.Text, analyzer = "mixed_korean_english_analyzer", searchAnalyzer = "whitespace")
    private String name;

    @Field(type = FieldType.Text, analyzer = "nori_analyzer", searchAnalyzer = "whitespace")
    private String address;

    @Field(type = FieldType.Long)
    private Long rating;

    @Field(type = FieldType.Text)
    private String imageurl;

    public AccommodationDocument(Accommodation accommodation) {
        this.accommodationId = accommodation.getId();
        this.name = accommodation.getName();
        this.address = accommodation.getAddress();
        this.rating = accommodation.getRating();
        this.imageurl = accommodation.getImageurl();

        System.out.println("Accommodation document created: " + this);
    }

    public void update(Accommodation accommodation) {
        this.name = accommodation.getName();
        this.address = accommodation.getAddress();
        this.rating = accommodation.getRating();
        this.imageurl = accommodation.getImageurl();
    }

}
