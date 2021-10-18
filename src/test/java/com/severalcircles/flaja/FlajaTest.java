package com.severalcircles.flaja;

import com.severalcircles.flaja.data.user.UserNotFoundException;
import com.severalcircles.flames.data.user.FlamesUser;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FlajaTest {
    public void ZeroUserTest() throws UserNotFoundException, IOException, InterruptedException {
        Flaja flaja = new Flaja();
        FlamesUser zeroUser = flaja.getUserByID("0");
        assert zeroUser.getScore() == 0;
    }

}