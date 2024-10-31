package com.sparta.quokkatravel.domain.search.document;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@NoArgsConstructor
@Document(indexName = "accommodations2")
public class AccommodationDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "custom_analyzer", searchAnalyzer = "whitespace")
    private String name;

    @Field(type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard")
    private String address;

    @Field(type = FieldType.Long)
    private Long rating;

    @Field(type = FieldType.Text)
    private String imageurl;

    public AccommodationDocument(Accommodation accommodation) {
        this.name = accommodation.getName();
        this.address = accommodation.getAddress();
        this.rating = accommodation.getRating();
        this.imageurl = accommodation.getImageurl();
    }

}
