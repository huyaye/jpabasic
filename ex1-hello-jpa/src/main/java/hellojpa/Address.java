package hellojpa;

import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	private String city;

	private String street;

	private String zipcode;

	public Address(String city, String street, String zipcode) {
		super();
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}

	public String fullAddress() {
		return city + " " + street + " " + zipcode;
	}

	public boolean isValid() {
		return true;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	public String getZipcode() {
		return zipcode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Address address = (Address) o;
		return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(), address.getStreet())
				&& Objects.equals(getZipcode(), address.getZipcode());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
		return result;
	}

}
