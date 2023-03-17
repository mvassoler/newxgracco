package com.mvassoler.newxgracco.newxgracco.domain;

import com.mvassoler.newxgracco.newxgracco.enums.AuthenticationProvider;
import com.mvassoler.newxgracco.newxgracco.enums.PersonalType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@Table(name = "domains",
        indexes = {@Index(name = "domain_identification_key", columnList = "identification_number", unique = true),
                @Index(name = "domain_identification_name_key", columnList = "identification_name")}
)
public class Domain implements Serializable {

    @Serial
    private static final long serialVersionUID = 5915136691466203553L;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "authentication_provider", length = 20, nullable = false)
    private AuthenticationProvider authenticationProvider;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "identification_number", length = 14, nullable = false)
    private String identificationNumber;

    @Column(name = "phone_number", length = 13)
    private String phoneNumber;

    @Column(name = "cell_phone_number", length = 13)
    private String cellPhoneNumber;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "personal_type", length = 2, nullable = false)
    private PersonalType personalType;

    @Column(name = "identification_name")
    private String identificationName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Domain domain = (Domain) o;
        return getId() != null && Objects.equals(getId(), domain.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
