package ca.lichangzhang.SuperheroSighting.service;

/**　Email: xiaoyuzhang668@gmail.com
 *   Date: 2022
 *
 *  @author catzh
 */
public class SuperHeroNullException extends Exception{
       public SuperHeroNullException(String message) {
        super(message);
    }

    public SuperHeroNullException(String message,
            Throwable cause) {
        super(message, cause);
    }

}
