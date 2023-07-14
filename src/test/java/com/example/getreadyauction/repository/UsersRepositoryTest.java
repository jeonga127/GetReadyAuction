package com.example.getreadyauction.repository;

import com.example.getreadyauction.domain.scheduler.service.SchedulerService;
import com.example.getreadyauction.domain.user.repository.UsersRepository;
import com.example.getreadyauction.domain.user.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@MockBean(SchedulerService.class)
class UsersRepositoryTest {

    @Autowired
    UsersRepository usersRepository;

    @DisplayName("이름이 일치하는 회원 조회")
    @Test
    void findByUsername() {
        //given
        Users testUser = new Users("testname1", "@testPassword1");
        usersRepository.save(testUser);

        //when
        Optional<Users> findResult = usersRepository.findByUsername("testname1");

        //given
        assertThat(findResult).isPresent();
        assertThat(findResult.get()).isEqualTo(testUser);
    }
}