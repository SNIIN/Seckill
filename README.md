# Seckill 秒杀项目第二版（分布式应用）

目前已完成数据库分库、服务拆分、注册、通信。<br>
### 服务拆分后的拓扑图:
<img src="https://user-images.githubusercontent.com/92317070/232967479-e6f49c05-04a6-4fa9-b022-0c19ad137942.png" heigh="380px" width="380px">

### 预期接下来的实现：
编写脚本在docker上部署各个服务，预期使用多台主机加入docker swarm中。<br>
实现网关流量控制与分发<br>
到实验室实际部署测试效率<br>
