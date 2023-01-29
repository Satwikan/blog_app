package com.example.user_management.blog;

import com.example.user_management.appuser.AppUser;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Blog {

    @SequenceGenerator(name = "blog_sequence", sequenceName = "blog_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blog_sequence")
    private Long id;
    private String title;
    private String body;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    private AppUser author;

    private long createdAt;
    private long updatedAt;
}
