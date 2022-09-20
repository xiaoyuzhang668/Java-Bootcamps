package ca.lichangzhang.guessnumber.service;

import ca.lichangzhang.guessnumber.models.Game;
import ca.lichangzhang.guessnumber.models.Round;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author catzh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceLayerImplTest {

    @Autowired
    ServiceLayerImpl service;

    /**
     * Test of calculateResult method, of class ServiceLayerImpl.
     */
    @Test
    public void testCalculateResult() {
        String answer = "1234";
        String guess = "5627";

        String result = service.calculateResult(guess, answer);

        assertEquals("e:0:p:1", result);
    }

    @Test
    public void calculateResult2() {
        String answer = "1234";
        String guess = "1234";

        String result = service.calculateResult(guess, answer);

        assertEquals("e:4:p:0", result);
    }

    @Test
    public void calculateResult3() {
        String answer = "5678";
        String guess = "1234";

        String result = service.calculateResult(guess, answer);

        assertEquals("e:0:p:0", result);
    }

    @Test
    public void calculateResult4() {
        String answer = "1234";
        String guess = "1938";

        String result = service.calculateResult(guess, answer);

        assertEquals("e:2:p:0", result);
    }

    @Test
    public void calculateResult5() {
        String answer = "1234";
        String guess = "4321";

        String result = service.calculateResult(guess, answer);

        assertEquals("e:0:p:4", result);
    }

    @Test
    public void calculateResult6() {
        String answer = "1234";
        String guess = "9843";

        String result = service.calculateResult(guess, answer);

        assertEquals("e:0:p:2", result);
    }
}
