package ca.lichangzhang.SuperheroSighting.dto;

import ca.lichangzhang.SuperheroSighting.service.DoubleNotNullConstraint;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.lang.Nullable;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
public class Location {

    private int locationId;

    @NotBlank(message = "Location name must not be empty.")
    @Size(max = 100, message = "Location name must be less than 100 characters.")
    private String name;

    @NotBlank(message = "Location description must not be empty.")
    @Size(max = 255, message = "Location description must be less than 255 characters.")
    private String description;

    @NotBlank(message = "Location address must not be empty.")
    @Size(max = 200, message = "Location address must be less than 200 characters.")
    private String address;

    @Size(max = 11, message = "Location phone must be less than 11 characters.")
    @Pattern(regexp = "^(0|[1-9][0-9]*|)$", message = "Location phone must be all numeric.") 
    private String phone;

    @DoubleNotNullConstraint(notEmpty = true, min = 1, max = 16,
            message = "Location Latitude must not be empty.", messageLength = " Location Latitude should be less than 16 characters.", 
            messageNumeric = " Location Latitude number should be all numeric.")
    private String latitude;

    @DoubleNotNullConstraint(notEmpty = true, min = 1, max = 16,
            message = "Location longitude must not be empty.", messageLength = " Location longitude should be less than 16 characters.", 
            messageNumeric = " Location longitude number should be all numeric.")
    private String longitude;

    public Location() {
        
    }
        public Location(int locationId) {
        this.locationId = locationId;
    }
        
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTempUrl() {
        String lat = this.getLatitude();
        String tempUrl = null;
        if (lat != null) {
            tempUrl = "https://www.google.com/maps/embed/v1/place?key=AIzaSyBFw0Qbyq9zTFTd-tUY6dZWTgaQzuU17R8&q="
                    + this.latitude + ", " + this.longitude + "&zoom=4";
        }
        return tempUrl;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.locationId;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.description);
        hash = 89 * hash + Objects.hashCode(this.address);
        hash = 89 * hash + Objects.hashCode(this.latitude);
        hash = 89 * hash + Objects.hashCode(this.longitude);
        hash = 89 * hash + Objects.hashCode(this.phone);
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
        final Location other = (Location) obj;
        if (this.locationId != other.locationId) {
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
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Location{" + "locationId=" + locationId + ", name=" + name + ", description=" + description + ", address=" + address + ", latitude=" + latitude + ", longitude=" + longitude + ", phone=" + phone + '}';
    }
}
