package com.sparta.quokkatravel.domain.search.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "index005")
public class AccommodationDocument {

    @Id
    private String id = UUID.randomUUID().toString();

    @Field(type = FieldType.Long)
    private Long accommodationId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String koreanPartOfName;
    @Field(type = FieldType.Text)
    private String englishPartOfName;

    @Field(type = FieldType.Text)
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
        setPartOfName(accommodation.getName());
    }

    public void update(Accommodation accommodation) {
        this.name = accommodation.getName();
        this.address = accommodation.getAddress();
        this.rating = accommodation.getRating();
        this.imageurl = accommodation.getImageurl();
        setPartOfName(accommodation.getName());
    }

    public void setPartOfName(String name) {
        LanguageSeparator ls = new LanguageSeparator(name);
        this.koreanPartOfName = ls.getKoreanPart();
        this.englishPartOfName = ls.getEnglishPart();
    }

}
