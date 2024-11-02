package com.sparta.quokkatravel.domain.search.document;

import lombok.Getter;

@Getter
public class LanguageSeparator {

    private final String englishPart;
    private final String koreanPart;

    // 생성자
    public LanguageSeparator(String input) {
        this.englishPart = extractEnglish(input);
        this.koreanPart = extractKorean(input);
    }

    // 영어 부분 추출 메서드
    private String extractEnglish(String input) {
        StringBuilder englishBuilder = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.toString(c).matches("[A-Za-z]")) {
                englishBuilder.append(c);
            }
        }
        return englishBuilder.toString();
    }

    // 한국어 부분 추출 메서드
    private String extractKorean(String input) {
        StringBuilder koreanBuilder = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.toString(c).matches("[가-힣]")) {
                koreanBuilder.append(c);
            }
        }
        return koreanBuilder.toString();
    }

}
