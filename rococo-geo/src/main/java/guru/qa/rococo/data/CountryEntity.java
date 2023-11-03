package guru.qa.rococo.data;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "country")
public class CountryEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", columnDefinition = "BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), TRUE))")
    private UUID id;

    @Column(name = "name")
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
