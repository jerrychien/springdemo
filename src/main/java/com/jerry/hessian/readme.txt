hessian Server端原先的方式:
<bean name="/userService" class="org.springframework.remoting.caucho.HessianServiceExporter">
    <property name="service" ref="gameInviteServiceImpl"/>
    <property name="serviceInterface" value="com.laohu.intra.ugc.remote.userService"/>
</bean>

hessian Client端原先的方式:
<bean id="userService" class="org.springframework.remoting.caucho.HessianProxyFactoryBean" parent="template">
    <property name="serviceUrl" value="http://user.intra.laohu.com/userService" />
    <property name="serviceInterface" value="com.laohu.intra.user.remote.UserService" />
</bean>

只需要修改HessianServiceExporter和HessianProxyFactory类做签名就可以了