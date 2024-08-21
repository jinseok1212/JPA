package com.example.jpa.memo.repository;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.MemberMemoDTO;
import com.example.jpa.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemoCustomRepository {

    int updateTest(String writer, Long mno);
    List<Memo> mtoJoin1(Long mno);//manyToOne조인 (N + 1문제) - 조인을 하기 위해 추가적인 select 구문을 날리는 행위
    List<Object[]> mtoJoin2(Long mno); //두개 이상의 엔티티 동시에 조회 (반환 Object[])
    List<Object[]> mtoJoin3(String writer); //연관관계가 없는 (FK제약 x)  엔티티 조인
    List<Memo> mtoJoin4(); //성능향상 FEtch조인 (로딩전략보다 무조건 우선시 적용됨)

    ///////////////////////////////////////////////
    //원투매니방식
    Member otmJoin(String id); //OneToMany 조인
    List<Member> otmJoin2(String id); //Fetch Join - 1:N 조인에서 Fetch방식은 중복행을 생성( 중복행 제거 distinct)

    /////////////////////////////////////////////////////////////////////
    //다대일 양방향 맵핑 사용하세요~~

    //DTO로 반환받기
    List<MemberMemoDTO> otmJoin3(String id);
//    Page<MemberMemoDTO> joinPage(String text, Pageable pageable);
}
