package com.upgrade.pacificocean;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.upgrade.pacificocean.rest.resource.CampsiteResource;

@Component
public class JerseyConfig extends ResourceConfig {

   public JerseyConfig() {
	   register(CampsiteResource.class);
   }

}