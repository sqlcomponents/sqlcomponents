package org.example.store;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.stream.Collectors;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.example.model.AllInAllAzaguRajaReference;

/** */
public final class AllInAllAzaguRajaReferenceStore {

  private final DataSource dataSource;

  public AllInAllAzaguRajaReferenceStore(final DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public final int insert(final AllInAllAzaguRajaReference allInAllAzaguRajaReference)
      throws SQLException {
    final String query =
        """
  		INSERT INTO all_in_all_azagu_raja_reference (
  			code
  			,name
  		)
  	    VALUES ('1', 'x'), ('2', 'y'), ('3', 'z')
  		""";

    try (Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query)) {
      preparedStatement.setString(1, allInAllAzaguRajaReference.getCode());
      preparedStatement.setString(2, allInAllAzaguRajaReference.getName());
      return preparedStatement.executeUpdate();
    }
  }

  public final InsertBuilder insert() {
    return new InsertBuilder(this);
  }

  public static class InsertBuilder {
    private final AllInAllAzaguRajaReferenceStore allInAllAzaguRajaReferenceStore;

    private AllInAllAzaguRajaReference allInAllAzaguRajaReference;

    private InsertBuilder(AllInAllAzaguRajaReferenceStore allInAllAzaguRajaReferenceStore) {
      this.allInAllAzaguRajaReferenceStore = allInAllAzaguRajaReferenceStore;
    }

    public InsertBuilder value(AllInAllAzaguRajaReference allInAllAzaguRajaReference) {
      this.allInAllAzaguRajaReference = allInAllAzaguRajaReference;
      return this;
    }

    public AllInAllAzaguRajaReference returning() throws SQLException {
      AllInAllAzaguRajaReference insertedAllInAllAzaguRajaReference = null;
      final String query =
          """
  		INSERT INTO all_in_all_azagu_raja_reference (
  			code
  			,name
  		)
  	    VALUES (
    ?
            ,?
  	    )
  		""";

      try (Connection conn = allInAllAzaguRajaReferenceStore.dataSource.getConnection();
          PreparedStatement preparedStatement =
              conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        preparedStatement.setString(1, allInAllAzaguRajaReference.getCode());
        preparedStatement.setString(2, allInAllAzaguRajaReference.getName());
        if (preparedStatement.executeUpdate() == 1) {

          ResultSet res = preparedStatement.getGeneratedKeys();
          while (res.next()) {
            insertedAllInAllAzaguRajaReference =
                allInAllAzaguRajaReferenceStore.find(res.getString("code"));
          }
        }
      }
      return insertedAllInAllAzaguRajaReference;
    }
  }

  public int delete(String code) throws SQLException {
    final String query =
        """
                DELETE FROM all_in_all_azagu_raja_reference
					WHERE
						code = ?
                """;
    try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, code);

