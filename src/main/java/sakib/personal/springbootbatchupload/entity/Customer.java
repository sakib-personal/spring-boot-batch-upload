package sakib.personal.springbootbatchupload.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer {
	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "gender")
	private String gender;

	@Column(name = "phone")
	private String phone;

	@Column(name = "country")
	private String country;

	@Column(name = "date_of_birth")
	private String dateOfBirth;

	@Column(name = "AGE")
	private String age;
}