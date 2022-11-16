package ca.lichangzhang.SuperheroSighting.dao;

import ca.lichangzhang.SuperheroSighting.dto.Power;
import java.util.List;

/**
 * Email: xiaoyuzhang668@gmail.com Date: 2022
 *
 * @author catzh
 */
public interface PowerDao {

    Power getPowerById(int powerId);

    List<Power> getAllPowers();

    Power addPower(Power power);

    void updatePower(Power power);

    void deletePowerById(int powerId);
}
