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
      String userdir="file:///"+System.getProperty("user.dir");
      System.out.println("dir is"+System.getProperty("user.dir"));
      userdir=userdir.replace("\\","/");
      System.out.println(userdir);
    registry.addResourceHandler("/upload/**").addResourceLocations(userdir+("/src/main/upload/"));
  }
}