package com.kaishengit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/buy/movie/{id}")
    public String buyMovie(@PathVariable Integer id) {

        //根据服务名称从Eureka上发现服务的提供者，并使用负载均衡的方式返回提供者的地址
        ServiceInstance serviceInstance = loadBalancerClient.choose("MOVIE-SERVICE-PROVIDER");
        System.out.println("uri:" + serviceInstance.getUri());
        System.out.println("serviceId:" + serviceInstance.getServiceId());
        //String url = "http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/movie/"+id;
        String url = serviceInstance.getUri().toString() + "/movie/"+id;
        return restTemplate.getForObject(url,String.class);
    }

}
