package guru.qa.rococo.db.model.userdata;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "\"user\"")
public class UserDataUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column()
    private String firstname;

    @Column()
    private String surname;

    @Column(name = "photo", columnDefinition = "bytea")
    private byte[] photo;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDataUserEntity that = (UserDataUserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(firstname, that.firstname) && Objects.equals(surname, that.surname) && Arrays.equals(photo, that.photo);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, firstname, surname);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
