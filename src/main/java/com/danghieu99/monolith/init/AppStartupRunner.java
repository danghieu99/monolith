package com.danghieu99.monolith.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private final RoleInitService roleInitService;
    private final AccountInitService accountInitService;

    public AppStartupRunner(RoleInitService roleInitService, AccountInitService accountInitService) {
        this.roleInitService = roleInitService;
        this.accountInitService = accountInitService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        roleInitService.init();
        accountInitService.init();
    }
}
