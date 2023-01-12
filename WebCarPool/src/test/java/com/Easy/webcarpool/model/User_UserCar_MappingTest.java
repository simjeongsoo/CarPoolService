package com.Easy.webcarpool.model;

import com.Easy.webcarpool.repository.UserCarRepository;
import com.Easy.webcarpool.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
@ComponentScan(basePackages = {"package com.Easy.webcarpool.model"})
public class User_UserCar_MappingTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCarRepository userCarRepository;

    @DisplayName("User와 UserCar OneToOne 테스트")
    @Test
    void oneToOne_테스트() throws Exception {
        //given
        String username = "user1";
        String carType = "BMW";

        User user = User.builder().username(username).build();
        UserCar userCar = UserCar.builder().carType(carType).build();

        //when
        userCarRepository.save(userCar);
        user.updateUserCar(username, userCar);
        userRepository.save(user);

        //then
        User user1 = userRepository.findById(user.getId()).get();
        assertThat(user1.getUserCar().getCarType()).isEqualTo(carType);

    }

}
