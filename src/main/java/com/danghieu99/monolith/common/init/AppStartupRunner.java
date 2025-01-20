package com.danghieu99.monolith.common.init;

import com.danghieu99.monolith.auth.service.init.AccountInitService;
import com.danghieu99.monolith.auth.service.init.RoleInitService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppStartupRunner implements ApplicationRunner {

    private final RoleInitService roleInitService;
    private final AccountInitService accountInitService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        roleInitService.init();
        accountInitService.init();
    }
}
