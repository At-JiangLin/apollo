package com.ctrip.framework.apollo.metaservice.service;

import com.ctrip.framework.apollo.core.ServiceNameConsts;
import com.ctrip.framework.apollo.tracer.Tracer;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.model.HealthService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DiscoveryService {

  private final static ConsulClient consulClient = new ConsulClient("localhost");

  public List<HealthService> getConfigServiceInstances() {
    Response<List<HealthService>> healthyServices = consulClient.getHealthServices(ServiceNameConsts.APOLLO_CONFIGSERVICE, true, QueryParams.DEFAULT);

    if (healthyServices.getValue() == null) {
      Tracer.logEvent("Apollo.ConsulDiscovery.NotFound", ServiceNameConsts.APOLLO_METASERVICE);
    }

    return healthyServices.getValue() != null ? healthyServices.getValue() : Collections.emptyList();
  }


  public List<HealthService> getMetaServiceInstances() {
    Response<List<HealthService>> healthyServices = consulClient.getHealthServices(ServiceNameConsts.APOLLO_METASERVICE, true, QueryParams.DEFAULT);

    if (healthyServices.getValue() == null) {
      Tracer.logEvent("Apollo.ConsulDiscovery.NotFound", ServiceNameConsts.APOLLO_METASERVICE);
    }
    return healthyServices.getValue() != null ? healthyServices.getValue() : Collections.emptyList();
  }

  public List<HealthService> getAdminServiceInstances() {
    Response<List<HealthService>> healthyServices = consulClient.getHealthServices(ServiceNameConsts.APOLLO_ADMINSERVICE, true, QueryParams.DEFAULT);

    if (healthyServices.getValue() == null) {
      Tracer.logEvent("Apollo.EurekaDiscovery.NotFound", ServiceNameConsts.APOLLO_ADMINSERVICE);
    }
    return healthyServices.getValue() != null ? healthyServices.getValue() : Collections.emptyList();
  }
}
