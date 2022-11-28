package ca.lichangzhang.SuperheroSighting.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
public class Sighting {

    private int sightingId;

    private Hero hero;

    private Location location;

    @NotBlank(message = "Sighting description must not be empty.")
    @Size(max = 255, message = "Sighting description must be less than 255 characters")
    private String description;

    @NotBlank(message = "Sighting Date must not be empty.")
    private String sightingDate;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Sighting() {
    }

    public Sighting(int sightingId) {
        this.sightingId = sightingId;
    }

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(String sightingDate) {
        this.sightingDate = sightingDate;
    }

    public LocalDateTime getFormatted() {
        LocalDateTime formatted = null;
        if (this.getSightingDate() != null) {
            formatted = LocalDateTime.parse(this.getSightingDate(), formatter);
        }
        return formatted;
    }

    public LocalDateTime getFormatted2() {
        LocalDateTime formatted2 = LocalDateTime.parse(this.getSightingDate(), formatter2);
        return formatted2;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.sightingId;
        hash = 29 * hash + Objects.hashCode(this.hero);
        hash = 29 * hash + Objects.hashCode(this.location);
        hash = 29 * hash + Objects.hashCode(this.description);
        hash = 29 * hash + Objects.hashCode(this.sightingDate);
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
        final Sighting other = (Sighting) obj;
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.hero, other.hero)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.sightingDate, other.sightingDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sighting{" + "sightingId=" + sightingId + ", hero=" + hero + ", location=" + location + ", description=" + description + ", sightingDate=" + sightingDate + '}';
    }
}
