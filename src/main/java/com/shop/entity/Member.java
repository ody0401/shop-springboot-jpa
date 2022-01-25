package com.shop.entity;

import com.shop.constant.Role;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "member")
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long id;

    @Column(length = 50, nullable = false)
    private String userName;

    @Column(length = 100, nullable = false, unique = true)
    private String userId;

    @Column(length = 200, nullable = false)
    private String userPw;

    @Enumerated(EnumType.STRING)
    private Role role;

}
