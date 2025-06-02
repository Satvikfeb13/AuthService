package org.example.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.checkerframework.checker.interning.qual.InternedDistinct;
import static jakarta.persistence.GenerationType.*;
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class UserRole {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long roleID;
    private String name;
}
