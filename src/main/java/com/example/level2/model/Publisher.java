package com.example.level2.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Publishers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Publisher {
    @Id
    @Column(name = "pub_id")
    private String id;

    @Column(name = "pub_name", nullable = false)
    private String name;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return name;
    }
}
