package com.example.jpa.memo.repository;

import com.example.jpa.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long>,//엔티티타입, ID에 대한 타입
                                        MemoCustomRepository {//커스텀레포지토리
    //JpaRepository로 부터, 몇개의 추상메서드를 자동으로 상속받게 됩니다.

    //쿼리메서드
    Memo findByWriterAndText(String writer, String text);
    List<Memo> findByMnoLessThan(Long mno);

    Memo findByMno(Long mno);

    List<Memo> findByMnoBetween(Long mno1, Long mno2);

    List<Memo> findByWriterLike(String like);

    Memo findByWriterOrderByWriterDesc(String Writer);

    List<Memo> findByMnoIn(List<Long> mnos);
//쿼리메서드의 마지막 매개변수에 Pageable을 주면, 페이징처리합니다.
    List<Memo> findByMnoLessThanEqual(Long mno, Pageable pageable);


    ////////////////////////////////////////////////////
    //JPQL - SQL과 비슷하나, 엔티티를 사용해서 sql문을 작성
    //select, update, delete는 제공되는데, insert는 제공되지 않음
    //1. 테이블명이 아니라 엔티티가 사용됨
    //2. 속성(필드)은 대소문자를 전부 구분
    //3. 별칭은 필수
    //4. SQL키워드 구분 X

    @Query("select m from Memo m order by m.mno desc")
    List<Memo> getListDesc(); //메서드명 자유

    //JPQL 파라미터 전달 @Param(이름), :이름
    @Query("select m from Memo m where m.mno > :num order by m.mno desc")
    List<Memo> getListDesc2(@Param("num") Long mno);

    //JPQL select문의 실행 결과를 선별적으로 받으려면 Object[] 사용함
    @Query("select m.writer, m.text from Memo m where m.mno > :num order by m.mno desc")
    List<Object[]> getListDesc3(@Param("num") Long mno);

    //JPQL 업데이트
    @Modifying //업데이트임
    @Transactional //트랜잭션 반영
    @Query("update Memo m set m.writer = :a where m.mno = :b")
    int updateMemo(@Param("a") String a, @Param("b") Long b);

    //JPQL업데이트 - 객체파라미터를 넘기는 방법#{객체}
    @Transactional
    @Modifying
    @Query("update Memo m set m.writer = :#{#a.writer}, m.text = :#{#a.text} where m.mno = :#{#a.mno}")
    int updateMemo(@Param("a") Memo memo);

    //delete from memo where mno = 10;
    //JPQL 딜리트문 -
    @Transactional
    @Modifying
    @Query("delete from Memo m where m.mno = :a")
    int deleteMemo(@Param("a") Long a);

    //JPQL 마지막 매개변수에 Pageable을 주면, 페이지처리 합니다.
    //page처리에는 countQuery가 필요합니다 (countQuery구문은 직접 작성하는게 가능함)
    @Query(value = "select m from Memo m where m.mno <= :a",
    countQuery = "select count(m) from Memo m where m.mno <= :a")
    Page<Memo> getListJPQL(@Param("a") Long mno, Pageable pageable);

    //select mno, writer, text, concat(writer, text) as col, current_timestamp
    //from memo
    //where mno <= 100;
    //페이지네이션 처리하는 sql

    @Query("select m.mno, m.writer, m.text, concat(m.writer, m.text) as col, current_timestamp " +
            "from Memo m where m.mno <= :a")
    Page<Object[]> getQuiz(@Param("a") Long mno, Pageable pageable);


    //네이티브쿼리 - JPQL이 너무 어려우면, SQL방식으로 사용하는 것을 제공해줌
    @Query(value = "select * from memo where mno = ?", nativeQuery = true)
    Memo getNative(Long mno);
}

