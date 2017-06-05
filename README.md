# 说明：这一份的文档说明是对版本1.0.1-SN..做的
## chenrd-business逻辑层的简单抽象出接口
  1、继承实现这个逻辑层里面抽象的接口，就继承了几个非常常用的方法，例如：save,delete,get

## chenrd-common 公共帮组类
  xls文件的导入导出抽象，通过注解@XlsPortAnnotation
  简单的两行代码就可以：
  
      Export excel = new XlsExPort();
      XSSFWorkbook workbook = excel.export(orderCode, list, clas);
      
  2、还有一些util类

## chenrd-ehcache ehcache3.0以上的封装
  ehcache3.0官网的文旦，初始化代码量比较多，这里做一个封装
  
      EhcacheFactory cacheFactory = EhcacheFactory.getEhcacheFactory();
      Cache<String, EntityQueryBuilder> cache = cacheFactory.newCache();
  
## chenrd-executor 基于spring-core下面的task实现的动态添加定时任务
  动态添加定时任务，网络上检索出来的一般都是基于quartz的文章
  考虑到spring自带定时任务功能，何必舍近求远
  
      <!-- 线程池pool-size大小初始化5个线程，最大25个，最长的等待队列100，keep-alive线程池空闲的情况下线程执行结束之后等待多少秒在关闭 -->
	    <task:executor id="executor"  pool-size="5-25" queue-capacity="100" keep-alive="0"/>
	    <task:scheduler id="scheduler" pool-size="10" />
	    <task:annotation-driven executor="executor" scheduler="scheduler" />
  spring的配置文件里面添加上面的配置文件，已经开启了线程池，定时任务，定时任务注解功能，官网说明<task:executor实例化接口TaskExecutor，<task:scheduler实例化接口TaskScheduler。
  说明：查看源码之后可以看出来这一套机制就是在java线程池的基础上做了一次封装
  chenrd-executor模块里面主要的一个接口：SpringTaskSchedulerService，这个接口里面提供了几个添加删除定时任务的方法
 
 ## chenrd-hibernate 基于hibernate的一些封装
  主要是抽象了一下dao层常用的方法，get,getByProperty,find,findByproperty等，这个层提供的接口：BaseDAO
  
 ## chenrd-http httpclient的封装
  这个是定制的封装，项目需求一下拉去数据及数据的分析入库，提供了一套常规的数据解析方式html,json,php的解析方式。
  如果这一套常规的解析方式不满足需求，只要新建一个方法替换原来的解析类，具体哪些步骤不符合就覆盖原来的方法。
      
      public interface StrAnalytical {
    
      /**
       * 解析
       * 
       * @param str
       * @return 
       * @see
       */
      List<List<String>> analytical() throws AnalysisException;
    
      /**
       * 初始化
       * @param str
       * @param analyParma 
       * @see
       */
      void init(String str, AnalyParma analyParma);
      }
      
   
