package com.tpe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Enumerated(EnumType.STRING) //db'ye enum type hangi turde kaydedilecek bunu @Enumerated ile yapmaliyiz - EnumType.STRING veya EnumType.ORDINAL kullanilabilir
    //enum sabitlerinin db'de string olarak saklanmasini saglar
    @Column(nullable = false)
    private RoleType type;
}
