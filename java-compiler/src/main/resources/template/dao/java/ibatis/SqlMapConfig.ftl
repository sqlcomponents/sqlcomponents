<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE sqlMapConfig
PUBLIC "-//iBATIS.com//DTD SQL Map Config 2.0//EN"
"http://ibatis.apache.org/dtd/sql-map-config-2.dtd">

<sqlMapConfig>

<#list entities as entity>
    <sqlMap resource="${entity.daoPackage?replace(".", "/")}/sqlmapdao/sqlmap/${entity.name}.xml" />    
</#list>

</sqlMapConfig>