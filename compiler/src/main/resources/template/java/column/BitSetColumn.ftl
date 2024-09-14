<#macro BitSetColumn property>
    <@columnheader property=property/>
    public void set(final PreparedStatement preparedStatement, final int i, final BitSet value) throws SQLException {


        if(value == null) {

            preparedStatement.setNull(i,${property.column.dataType?replace(",", "")},"${property.column.typeName}" );
        } else {
            PGobject bitObject = new PGobject();
            bitObject.setType("bit");
            StringBuffer valueBuffer = new StringBuffer();
            for (int j=0;j< value.length();j++) {
                valueBuffer.append(value.get(j)==true?"1":"0");
            }
            bitObject.setValue(valueBuffer.toString());
            preparedStatement.setObject(i, bitObject);
        }
    
    
    }

    public BitSet get(final ResultSet rs,final int index) throws SQLException {
        String jsonText = rs.getString(index);
        return jsonText == null ? null : BitSet.valueOf(jsonText.getBytes());
    }
    public final WhereClause  eq(final BitSet value) {
    sql = "${property.column.escapedName?j_string} =" + value ;
    return getWhereClause();
    }
    <@columnfooter property=property/>
</#macro>