      return preparedStatement.executeUpdate();
    }
  }

  public int delete(Criteria criteria) throws SQLException {
    String whereClause = criteria.asSql();
    final String query =
        "DELETE FROM all_in_all_azagu_raja_reference"
            + (whereClause == null ? "" : (" WHERE " + whereClause));
    System.out.println(query);
    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {
      return statement.executeUpdate(query);
    }
  }

  public int deleteAll() throws SQLException {
    final String query = "DELETE FROM all_in_all_azagu_raja_reference";
    try (Connection connection = dataSource.getConnection();
        Statement statement = connection.prepareStatement(query)) {
      return statement.executeUpdate();
    }
  }

  public List<AllInAllAzaguRajaReference> select() throws SQLException {
    final String query =
            """
                                    SELECT
                    "code","name"		FROM all_in_all_azagu_raja_reference
                                    """;
    try (Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query)) {

      ResultSet resultSet = preparedStatement.executeQuery();
      List<AllInAllAzaguRajaReference> arrays = new ArrayList();
      while (resultSet.next()) {
        arrays.add(rowMapper(resultSet));
      }
      return arrays;
    }
  }

  public List<AllInAllAzaguRajaReference> select(Criteria criteria) throws SQLException {
    String whereClause = criteria.asSql();
    final String query =
        "SELECT \"code\",\"name\" FROM all_in_all_azagu_raja_reference"
            + (whereClause == null ? "" : (" WHERE " + whereClause));
    try (Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query)) {

      ResultSet resultSet = preparedStatement.executeQuery();
      List<AllInAllAzaguRajaReference> arrays = new ArrayList();
      while (resultSet.next()) {
        arrays.add(rowMapper(resultSet));
      }
      return arrays;
    }
  }

  public List<AllInAllAzaguRajaReference> getAllInAllAzaguRajaReferences() throws SQLException {
    HashMap<String, Object> map = new HashMap<String, Object>();

    return null;
  }

  public boolean exists(String code) throws SQLException {
    final String query =
        """
                SELECT
		1
		FROM all_in_all_azagu_raja_reference
		WHERE
			code = ?
                """;
    boolean isExists = false;
    try (Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query)) {
      preparedStatement.setString(1, code);

      ResultSet resultSet = preparedStatement.executeQuery();

      isExists = resultSet.next();
    }
    return isExists;
  }

  public void refreshAllInAllAzaguRajaReferences() throws SQLException {}

  public int update(AllInAllAzaguRajaReference allInAllAzaguRajaReference) throws SQLException {
    final String query =
        """
		UPDATE all_in_all_azagu_raja_reference SET
        			"name" = ?
        		WHERE
        			"code" = ?
		""";

    try (Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query)) {

      preparedStatement.setString(1, allInAllAzaguRajaReference.getName());

      preparedStatement.setString(2, allInAllAzaguRajaReference.getCode());

      return preparedStatement.executeUpdate();
    }
  }

  public AllInAllAzaguRajaReference find(String code) throws SQLException {
    final String query =
        """
                SELECT
\"code\",\"name\"		FROM all_in_all_azagu_raja_reference
		WHERE
			code = ?
                """;
    try (Connection conn = dataSource.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(query)) {
      preparedStatement.setString(1, code);

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) return rowMapper(resultSet);
    }
    return null;
  }

  private AllInAllAzaguRajaReference rowMapper(ResultSet rs) throws SQLException {
    final AllInAllAzaguRajaReference allInAllAzaguRajaReference = new AllInAllAzaguRajaReference();
    allInAllAzaguRajaReference.setCode(rs.getString(1));
    allInAllAzaguRajaReference.setName(rs.getString(2));
    return allInAllAzaguRajaReference;
  }

  public static PartialCriteria.NullableStringField code() {
    return new Criteria().code();
  }

  public static PartialCriteria.NullableStringField name() {
    return new Criteria().name();
  }

  public static class Criteria extends PartialCriteria {
    private String asSql() {
      return nodes.isEmpty()
          ? null
          : nodes.stream()
              .map(
                  node -> {
                    String asSql;
                    if (node instanceof Field) {
                      asSql = ((Field) node).asSql();
                    } else if (node instanceof Criteria) {
                      asSql = "(" + ((Criteria) node).asSql() + ")";
                    } else {
                      asSql = (String) node;
                    }
                    return asSql;
                  })
              .collect(Collectors.joining(" "));
    }

    public PartialCriteria and() {
      this.nodes.add("AND");
      return this;
    }

    public PartialCriteria or() {
      this.nodes.add("OR");
      return this;
    }

    public Criteria and(final Criteria criteria) {
      this.nodes.add("AND");
      this.nodes.add(criteria);
      return (Criteria) this;
    }

    public Criteria or(final Criteria criteria) {
      this.nodes.add("OR");
      this.nodes.add(criteria);
      return (Criteria) this;
    }
  }

  public static class PartialCriteria {

    protected final List<Object> nodes;

    public PartialCriteria() {
      this.nodes = new ArrayList<>();
    }

    public NullableStringField code() {
      NullableStringField query = new NullableStringField("code", this);
      this.nodes.add(query);
      return query;
    }

    public NullableStringField name() {
      NullableStringField query = new NullableStringField("name", this);
      this.nodes.add(query);
      return query;
    }

    public abstract class Field {

      protected final String columnName;
      private final PartialCriteria criteria;

      public Field(final String columnName, final PartialCriteria criteria) {
        this.columnName = columnName;
        this.criteria = criteria;
      }

      protected Criteria getCriteria() {
        return (Criteria) criteria;
      }

      protected abstract String asSql();
    }

    public class StringField extends Field {
      protected String sql;

      public StringField(final String columnName, final PartialCriteria criteria) {
        super(columnName, criteria);
      }

      public Criteria eq(final String value) {
        sql = columnName + "='" + value + "'";
        return getCriteria();
      }

      public Criteria like(final String value) {
        sql = columnName + " LIKE '" + value + "'";
        return getCriteria();
      }

      @Override
      protected String asSql() {
        return sql;
      }
    }

    public class NullableStringField extends StringField {

      public NullableStringField(final String columnName, final PartialCriteria criteria) {
        super(columnName, criteria);
      }

      public Criteria isNull() {
        sql = columnName + " IS NULL";
        return getCriteria();
      }

      public Criteria isNotNull() {
        sql = columnName + " IS NOT NULL";
        return getCriteria();
      }
    }

    public class NumberField<T extends Number> extends Field {

      protected String sql;

      public NumberField(final String columnName, final PartialCriteria criteria) {
        super(columnName, criteria);
      }

      public Criteria eq(final T value) {
        sql = columnName + "=" + value;
        return getCriteria();
      }

      public Criteria gt(final T value) {
        sql = columnName + ">" + value;
        return getCriteria();
      }

      public Criteria gte(final T value) {
        sql = columnName + ">=" + value;
        return getCriteria();
      }

      public Criteria lt(final T value) {
        sql = columnName + "<" + value;
        return getCriteria();
      }

      public Criteria lte(final T value) {
        sql = columnName + "<=" + value;
        return getCriteria();
      }

      @Override
      protected String asSql() {
        return sql;
      }
    }

    public class NullableNumberField<T extends Number> extends NumberField<T> {

      public NullableNumberField(final String columnName, final PartialCriteria criteria) {
        super(columnName, criteria);
      }

      public Criteria isNull() {
        sql = columnName + " IS NULL";
        return getCriteria();
      }

      public Criteria isNotNull() {
        sql = columnName + " IS NOT NULL";
        return getCriteria();
      }
    }

    public class DateField<Date> extends Field {

      protected String sql;

      public DateField(final String columnName, final PartialCriteria criteria) {
        super(columnName, criteria);
      }

      public Criteria eq(final Date value) {
        sql = columnName + "=" + value;
        return getCriteria();
      }

      public Criteria gt(final Date value) {
        sql = columnName + ">" + value;
        return getCriteria();
      }

      public Criteria gte(final Date value) {
        sql = columnName + ">=" + value;
        return getCriteria();
      }

      public Criteria lt(final Date value) {
        sql = columnName + "<" + value;
        return getCriteria();
      }

      public Criteria lte(final Date value) {
        sql = columnName + "<=" + value;
        return getCriteria();
      }

      @Override
      protected String asSql() {
        return sql;
      }
    }

    public class NullableDateField extends DateField {

      public NullableDateField(final String columnName, final PartialCriteria criteria) {
        super(columnName, criteria);
      }

      public Criteria isNull() {
        sql = columnName + " IS NULL";
        return getCriteria();
      }

      public Criteria isNotNull() {
        sql = columnName + " IS NOT NULL";
        return getCriteria();
      }
    }
  }
}
