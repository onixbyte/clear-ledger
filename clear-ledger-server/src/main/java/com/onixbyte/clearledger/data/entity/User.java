package com.onixbyte.clearledger.data.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.RelationManyToMany;
import com.mybatisflex.annotation.Table;
import com.onixbyte.clearledger.data.domain.UserDomain;
import com.onixbyte.clearledger.data.view.UserView;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table("users")
public class User {

    @Id(keyType = KeyType.None)
    private Long id;

    private String username;

    private String password;

    private String email;

    private LocalDateTime createdAt;

    /**
     * Convert object to domain object.
     *
     * @return the domain object
     */
    public UserDomain toDomain() {
        return UserDomain.builder()
                .id(getId())
                .username(getUsername())
                .password(getPassword())
                .email(getEmail())
                .build();
    }

    public UserView toView() {
        return UserView.builder()
                .id(String.valueOf(getId()))
                .username(getUsername())
                .email(getEmail())
                .build();
    }

}
