package com.cx.controller;


import com.cx.client.ProductClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Test1Controller {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private ProductClient productClient;

    /**
     * 使用RestTemplate的第一种方式调用商品服务
     * 缺点：url直接写死 线上部署的时候甚至不知道对应的服务部署到哪台服务器上，且对方可能有多个地址，这样写显然是不行的
     * @return
     */
    @RequestMapping("order-to-product-test1")
    public String getProductMsg1(){
        RestTemplate restTemplate=new RestTemplate();
        String response=restTemplate.getForObject("http://localhost:8080/getProductMsg",String.class);
        return response;

    }

    /**
     * 使用RestTemplate的第二种方式,通过loadBalanceClient负载均衡到某一台服务所部署的具体的服务器上
     * @return
     */
    @RequestMapping("order-to-product-test2")
    public String getProductMsg2(){
        //参数是需要远程调用的服务的名字（在eureka上的名字）
        ServiceInstance serviceInstance=loadBalancerClient.choose("PRODUCT");
        //获取主机ip
        String host=serviceInstance.getHost();
        //获取端口号
        int port=serviceInstance.getPort();
        String url=String.format("http://%s:%s",host,port+"getProductMsg");

        RestTemplate restTemplate=new RestTemplate();
        String response=restTemplate.getForObject(url,String.class);
        return response;
    }

    /**
     * 使用@LoadBalance注解
     * @return
     */
    @RequestMapping("order-to-product-test3")
    public String getProductMsg3(){
        String response=restTemplate.getForObject("http://PRODUCT/getProductMsg",String.class);
        return response;
    }

    /**
     * 使用feign客户端的方式进行通信
     * @return
     */
    @RequestMapping("order-to-product-test4")
    public String getProductMsg4(){
       String response=productClient.getProductMsg();
       return response;
    }




}
