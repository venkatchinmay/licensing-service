package com.chinmay.licensing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/*

    en: English - default
    fr: French
    de: German
    es: Spanish
    it: Italian
    pt: Portuguese
    ja: Japanese
    ko: Korean
    zh: Chinese
    ru: Russian
    ar: Arabic
    hi: Hindi
    tr: Turkish
    nl: Dutch
    pl: Polish
    sv: Swedish
    fi: Finnish
    no: Norwegian
    da: Danish
    cs: Czech

 */
@Configuration
public class LicenseConfiguration {

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // Don't throw error if message is not found, instead return error code
        messageSource.setUseCodeAsDefaultMessage(true);
        // set baseNames of the language properties files
        messageSource.setBasenames("messages");
        return messageSource;
    }
}
