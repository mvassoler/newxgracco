package com.mvassoler.newxgracco.newxgracco.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "domains_ad",
        indexes = {@Index(name = "domains_ad_username_password", columnList = "ldap_username, ldap_password", unique = true),
                @Index(name = "domains_ad_username_password", columnList = "domain_id", unique = true)}
)
public class DomainAd implements Serializable {
    @Serial
    private static final long serialVersionUID = 1831517141173477771L;

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @Column(name = "deleted")
    private LocalDateTime deleted;

    @Column(name = "ldap_base_dn", nullable = false)
    private String ldapBaseDn;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @Column(name = "ldap_url")
    private String ldapUrl;

    @Column(name = "ldap_username")
    private String ldapUserName;

    @Column(name = "ldap_password")
    private String ldapPassword;

    @Column(name = "password_encrypt")
    private Boolean passwordEncrypt;

    @Column(name = "prefix_remove")
    private String prefixRemove;

    @Column(name = "prefix_default")
    private String prefixDefault;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DomainAd domainAd = (DomainAd) o;
        return getId() != null && Objects.equals(getId(), domainAd.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
