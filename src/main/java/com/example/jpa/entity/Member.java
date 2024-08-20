package com.example.jpa.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class) //JPA엔티티를 관리하는 영역에서 자동으로 변수에 적용해주는 역할
//Auditing 기능을 활성화 시키려면 main클래스에 @EnablsJpaAuditing을 추가합니다.
public class Member {
    @Id //PK
    private String id;
    @Column(nullable = false, length = 50) //null허용x, 길이 50
    private String name;

    @CreatedDate //JPA를 통해서 인서트시에 날짜가 자동 입력됨
    @Column(updatable = false) //이 컬럼이 JPA에 의해서 자동으로 변경되는 것을 방지함
    private LocalDateTime signDate;

}
