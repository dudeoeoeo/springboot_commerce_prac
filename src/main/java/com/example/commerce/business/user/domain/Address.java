package com.example.commerce.business.user.domain;

import com.example.commerce.business.user.dto.request.AddressAddRequest;
import lombok.*;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder @ToString
@Table(name = "address")
public class Address {

    @Id @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "jibun")
    private String jiBun;

    @Column(name = "road")
    private String road;

    @Column(name = "building")
    private String building;

    @Column(name = "detail")
    private String detail;

    public static Address newAddress(User user, AddressAddRequest request) {
        return Address.builder()
                .user(user)
                .zipcode(request.getZipcode())
                .jiBun(request.getJiBun())
                .road(request.getRoad())
                .building(request.getBuilding())
                .detail(request.getDetail())
                .build();
    }
}
