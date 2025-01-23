package com.tpe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "t_user") //user isminde postgree 'de kendi varsayilan tablosu oldugu icin kabul etmez biz burda tekrar isimlendirdik
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50, unique = true)
    private String userName;

    @Column(nullable = false, length = 255) //password hash'lenecegi icin length kismini 255 yaptim cunku girilen passworde ekelemler olcak
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    //SSecurity(AuthProvider) userı getirdiğinde
    //rolleri de gelmeli ki Yetkisini kontrol edebilsin!!!
    //burda rollerede ulasabilmesi icin kullanicilari ve rolleride getirebilmesi gerek
    //burada kullanicilarinrole ile iliskisini kurmak yeterli tek tarafli
    private Set<Role> roles = new HashSet<>(); //bir kullanicidan birden fazla rolu olabilir
}
