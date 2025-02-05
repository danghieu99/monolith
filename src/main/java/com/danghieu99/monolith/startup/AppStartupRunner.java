package com.danghieu99.monolith.startup;

import com.danghieu99.monolith.security.service.init.AccountInitService;
import com.danghieu99.monolith.security.service.init.RoleInitService;
import com.danghieu99.monolith.product.service.init.CategoryInitService;
import com.danghieu99.monolith.product.service.init.ProductInitService;
import com.danghieu99.monolith.product.service.init.ShopInitService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppStartupRunner implements ApplicationRunner {

    private final RoleInitService roleInitService;
    private final AccountInitService accountInitService;
    private final ProductInitService productInitService;
    private final CategoryInitService categoryInitService;
    private final ShopInitService shopInitService;

    @Override
    public void run(ApplicationArguments args) {
        roleInitService.init();
        accountInitService.init();
        categoryInitService.init();
        shopInitService.init();
        productInitService.init();
    }
}
