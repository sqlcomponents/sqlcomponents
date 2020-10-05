<#include "/template/dao/java/method/signature/MViewRefresh.ftl">{
		sqlSession.update("refresh${pluralName}",null) ;
	}
