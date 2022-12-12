# webflux-ecommerce
scalable ecommerce proof of concept using spring reactor in a multi-container environment

>how to run the application on windows (**docker, mvn and java must be installed first !**)
>
> open the powershell at your_directory/webflux-ecommerce/docker>
```
.\start.cmd
```


- _the GET/products endpoint doesn't need authentication_
- _if you want to add a product, first you need to create an admin user on mongoDB 
(both in admin and user collection, same data) [advice: use mongoDB Compass]_

> ## Services:
>
> >### mongo
>> #### NoSQL db storing documents (users, products, orders) using a docker named volume
>
>> ### redis
>> #### key-value in-memory db used as cache for fetching products or to verify authentication tokens
>
>> ### zookeeper & kafka 
>> #### producer-consumer service in order to register user-accesses sent via kafka-producer 
>
>> ### tsunami 
>> #### jvm based image using java 11 to run the spring-boot web-server

 
