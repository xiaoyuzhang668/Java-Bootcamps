package ca.lichangzhang.SuperheroSighting.dto;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
public class Hero {

    private int heroId;

    @NotBlank(message = "Hero name must not be empty.")
    @Size(max = 100, message = "Hero name must be less than 100 characters.")
    private String name;

    private Boolean isHero;

    @NotBlank(message = "Hero name must not be blank")
    @Size(max = 255, message = "Hero description must be fewer than 255 characters")
    private String description;

    private Power power;

    private byte[] photo;
    private List<Organization> organizations;

    public Hero(){
        
    }
                //set orderNumber readonly
    public Hero(int heroId) {
        this.heroId = heroId;
    }
    
    
    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsHero() {
        return isHero;
    }

    public void setIsHero(Boolean isHero) {
        this.isHero = isHero;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public String getTempPhoto() {
        String tempPhoto = null;
        if (this.getPhoto() != null) {
            tempPhoto = "data:image/jpeg;base64," + Base64.getMimeEncoder().encodeToString(this.getPhoto());
        }
        return tempPhoto;
    }   

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.heroId;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.isHero);
        hash = 37 * hash + Objects.hashCode(this.description);
        hash = 37 * hash + Objects.hashCode(this.power);
        hash = 37 * hash + Arrays.hashCode(this.photo);
        hash = 37 * hash + Objects.hashCode(this.organizations);
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
        final Hero other = (Hero) obj;
        if (this.heroId != other.heroId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.isHero, other.isHero)) {
            return false;
        }
        if (!Objects.equals(this.power, other.power)) {
            return false;
        }
        if (!Arrays.equals(this.photo, other.photo)) {
            return false;
        }
        if (!Objects.equals(this.organizations, other.organizations)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Hero{" + "heroId=" + heroId + ", name=" + name + ", isHero=" + isHero + ", description=" + description + ", power=" + power + ", photo=" + photo + ", organizations=" + organizations + '}';
    }
}
