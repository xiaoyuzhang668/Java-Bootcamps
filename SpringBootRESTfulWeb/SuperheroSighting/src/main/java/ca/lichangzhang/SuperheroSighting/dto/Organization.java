package ca.lichangzhang.SuperheroSighting.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
public class Organization {

    private int organizationId;

    @NotBlank(message = "Organization name must not be empty.")
    @Size(max = 100, message = "Organization name must be less than 100 characters.")
    private String name;

    @NotBlank(message = "Organization description must not be empty.")
    @Size(max = 255, message = "Organization description must be less than 255 characters.")
    private String description;

    @NotBlank(message = "Organization address must not be empty.")
    @Size(max = 200, message = "Organization address must be less than 200 characters.")
    private String address;

    @NotBlank(message = "Organization contact must not be empty.")
    @Size(max = 100, message = "Organization contact must be less than 100 characters.")
    private String contact;

   @Size(max = 11, message = "Organization phone must be less than 11 characters.")
   @Pattern(regexp = "^(0|[1-9][0-9]*|)$", message = "Organization phone must be all numeric.") 
    private String phone;

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.organizationId;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.address);
        hash = 97 * hash + Objects.hashCode(this.contact);
        hash = 97 * hash + Objects.hashCode(this.phone);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Organization other = (Organization) obj;
        if (this.organizationId != other.organizationId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.contact, other.contact)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Organization{" + "organizationId=" + organizationId + ", name=" + name + ", description=" + description + ", address=" + address + ", contact=" + contact + ", phone=" + phone + '}';
    }

}
