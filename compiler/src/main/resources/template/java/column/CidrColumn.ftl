<#macro CidrColumn property>
    <@columnheader property=property/>
     public void set(final DataManager.SqlBuilder preparedStatement, final SubnetUtils cidrAddress) {
    PGobject pgObject = null ;
        if(cidrAddress != null) {
            pgObject = new PGobject();
            pgObject.setType("cidr");
            try {
            pgObject.setValue(cidrAddress.getInfo().getCidrSignature());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
     preparedStatement.param(pgObject);
    }

    public SubnetUtils get(final ResultSet rs,final int index) throws SQLException {
    String cidrAddress = rs.getString(index);
    return cidrAddress == null ? null : new SubnetUtils(cidrAddress);
    }
    

    <@columnfooter property=property/>
</#macro>