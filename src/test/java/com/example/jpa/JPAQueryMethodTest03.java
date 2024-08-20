package com.example.jpa;

import com.example.jpa.entity.Memo;
import com.example.jpa.memo.repository.MemoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class JPAQueryMethodTest03 {

    // 쿼리메서드 JPA인터페이스의 메서드 모형을 보고, SQL을대신 실행시킴(다양한 select구문 활용)
//    https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testCode01() {
//        Memo m = memoRepository.findByWriterAndText("example10","sample10");
//        System.out.println(m.toString());
        List<Memo> list = memoRepository.findByMnoLessThan(20L); //20보다 미만
        System.out.println(list.toString());

    }
    @Test
    public void testCode02() {
        List<Long> mnoList = Arrays.asList(10L, 20L, 30L, 40L, 50L);

        Memo m = memoRepository.findByMno(11L);
        List<Memo> list = memoRepository.findByMnoBetween(10L, 20L);
        List<Memo> m2 = memoRepository.findByWriterLike("%10%");
        Memo list2 = memoRepository.findByWriterOrderByWriterDesc("example1");
        List<Memo> list3 = memoRepository.findByMnoIn(mnoList);
        System.out.println(m.toString());
        System.out.println(list.toString());
        System.out.println(m2.toString());
        System.out.println(list2.toString());
        System.out.println(list3.toString());
    }


    @Test
    public void testCode03 () {
        Pageable pageable = PageRequest.of(0,10, Sort.by("mno").descending());
        List<Memo> list = memoRepository.findByMnoLessThanEqual(50L, pageable);
        for(Memo m : list ) {
            System.out.println(m.toString());
        }
    }

}
