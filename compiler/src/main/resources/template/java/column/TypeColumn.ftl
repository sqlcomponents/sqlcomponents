<#macro TypeColumn property>
<@columnheader property=property/>

    public void set(final PreparedStatement preparedStatement, final int i, final ${getClassName(property.dataType)} value) throws SQLException {

preparedStatement.setObject(i, value.name(), java.sql.Types.VARCHAR);
}

    public ${getClassName(property.dataType)} get(final ResultSet rs,final int index) throws SQLException {
        return ${getClassName(property.dataType)}.valueOf(rs.getString(index));
}

    <@columnfooter property=property/>
</#macro>