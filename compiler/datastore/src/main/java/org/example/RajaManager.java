package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.example.store.CacheStore;
import org.example.store.ConnectionStore;
import org.example.store.FlywaySchemaHistoryStore;
import org.example.store.RajaStore;
import org.json.JSONObject;
import org.postgresql.util.PGInterval;
import org.postgresql.util.PGobject;
public final class RajaManager {

    private static RajaManager rajaManager;

    private final javax.sql.DataSource dbDataSource;

    private final Observer observer;

    private final FlywaySchemaHistoryStore flywaySchemaHistoryStore;
    private final CacheStore cacheStore;
    private final ConnectionStore connectionStore;
    private final RajaStore rajaStore;

    private RajaManager(final javax.sql.DataSource dbDataSource
    ,final Function<String,String> encryptionFunction
    ,final Function<String,String> decryptionFunction
    ) {
        this.dbDataSource = dbDataSource;
        this.observer = new Observer();
        this.flywaySchemaHistoryStore = new FlywaySchemaHistoryStore(dbDataSource,this.observer
        );
        this.cacheStore = new CacheStore(dbDataSource,this.observer
        );
        this.connectionStore = new ConnectionStore(dbDataSource,this.observer
        );
        this.rajaStore = new RajaStore(dbDataSource,this.observer
                    ,this::getInterval
                    ,this::convertInterval
                    ,this::getJson
                    ,this::convertJson
                    ,this::getJsonb
                    ,this::convertJsonb
                    ,this::getUuid
                    ,this::convertUuid
            ,encryptionFunction
            , decryptionFunction
        );
    }

    public static final RajaManager getManager(final DataSource dbDataSource
    ,final Function<String,String> encryptionFunction
    ,final Function<String,String> decryptionFunction
                                                            ) {
        if(rajaManager == null) {
            rajaManager = new RajaManager(dbDataSource
            
            , encryptionFunction
            ,decryptionFunction
            );
        }
        return rajaManager;
    }
    public final FlywaySchemaHistoryStore getFlywaySchemaHistoryStore() {
        return this.flywaySchemaHistoryStore;
    }
    public final CacheStore getCacheStore() {
        return this.cacheStore;
    }
    public final ConnectionStore getConnectionStore() {
        return this.connectionStore;
    }
    public final RajaStore getRajaStore() {
        return this.rajaStore;
    }

    private final Duration getInterval(final ResultSet rs,final int index) throws SQLException {
        final PGInterval interval = (PGInterval) rs.getObject(index);

        if (interval == null) {
            return null;
        }

        final int days = interval.getDays();
        final int hours = interval.getHours();
        final int minutes = interval.getMinutes();
        final double seconds = interval.getSeconds();

        return Duration.ofDays(days)
                .plus(hours, ChronoUnit.HOURS)
                .plus(minutes, ChronoUnit.MINUTES)
                .plus((long) Math.floor(seconds), ChronoUnit.SECONDS);
    }

    private final PGobject convertInterval(final Duration duration) throws SQLException {
        if(duration != null) {
            final int days = (int) duration.toDays();
            final int hours = (int) (duration.toHours() % 24);
            final int minutes = (int) (duration.toMinutes() % 60);
            final double seconds = duration.getSeconds() % 60;
            return new PGInterval(0, 0, days, hours, minutes, seconds);
        }
        return null;
    }
    private final JSONObject getJson(final ResultSet rs,final int index) throws SQLException {
        PGobject pGobject = (PGobject) rs.getObject(index);
        return pGobject == null ? null : new JSONObject(pGobject.getValue());
    }

    private final PGobject convertJson(final JSONObject jsonObject) throws SQLException {
        if(jsonObject == null) {
            PGobject pGobject = new PGobject();
            pGobject.setType("json");
            pGobject.setValue(jsonObject.toString());
        }
        return null;
    }
    private final JSONObject getJsonb(final ResultSet rs,final int index) throws SQLException {
        PGobject pGobject = (PGobject) rs.getObject(index);
        return pGobject == null ? null : new JSONObject(pGobject.getValue());
    }

    private final PGobject convertJsonb(final JSONObject jsonObject) throws SQLException {
        if(jsonObject == null) {
            PGobject pGobject = new PGobject();
            pGobject.setType("jsonb");
            pGobject.setValue(jsonObject.toString());
        }
        return null;
    }
    private final UUID getUuid(final ResultSet rs,final int index) throws SQLException {
        return (UUID) rs.getObject(index);
    }

    private final PGobject convertUuid(final UUID uuid) throws SQLException {
        if(uuid == null) {
            PGobject pGobject = new PGobject();
            pGobject.setType("uuid");
            pGobject.setValue(uuid.toString());
        }
        return null;
    }

    public class Observer
    {
        // Observer is internal
        // This also prevents store creation outside RajaManager
        private Observer() {

        }
    }

    @FunctionalInterface
    public interface ConvertFunction<T, Object>
    {
        Object apply(T t) throws SQLException;
    }

    @FunctionalInterface
    public interface GetFunction<ResultSet, Integer, R>
    {
        R apply(ResultSet t, Integer u) throws SQLException;
    }


    public static <T> Page<T> page(List<T> content, int totalElements) {
        return new Page(content,totalElements);
    }

    public static class Page<T> {
        final List<T> content;
        final int totalElements;

        private Page(List<T> content, int totalElements) {
            this.content = content;
            this.totalElements = totalElements;
        }

        public List<T> getContent() {
            return content;
        }

        public int getTotalElements() {
            return totalElements;
        }
    }

}

