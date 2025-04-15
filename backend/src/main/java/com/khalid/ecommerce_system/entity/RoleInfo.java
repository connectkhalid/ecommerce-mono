package com.khalid.ecommerce_system.entity;

import com.khalid.ecommerce_system.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "role_info")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class RoleInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_code", nullable = false)
    private Long roleCode;

    @Column (name = "role_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "delete_flg", nullable = false)
    private boolean deleteFlag;

    @Column(name = "created_dt", nullable = false)
    private Timestamp createdDt;

    @Column(name = "updated_dt")
    private Timestamp updatedDt;

    @OneToMany(targetEntity = AccountInfo.class, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "roleInfo", orphanRemoval = true)
    @ToString.Exclude
    private List<AccountInfo> accountInfos;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RoleInfo roleInfo = (RoleInfo) o;
        return getId() != null && Objects.equals(getId(), roleInfo.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
