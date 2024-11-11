//package com.sparta.quokkatravel.domain.common.batch;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//
//public interface ItemProcessor<I, O> {
//
//    /*
//    * ItemProcessor 는 Reade r에서 넘겨받은 데이터를 개별 건으로 가공하고 처리하는 역할을 함
//    *
//    * 변환 : Reader 에서 챙긴 데이터를 가지고 원하는 타입으로 변환해서 Writer 에 넘겨줌
//    *
//    * 필터 : Reader 에서 넘겨준 데이터를 Writer 로 넘겨줄지 설정할 수 있음
//    *       null 을 반환하면 Writer 에게 전달이 안됨
//    *
//    * I : ItemReader 에서 받을 데이터 타입
//    * O : ItemWriter 에 보낼 데이터 타입
//    */
//
//    @Nullable
//    O preocess(@Nonnull I item) throws Exception;
//}
