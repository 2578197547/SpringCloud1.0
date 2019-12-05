#公共包，方便其他服务引用

1、引用该类在pom.xml加上
<dependency>
	<groupid>cn.springcloud.book</groupid>
	 <artifactid>ch10-1-common</artifactid>
	 <version>${parent.version}</version>
</dependency>
2、只要引用该服务，拦截器，定义的bean都将在引用该服务的工程中起作用