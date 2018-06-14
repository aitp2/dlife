jhipster-registry  Eureka  see https://www.jhipster.tech/jhipster-registry/

gateway JHipster can generate API gateways. A gateway is a normal JHipster application, so you can use the usual JHipster options and development workflows on that project, but it also acts as the entrance to your microservices. More specifically, it provides HTTP routing and load balancing, quality of service, security and API documentation for all microservices. see https://www.jhipster.tech/api-gateway/

UAA user authentication center 能够在此验证并获取token, oauth2暂时已经注释掉

Micro Service

fitness 健身
kitchen 大厨在身边
pinfan 拼饭项目

启动步骤
1.修改 UAA和各自微服务中的数据库设置。并新建相应数据库
如下：
        url: jdbc:mysql://192.168.56.200:3306/uaa?useUnicode=true&characterEncoding=utf8&useSSL=false
        username: root
        password: 123456
2.启动jhipster-registry 进入项目 执行mvn spring-boot:run 监听 8761端口
3.启动UAA进入项目 执行mvn spring-boot:run 监听9999端口

然后启动各自的微服务即可,使用mapstruct 需要先编译代码在启动。

