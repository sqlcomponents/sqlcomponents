<#macro CidrColumn property>
    <@columnheader property=property/>
     public void set(final PreparedStatement preparedStatement, final int i, final SubnetUtils cidrAddress) throws SQLException {
    PGobject pgObject = null ;
        if(cidrAddress != null) {
            pgObject = new PGobject();
            pgObject.setType("cidr");
            pgObject.setValue(cidrAddress.getInfo().getCidrSignature());
    }
     preparedStatement.setObject(i,pgObject);
    }

    public SubnetUtils get(final ResultSet rs,final int index) throws SQLException {
    String cidrAddress = rs.getString(index);
    return cidrAddress == null ? null : new SubnetUtils(cidrAddress);
    }
    

    <@columnfooter property=property/>
</#macro>