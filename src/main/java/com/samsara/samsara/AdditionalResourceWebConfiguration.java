/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samsara.samsara;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
      System.out.println(System.getProperty("user.dir"));
    registry.addResourceHandler("/upload/**").addResourceLocations("file:///C:/Users/eldee/Documents/NetBeansProjects/spring/spring/brokerage/src/main/upload/");
  }
}